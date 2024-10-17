package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.CidadaoIdentificadoAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.CidadaoIdentificado;
import br.ufs.avisamed.repository.CidadaoIdentificadoRepository;
import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
import br.ufs.avisamed.service.mapper.CidadaoIdentificadoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CidadaoIdentificadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CidadaoIdentificadoResourceIT {

    private static final Integer DEFAULT_ID_USUARIO = 1;
    private static final Integer UPDATED_ID_USUARIO = 2;

    private static final String ENTITY_API_URL = "/api/cidadao-identificados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CidadaoIdentificadoRepository cidadaoIdentificadoRepository;

    @Autowired
    private CidadaoIdentificadoMapper cidadaoIdentificadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCidadaoIdentificadoMockMvc;

    private CidadaoIdentificado cidadaoIdentificado;

    private CidadaoIdentificado insertedCidadaoIdentificado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CidadaoIdentificado createEntity(EntityManager em) {
        CidadaoIdentificado cidadaoIdentificado = new CidadaoIdentificado().idUsuario(DEFAULT_ID_USUARIO);
        return cidadaoIdentificado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CidadaoIdentificado createUpdatedEntity(EntityManager em) {
        CidadaoIdentificado cidadaoIdentificado = new CidadaoIdentificado().idUsuario(UPDATED_ID_USUARIO);
        return cidadaoIdentificado;
    }

    @BeforeEach
    public void initTest() {
        cidadaoIdentificado = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCidadaoIdentificado != null) {
            cidadaoIdentificadoRepository.delete(insertedCidadaoIdentificado);
            insertedCidadaoIdentificado = null;
        }
    }

    @Test
    @Transactional
    void createCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);
        var returnedCidadaoIdentificadoDTO = om.readValue(
            restCidadaoIdentificadoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidadaoIdentificadoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CidadaoIdentificadoDTO.class
        );

        // Validate the CidadaoIdentificado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCidadaoIdentificado = cidadaoIdentificadoMapper.toEntity(returnedCidadaoIdentificadoDTO);
        assertCidadaoIdentificadoUpdatableFieldsEquals(
            returnedCidadaoIdentificado,
            getPersistedCidadaoIdentificado(returnedCidadaoIdentificado)
        );

        insertedCidadaoIdentificado = returnedCidadaoIdentificado;
    }

    @Test
    @Transactional
    void createCidadaoIdentificadoWithExistingId() throws Exception {
        // Create the CidadaoIdentificado with an existing ID
        cidadaoIdentificado.setId(1L);
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadaoIdentificadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidadaoIdentificadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdUsuarioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cidadaoIdentificado.setIdUsuario(null);

        // Create the CidadaoIdentificado, which fails.
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        restCidadaoIdentificadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidadaoIdentificadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCidadaoIdentificados() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        // Get all the cidadaoIdentificadoList
        restCidadaoIdentificadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidadaoIdentificado.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO)));
    }

    @Test
    @Transactional
    void getCidadaoIdentificado() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        // Get the cidadaoIdentificado
        restCidadaoIdentificadoMockMvc
            .perform(get(ENTITY_API_URL_ID, cidadaoIdentificado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cidadaoIdentificado.getId().intValue()))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingCidadaoIdentificado() throws Exception {
        // Get the cidadaoIdentificado
        restCidadaoIdentificadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCidadaoIdentificado() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidadaoIdentificado
        CidadaoIdentificado updatedCidadaoIdentificado = cidadaoIdentificadoRepository.findById(cidadaoIdentificado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCidadaoIdentificado are not directly saved in db
        em.detach(updatedCidadaoIdentificado);
        updatedCidadaoIdentificado.idUsuario(UPDATED_ID_USUARIO);
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(updatedCidadaoIdentificado);

        restCidadaoIdentificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadaoIdentificadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCidadaoIdentificadoToMatchAllProperties(updatedCidadaoIdentificado);
    }

    @Test
    @Transactional
    void putNonExistingCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadaoIdentificadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cidadaoIdentificadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCidadaoIdentificadoWithPatch() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidadaoIdentificado using partial update
        CidadaoIdentificado partialUpdatedCidadaoIdentificado = new CidadaoIdentificado();
        partialUpdatedCidadaoIdentificado.setId(cidadaoIdentificado.getId());

        restCidadaoIdentificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidadaoIdentificado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCidadaoIdentificado))
            )
            .andExpect(status().isOk());

        // Validate the CidadaoIdentificado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCidadaoIdentificadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCidadaoIdentificado, cidadaoIdentificado),
            getPersistedCidadaoIdentificado(cidadaoIdentificado)
        );
    }

    @Test
    @Transactional
    void fullUpdateCidadaoIdentificadoWithPatch() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cidadaoIdentificado using partial update
        CidadaoIdentificado partialUpdatedCidadaoIdentificado = new CidadaoIdentificado();
        partialUpdatedCidadaoIdentificado.setId(cidadaoIdentificado.getId());

        partialUpdatedCidadaoIdentificado.idUsuario(UPDATED_ID_USUARIO);

        restCidadaoIdentificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidadaoIdentificado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCidadaoIdentificado))
            )
            .andExpect(status().isOk());

        // Validate the CidadaoIdentificado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCidadaoIdentificadoUpdatableFieldsEquals(
            partialUpdatedCidadaoIdentificado,
            getPersistedCidadaoIdentificado(partialUpdatedCidadaoIdentificado)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cidadaoIdentificadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCidadaoIdentificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cidadaoIdentificado.setId(longCount.incrementAndGet());

        // Create the CidadaoIdentificado
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = cidadaoIdentificadoMapper.toDto(cidadaoIdentificado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadaoIdentificadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cidadaoIdentificadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CidadaoIdentificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCidadaoIdentificado() throws Exception {
        // Initialize the database
        insertedCidadaoIdentificado = cidadaoIdentificadoRepository.saveAndFlush(cidadaoIdentificado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cidadaoIdentificado
        restCidadaoIdentificadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cidadaoIdentificado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cidadaoIdentificadoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CidadaoIdentificado getPersistedCidadaoIdentificado(CidadaoIdentificado cidadaoIdentificado) {
        return cidadaoIdentificadoRepository.findById(cidadaoIdentificado.getId()).orElseThrow();
    }

    protected void assertPersistedCidadaoIdentificadoToMatchAllProperties(CidadaoIdentificado expectedCidadaoIdentificado) {
        assertCidadaoIdentificadoAllPropertiesEquals(
            expectedCidadaoIdentificado,
            getPersistedCidadaoIdentificado(expectedCidadaoIdentificado)
        );
    }

    protected void assertPersistedCidadaoIdentificadoToMatchUpdatableProperties(CidadaoIdentificado expectedCidadaoIdentificado) {
        assertCidadaoIdentificadoAllUpdatablePropertiesEquals(
            expectedCidadaoIdentificado,
            getPersistedCidadaoIdentificado(expectedCidadaoIdentificado)
        );
    }
}
