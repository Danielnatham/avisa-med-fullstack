package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.ColaboradorAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.repository.ColaboradorRepository;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.mapper.ColaboradorMapper;
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
 * Integration tests for the {@link ColaboradorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColaboradorResourceIT {

    private static final Integer DEFAULT_ID_DEPARTAMENTO = 1;
    private static final Integer UPDATED_ID_DEPARTAMENTO = 2;

    private static final Integer DEFAULT_ID_USUARIO = 1;
    private static final Integer UPDATED_ID_USUARIO = 2;

    private static final String ENTITY_API_URL = "/api/colaboradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ColaboradorMapper colaboradorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColaboradorMockMvc;

    private Colaborador colaborador;

    private Colaborador insertedColaborador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador().idDepartamento(DEFAULT_ID_DEPARTAMENTO).idUsuario(DEFAULT_ID_USUARIO);
        return colaborador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaborador createUpdatedEntity(EntityManager em) {
        Colaborador colaborador = new Colaborador().idDepartamento(UPDATED_ID_DEPARTAMENTO).idUsuario(UPDATED_ID_USUARIO);
        return colaborador;
    }

    @BeforeEach
    public void initTest() {
        colaborador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedColaborador != null) {
            colaboradorRepository.delete(insertedColaborador);
            insertedColaborador = null;
        }
    }

    @Test
    @Transactional
    void createColaborador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);
        var returnedColaboradorDTO = om.readValue(
            restColaboradorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(colaboradorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ColaboradorDTO.class
        );

        // Validate the Colaborador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedColaborador = colaboradorMapper.toEntity(returnedColaboradorDTO);
        assertColaboradorUpdatableFieldsEquals(returnedColaborador, getPersistedColaborador(returnedColaborador));

        insertedColaborador = returnedColaborador;
    }

    @Test
    @Transactional
    void createColaboradorWithExistingId() throws Exception {
        // Create the Colaborador with an existing ID
        colaborador.setId(1L);
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(colaboradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDepartamentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        colaborador.setIdDepartamento(null);

        // Create the Colaborador, which fails.
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(colaboradorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdUsuarioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        colaborador.setIdUsuario(null);

        // Create the Colaborador, which fails.
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        restColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(colaboradorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllColaboradors() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        // Get all the colaboradorList
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDepartamento").value(hasItem(DEFAULT_ID_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO)));
    }

    @Test
    @Transactional
    void getColaborador() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        // Get the colaborador
        restColaboradorMockMvc
            .perform(get(ENTITY_API_URL_ID, colaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colaborador.getId().intValue()))
            .andExpect(jsonPath("$.idDepartamento").value(DEFAULT_ID_DEPARTAMENTO))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingColaborador() throws Exception {
        // Get the colaborador
        restColaboradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingColaborador() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the colaborador
        Colaborador updatedColaborador = colaboradorRepository.findById(colaborador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedColaborador are not directly saved in db
        em.detach(updatedColaborador);
        updatedColaborador.idDepartamento(UPDATED_ID_DEPARTAMENTO).idUsuario(UPDATED_ID_USUARIO);
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(updatedColaborador);

        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(colaboradorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedColaboradorToMatchAllProperties(updatedColaborador);
    }

    @Test
    @Transactional
    void putNonExistingColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(colaboradorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador.idDepartamento(UPDATED_ID_DEPARTAMENTO);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertColaboradorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedColaborador, colaborador),
            getPersistedColaborador(colaborador)
        );
    }

    @Test
    @Transactional
    void fullUpdateColaboradorWithPatch() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the colaborador using partial update
        Colaborador partialUpdatedColaborador = new Colaborador();
        partialUpdatedColaborador.setId(colaborador.getId());

        partialUpdatedColaborador.idDepartamento(UPDATED_ID_DEPARTAMENTO).idUsuario(UPDATED_ID_USUARIO);

        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedColaborador))
            )
            .andExpect(status().isOk());

        // Validate the Colaborador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertColaboradorUpdatableFieldsEquals(partialUpdatedColaborador, getPersistedColaborador(partialUpdatedColaborador));
    }

    @Test
    @Transactional
    void patchNonExistingColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colaboradorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(colaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        colaborador.setId(longCount.incrementAndGet());

        // Create the Colaborador
        ColaboradorDTO colaboradorDTO = colaboradorMapper.toDto(colaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColaboradorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(colaboradorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteColaborador() throws Exception {
        // Initialize the database
        insertedColaborador = colaboradorRepository.saveAndFlush(colaborador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the colaborador
        restColaboradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, colaborador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return colaboradorRepository.count();
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

    protected Colaborador getPersistedColaborador(Colaborador colaborador) {
        return colaboradorRepository.findById(colaborador.getId()).orElseThrow();
    }

    protected void assertPersistedColaboradorToMatchAllProperties(Colaborador expectedColaborador) {
        assertColaboradorAllPropertiesEquals(expectedColaborador, getPersistedColaborador(expectedColaborador));
    }

    protected void assertPersistedColaboradorToMatchUpdatableProperties(Colaborador expectedColaborador) {
        assertColaboradorAllUpdatablePropertiesEquals(expectedColaborador, getPersistedColaborador(expectedColaborador));
    }
}
