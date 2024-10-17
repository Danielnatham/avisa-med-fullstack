package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.EquipeAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.Equipe;
import br.ufs.avisamed.repository.EquipeRepository;
import br.ufs.avisamed.service.dto.EquipeDTO;
import br.ufs.avisamed.service.mapper.EquipeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link EquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipeResourceIT {

    private static final Integer DEFAULT_ID_OCORRENCIA = 1;
    private static final Integer UPDATED_ID_OCORRENCIA = 2;

    private static final Integer DEFAULT_ID_G = 1;
    private static final Integer UPDATED_ID_G = 2;

    private static final Instant DEFAULT_DATA_ATRIBUICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATRIBUICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FINALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FINALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeMapper equipeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    private Equipe insertedEquipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .idOcorrencia(DEFAULT_ID_OCORRENCIA)
            .idG(DEFAULT_ID_G)
            .dataAtribuicao(DEFAULT_DATA_ATRIBUICAO)
            .dataFinalizacao(DEFAULT_DATA_FINALIZACAO);
        return equipe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createUpdatedEntity(EntityManager em) {
        Equipe equipe = new Equipe()
            .idOcorrencia(UPDATED_ID_OCORRENCIA)
            .idG(UPDATED_ID_G)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataFinalizacao(UPDATED_DATA_FINALIZACAO);
        return equipe;
    }

    @BeforeEach
    public void initTest() {
        equipe = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEquipe != null) {
            equipeRepository.delete(insertedEquipe);
            insertedEquipe = null;
        }
    }

    @Test
    @Transactional
    void createEquipe() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);
        var returnedEquipeDTO = om.readValue(
            restEquipeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipeDTO.class
        );

        // Validate the Equipe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEquipe = equipeMapper.toEntity(returnedEquipeDTO);
        assertEquipeUpdatableFieldsEquals(returnedEquipe, getPersistedEquipe(returnedEquipe));

        insertedEquipe = returnedEquipe;
    }

    @Test
    @Transactional
    void createEquipeWithExistingId() throws Exception {
        // Create the Equipe with an existing ID
        equipe.setId(1L);
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdOcorrenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipe.setIdOcorrencia(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdGIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipe.setIdG(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataAtribuicaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipe.setDataAtribuicao(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataFinalizacaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipe.setDataFinalizacao(null);

        // Create the Equipe, which fails.
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipes() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].idOcorrencia").value(hasItem(DEFAULT_ID_OCORRENCIA)))
            .andExpect(jsonPath("$.[*].idG").value(hasItem(DEFAULT_ID_G)))
            .andExpect(jsonPath("$.[*].dataAtribuicao").value(hasItem(DEFAULT_DATA_ATRIBUICAO.toString())))
            .andExpect(jsonPath("$.[*].dataFinalizacao").value(hasItem(DEFAULT_DATA_FINALIZACAO.toString())));
    }

    @Test
    @Transactional
    void getEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.idOcorrencia").value(DEFAULT_ID_OCORRENCIA))
            .andExpect(jsonPath("$.idG").value(DEFAULT_ID_G))
            .andExpect(jsonPath("$.dataAtribuicao").value(DEFAULT_DATA_ATRIBUICAO.toString()))
            .andExpect(jsonPath("$.dataFinalizacao").value(DEFAULT_DATA_FINALIZACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe
            .idOcorrencia(UPDATED_ID_OCORRENCIA)
            .idG(UPDATED_ID_G)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataFinalizacao(UPDATED_DATA_FINALIZACAO);
        EquipeDTO equipeDTO = equipeMapper.toDto(updatedEquipe);

        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipeToMatchAllProperties(updatedEquipe);
    }

    @Test
    @Transactional
    void putNonExistingEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe.idOcorrencia(UPDATED_ID_OCORRENCIA).dataFinalizacao(UPDATED_DATA_FINALIZACAO);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEquipe, equipe), getPersistedEquipe(equipe));
    }

    @Test
    @Transactional
    void fullUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe
            .idOcorrencia(UPDATED_ID_OCORRENCIA)
            .idG(UPDATED_ID_G)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataFinalizacao(UPDATED_DATA_FINALIZACAO);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeUpdatableFieldsEquals(partialUpdatedEquipe, getPersistedEquipe(partialUpdatedEquipe));
    }

    @Test
    @Transactional
    void patchNonExistingEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // Create the Equipe
        EquipeDTO equipeDTO = equipeMapper.toDto(equipe);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipe
        restEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipeRepository.count();
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

    protected Equipe getPersistedEquipe(Equipe equipe) {
        return equipeRepository.findById(equipe.getId()).orElseThrow();
    }

    protected void assertPersistedEquipeToMatchAllProperties(Equipe expectedEquipe) {
        assertEquipeAllPropertiesEquals(expectedEquipe, getPersistedEquipe(expectedEquipe));
    }

    protected void assertPersistedEquipeToMatchUpdatableProperties(Equipe expectedEquipe) {
        assertEquipeAllUpdatablePropertiesEquals(expectedEquipe, getPersistedEquipe(expectedEquipe));
    }
}
