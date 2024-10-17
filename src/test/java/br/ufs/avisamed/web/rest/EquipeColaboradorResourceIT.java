package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.EquipeColaboradorAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.EquipeColaborador;
import br.ufs.avisamed.repository.EquipeColaboradorRepository;
import br.ufs.avisamed.service.dto.EquipeColaboradorDTO;
import br.ufs.avisamed.service.mapper.EquipeColaboradorMapper;
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
 * Integration tests for the {@link EquipeColaboradorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipeColaboradorResourceIT {

    private static final Integer DEFAULT_ID_COLABORADOR = 1;
    private static final Integer UPDATED_ID_COLABORADOR = 2;

    private static final Integer DEFAULT_ID_EQUIPE = 1;
    private static final Integer UPDATED_ID_EQUIPE = 2;

    private static final String ENTITY_API_URL = "/api/equipe-colaboradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipeColaboradorRepository equipeColaboradorRepository;

    @Autowired
    private EquipeColaboradorMapper equipeColaboradorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeColaboradorMockMvc;

    private EquipeColaborador equipeColaborador;

    private EquipeColaborador insertedEquipeColaborador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipeColaborador createEntity(EntityManager em) {
        EquipeColaborador equipeColaborador = new EquipeColaborador().idColaborador(DEFAULT_ID_COLABORADOR).idEquipe(DEFAULT_ID_EQUIPE);
        return equipeColaborador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipeColaborador createUpdatedEntity(EntityManager em) {
        EquipeColaborador equipeColaborador = new EquipeColaborador().idColaborador(UPDATED_ID_COLABORADOR).idEquipe(UPDATED_ID_EQUIPE);
        return equipeColaborador;
    }

    @BeforeEach
    public void initTest() {
        equipeColaborador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEquipeColaborador != null) {
            equipeColaboradorRepository.delete(insertedEquipeColaborador);
            insertedEquipeColaborador = null;
        }
    }

    @Test
    @Transactional
    void createEquipeColaborador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);
        var returnedEquipeColaboradorDTO = om.readValue(
            restEquipeColaboradorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeColaboradorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EquipeColaboradorDTO.class
        );

        // Validate the EquipeColaborador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEquipeColaborador = equipeColaboradorMapper.toEntity(returnedEquipeColaboradorDTO);
        assertEquipeColaboradorUpdatableFieldsEquals(returnedEquipeColaborador, getPersistedEquipeColaborador(returnedEquipeColaborador));

        insertedEquipeColaborador = returnedEquipeColaborador;
    }

    @Test
    @Transactional
    void createEquipeColaboradorWithExistingId() throws Exception {
        // Create the EquipeColaborador with an existing ID
        equipeColaborador.setId(1L);
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeColaboradorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdColaboradorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipeColaborador.setIdColaborador(null);

        // Create the EquipeColaborador, which fails.
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        restEquipeColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeColaboradorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdEquipeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        equipeColaborador.setIdEquipe(null);

        // Create the EquipeColaborador, which fails.
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        restEquipeColaboradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeColaboradorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEquipeColaboradors() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        // Get all the equipeColaboradorList
        restEquipeColaboradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipeColaborador.getId().intValue())))
            .andExpect(jsonPath("$.[*].idColaborador").value(hasItem(DEFAULT_ID_COLABORADOR)))
            .andExpect(jsonPath("$.[*].idEquipe").value(hasItem(DEFAULT_ID_EQUIPE)));
    }

    @Test
    @Transactional
    void getEquipeColaborador() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        // Get the equipeColaborador
        restEquipeColaboradorMockMvc
            .perform(get(ENTITY_API_URL_ID, equipeColaborador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipeColaborador.getId().intValue()))
            .andExpect(jsonPath("$.idColaborador").value(DEFAULT_ID_COLABORADOR))
            .andExpect(jsonPath("$.idEquipe").value(DEFAULT_ID_EQUIPE));
    }

    @Test
    @Transactional
    void getNonExistingEquipeColaborador() throws Exception {
        // Get the equipeColaborador
        restEquipeColaboradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipeColaborador() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipeColaborador
        EquipeColaborador updatedEquipeColaborador = equipeColaboradorRepository.findById(equipeColaborador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipeColaborador are not directly saved in db
        em.detach(updatedEquipeColaborador);
        updatedEquipeColaborador.idColaborador(UPDATED_ID_COLABORADOR).idEquipe(UPDATED_ID_EQUIPE);
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(updatedEquipeColaborador);

        restEquipeColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeColaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipeColaboradorDTO))
            )
            .andExpect(status().isOk());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipeColaboradorToMatchAllProperties(updatedEquipeColaborador);
    }

    @Test
    @Transactional
    void putNonExistingEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, equipeColaboradorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipeColaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipeColaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipeColaboradorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipeColaboradorWithPatch() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipeColaborador using partial update
        EquipeColaborador partialUpdatedEquipeColaborador = new EquipeColaborador();
        partialUpdatedEquipeColaborador.setId(equipeColaborador.getId());

        partialUpdatedEquipeColaborador.idColaborador(UPDATED_ID_COLABORADOR);

        restEquipeColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipeColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipeColaborador))
            )
            .andExpect(status().isOk());

        // Validate the EquipeColaborador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeColaboradorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEquipeColaborador, equipeColaborador),
            getPersistedEquipeColaborador(equipeColaborador)
        );
    }

    @Test
    @Transactional
    void fullUpdateEquipeColaboradorWithPatch() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipeColaborador using partial update
        EquipeColaborador partialUpdatedEquipeColaborador = new EquipeColaborador();
        partialUpdatedEquipeColaborador.setId(equipeColaborador.getId());

        partialUpdatedEquipeColaborador.idColaborador(UPDATED_ID_COLABORADOR).idEquipe(UPDATED_ID_EQUIPE);

        restEquipeColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipeColaborador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipeColaborador))
            )
            .andExpect(status().isOk());

        // Validate the EquipeColaborador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeColaboradorUpdatableFieldsEquals(
            partialUpdatedEquipeColaborador,
            getPersistedEquipeColaborador(partialUpdatedEquipeColaborador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipeColaboradorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipeColaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipeColaboradorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipeColaborador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipeColaborador.setId(longCount.incrementAndGet());

        // Create the EquipeColaborador
        EquipeColaboradorDTO equipeColaboradorDTO = equipeColaboradorMapper.toDto(equipeColaborador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeColaboradorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipeColaboradorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EquipeColaborador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipeColaborador() throws Exception {
        // Initialize the database
        insertedEquipeColaborador = equipeColaboradorRepository.saveAndFlush(equipeColaborador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipeColaborador
        restEquipeColaboradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipeColaborador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipeColaboradorRepository.count();
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

    protected EquipeColaborador getPersistedEquipeColaborador(EquipeColaborador equipeColaborador) {
        return equipeColaboradorRepository.findById(equipeColaborador.getId()).orElseThrow();
    }

    protected void assertPersistedEquipeColaboradorToMatchAllProperties(EquipeColaborador expectedEquipeColaborador) {
        assertEquipeColaboradorAllPropertiesEquals(expectedEquipeColaborador, getPersistedEquipeColaborador(expectedEquipeColaborador));
    }

    protected void assertPersistedEquipeColaboradorToMatchUpdatableProperties(EquipeColaborador expectedEquipeColaborador) {
        assertEquipeColaboradorAllUpdatablePropertiesEquals(
            expectedEquipeColaborador,
            getPersistedEquipeColaborador(expectedEquipeColaborador)
        );
    }
}
