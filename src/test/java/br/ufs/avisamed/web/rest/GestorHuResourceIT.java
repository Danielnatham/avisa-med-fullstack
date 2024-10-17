package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.GestorHuAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.GestorHu;
import br.ufs.avisamed.repository.GestorHuRepository;
import br.ufs.avisamed.service.dto.GestorHuDTO;
import br.ufs.avisamed.service.mapper.GestorHuMapper;
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
 * Integration tests for the {@link GestorHuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GestorHuResourceIT {

    private static final Integer DEFAULT_ID_COLABORADOR = 1;
    private static final Integer UPDATED_ID_COLABORADOR = 2;

    private static final String ENTITY_API_URL = "/api/gestor-hus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GestorHuRepository gestorHuRepository;

    @Autowired
    private GestorHuMapper gestorHuMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestorHuMockMvc;

    private GestorHu gestorHu;

    private GestorHu insertedGestorHu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestorHu createEntity(EntityManager em) {
        GestorHu gestorHu = new GestorHu().idColaborador(DEFAULT_ID_COLABORADOR);
        return gestorHu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestorHu createUpdatedEntity(EntityManager em) {
        GestorHu gestorHu = new GestorHu().idColaborador(UPDATED_ID_COLABORADOR);
        return gestorHu;
    }

    @BeforeEach
    public void initTest() {
        gestorHu = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGestorHu != null) {
            gestorHuRepository.delete(insertedGestorHu);
            insertedGestorHu = null;
        }
    }

    @Test
    @Transactional
    void createGestorHu() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);
        var returnedGestorHuDTO = om.readValue(
            restGestorHuMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorHuDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GestorHuDTO.class
        );

        // Validate the GestorHu in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGestorHu = gestorHuMapper.toEntity(returnedGestorHuDTO);
        assertGestorHuUpdatableFieldsEquals(returnedGestorHu, getPersistedGestorHu(returnedGestorHu));

        insertedGestorHu = returnedGestorHu;
    }

    @Test
    @Transactional
    void createGestorHuWithExistingId() throws Exception {
        // Create the GestorHu with an existing ID
        gestorHu.setId(1L);
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestorHuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorHuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdColaboradorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        gestorHu.setIdColaborador(null);

        // Create the GestorHu, which fails.
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        restGestorHuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorHuDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGestorHus() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        // Get all the gestorHuList
        restGestorHuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestorHu.getId().intValue())))
            .andExpect(jsonPath("$.[*].idColaborador").value(hasItem(DEFAULT_ID_COLABORADOR)));
    }

    @Test
    @Transactional
    void getGestorHu() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        // Get the gestorHu
        restGestorHuMockMvc
            .perform(get(ENTITY_API_URL_ID, gestorHu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestorHu.getId().intValue()))
            .andExpect(jsonPath("$.idColaborador").value(DEFAULT_ID_COLABORADOR));
    }

    @Test
    @Transactional
    void getNonExistingGestorHu() throws Exception {
        // Get the gestorHu
        restGestorHuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGestorHu() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorHu
        GestorHu updatedGestorHu = gestorHuRepository.findById(gestorHu.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGestorHu are not directly saved in db
        em.detach(updatedGestorHu);
        updatedGestorHu.idColaborador(UPDATED_ID_COLABORADOR);
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(updatedGestorHu);

        restGestorHuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestorHuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorHuDTO))
            )
            .andExpect(status().isOk());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGestorHuToMatchAllProperties(updatedGestorHu);
    }

    @Test
    @Transactional
    void putNonExistingGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestorHuDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorHuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gestorHuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gestorHuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGestorHuWithPatch() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorHu using partial update
        GestorHu partialUpdatedGestorHu = new GestorHu();
        partialUpdatedGestorHu.setId(gestorHu.getId());

        partialUpdatedGestorHu.idColaborador(UPDATED_ID_COLABORADOR);

        restGestorHuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestorHu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestorHu))
            )
            .andExpect(status().isOk());

        // Validate the GestorHu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestorHuUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedGestorHu, gestorHu), getPersistedGestorHu(gestorHu));
    }

    @Test
    @Transactional
    void fullUpdateGestorHuWithPatch() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gestorHu using partial update
        GestorHu partialUpdatedGestorHu = new GestorHu();
        partialUpdatedGestorHu.setId(gestorHu.getId());

        partialUpdatedGestorHu.idColaborador(UPDATED_ID_COLABORADOR);

        restGestorHuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestorHu.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGestorHu))
            )
            .andExpect(status().isOk());

        // Validate the GestorHu in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGestorHuUpdatableFieldsEquals(partialUpdatedGestorHu, getPersistedGestorHu(partialUpdatedGestorHu));
    }

    @Test
    @Transactional
    void patchNonExistingGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gestorHuDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestorHuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gestorHuDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGestorHu() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gestorHu.setId(longCount.incrementAndGet());

        // Create the GestorHu
        GestorHuDTO gestorHuDTO = gestorHuMapper.toDto(gestorHu);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestorHuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gestorHuDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestorHu in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGestorHu() throws Exception {
        // Initialize the database
        insertedGestorHu = gestorHuRepository.saveAndFlush(gestorHu);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gestorHu
        restGestorHuMockMvc
            .perform(delete(ENTITY_API_URL_ID, gestorHu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gestorHuRepository.count();
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

    protected GestorHu getPersistedGestorHu(GestorHu gestorHu) {
        return gestorHuRepository.findById(gestorHu.getId()).orElseThrow();
    }

    protected void assertPersistedGestorHuToMatchAllProperties(GestorHu expectedGestorHu) {
        assertGestorHuAllPropertiesEquals(expectedGestorHu, getPersistedGestorHu(expectedGestorHu));
    }

    protected void assertPersistedGestorHuToMatchUpdatableProperties(GestorHu expectedGestorHu) {
        assertGestorHuAllUpdatablePropertiesEquals(expectedGestorHu, getPersistedGestorHu(expectedGestorHu));
    }
}
