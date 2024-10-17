package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.GestorDepartamentoAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.GestorDepartamento;
import br.ufs.avisamed.repository.GestorDepartamentoRepository;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import br.ufs.avisamed.service.mapper.GestorDepartamentoMapper;
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
 * Integration tests for the {@link GestorDepartamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GestorDepartamentoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gestor-departamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GestorDepartamentoRepository gestorDepartamentoRepository;

    @Autowired
    private GestorDepartamentoMapper gestorDepartamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestorDepartamentoMockMvc;

    private GestorDepartamento gestorDepartamento;

    private GestorDepartamento insertedGestorDepartamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestorDepartamento createEntity(EntityManager em) {
        GestorDepartamento gestorDepartamento = new GestorDepartamento().titulo(DEFAULT_TITULO).descricao(DEFAULT_DESCRICAO);
        return gestorDepartamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestorDepartamento createUpdatedEntity(EntityManager em) {
        GestorDepartamento gestorDepartamento = new GestorDepartamento().titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);
        return gestorDepartamento;
    }

    @BeforeEach
    public void initTest() {
        gestorDepartamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGestorDepartamento != null) {
            gestorDepartamentoRepository.delete(insertedGestorDepartamento);
            insertedGestorDepartamento = null;
        }
    }

    @Test
    @Transactional
    void createGestorDepartamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);
        var returnedGestorDepartamentoDTO = om.readValue(
            restGestorDepartamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorDepartamentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GestorDepartamentoDTO.class
        );

        // Validate the GestorDepartamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGestorDepartamento = gestorDepartamentoMapper.toEntity(returnedGestorDepartamentoDTO);
        assertGestorDepartamentoUpdatableFieldsEquals(
            returnedGestorDepartamento,
            getPersistedGestorDepartamento(returnedGestorDepartamento)
        );

        insertedGestorDepartamento = returnedGestorDepartamento;
    }

    @Test
    @Transactional
    void createGestorDepartamentoWithExistingId() throws Exception {
        // Create the GestorDepartamento with an existing ID
        gestorDepartamento.setId(1L);
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestorDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorDepartamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gestorDepartamento.setTitulo(null);

        // Create the GestorDepartamento, which fails.
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        restGestorDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorDepartamentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gestorDepartamento.setDescricao(null);

        // Create the GestorDepartamento, which fails.
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        restGestorDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorDepartamentoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGestorDepartamentos() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        // Get all the gestorDepartamentoList
        restGestorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestorDepartamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getGestorDepartamento() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        // Get the gestorDepartamento
        restGestorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, gestorDepartamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestorDepartamento.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingGestorDepartamento() throws Exception {
        // Get the gestorDepartamento
        restGestorDepartamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGestorDepartamento() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorDepartamento
        GestorDepartamento updatedGestorDepartamento = gestorDepartamentoRepository.findById(gestorDepartamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGestorDepartamento are not directly saved in db
        em.detach(updatedGestorDepartamento);
        updatedGestorDepartamento.titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(updatedGestorDepartamento);

        restGestorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestorDepartamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorDepartamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGestorDepartamentoToMatchAllProperties(updatedGestorDepartamento);
    }

    @Test
    @Transactional
    void putNonExistingGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestorDepartamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorDepartamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorDepartamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorDepartamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGestorDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorDepartamento using partial update
        GestorDepartamento partialUpdatedGestorDepartamento = new GestorDepartamento();
        partialUpdatedGestorDepartamento.setId(gestorDepartamento.getId());

        partialUpdatedGestorDepartamento.titulo(UPDATED_TITULO);

        restGestorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestorDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestorDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the GestorDepartamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestorDepartamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGestorDepartamento, gestorDepartamento),
            getPersistedGestorDepartamento(gestorDepartamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateGestorDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorDepartamento using partial update
        GestorDepartamento partialUpdatedGestorDepartamento = new GestorDepartamento();
        partialUpdatedGestorDepartamento.setId(gestorDepartamento.getId());

        partialUpdatedGestorDepartamento.titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);

        restGestorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestorDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestorDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the GestorDepartamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestorDepartamentoUpdatableFieldsEquals(
            partialUpdatedGestorDepartamento,
            getPersistedGestorDepartamento(partialUpdatedGestorDepartamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gestorDepartamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestorDepartamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestorDepartamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGestorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorDepartamento.setId(longCount.incrementAndGet());

        // Create the GestorDepartamento
        GestorDepartamentoDTO gestorDepartamentoDTO = gestorDepartamentoMapper.toDto(gestorDepartamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorDepartamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gestorDepartamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGestorDepartamento() throws Exception {
        // Initialize the database
        insertedGestorDepartamento = gestorDepartamentoRepository.saveAndFlush(gestorDepartamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gestorDepartamento
        restGestorDepartamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, gestorDepartamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gestorDepartamentoRepository.count();
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

    protected GestorDepartamento getPersistedGestorDepartamento(GestorDepartamento gestorDepartamento) {
        return gestorDepartamentoRepository.findById(gestorDepartamento.getId()).orElseThrow();
    }

    protected void assertPersistedGestorDepartamentoToMatchAllProperties(GestorDepartamento expectedGestorDepartamento) {
        assertGestorDepartamentoAllPropertiesEquals(expectedGestorDepartamento, getPersistedGestorDepartamento(expectedGestorDepartamento));
    }

    protected void assertPersistedGestorDepartamentoToMatchUpdatableProperties(GestorDepartamento expectedGestorDepartamento) {
        assertGestorDepartamentoAllUpdatablePropertiesEquals(
            expectedGestorDepartamento,
            getPersistedGestorDepartamento(expectedGestorDepartamento)
        );
    }
}
