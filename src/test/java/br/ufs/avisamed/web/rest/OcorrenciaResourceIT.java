package br.ufs.avisamed.web.rest;

import static br.ufs.avisamed.domain.OcorrenciaAsserts.*;
import static br.ufs.avisamed.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufs.avisamed.IntegrationTest;
import br.ufs.avisamed.domain.Ocorrencia;
import br.ufs.avisamed.repository.OcorrenciaRepository;
import br.ufs.avisamed.service.dto.OcorrenciaDTO;
import br.ufs.avisamed.service.mapper.OcorrenciaMapper;
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
 * Integration tests for the {@link OcorrenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OcorrenciaResourceIT {

    private static final Integer DEFAULT_ID_SOLICITANTE = 1;
    private static final Integer UPDATED_ID_SOLICITANTE = 2;

    private static final Integer DEFAULT_ID_DEPARTAMENTO = 1;
    private static final Integer UPDATED_ID_DEPARTAMENTO = 2;

    private static final Instant DEFAULT_DATA_CRIACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CRIACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_RESOLUCAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_RESOLUCAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_SITUACAO = "AAAAAAAAAA";
    private static final String UPDATED_SITUACAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPLEXIDADE = 1;
    private static final Integer UPDATED_COMPLEXIDADE = 2;

    private static final String DEFAULT_PROTOCOLO = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ocorrencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private OcorrenciaMapper ocorrenciaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcorrenciaMockMvc;

    private Ocorrencia ocorrencia;

    private Ocorrencia insertedOcorrencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocorrencia createEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .idSolicitante(DEFAULT_ID_SOLICITANTE)
            .idDepartamento(DEFAULT_ID_DEPARTAMENTO)
            .dataCriacao(DEFAULT_DATA_CRIACAO)
            .dataResolucao(DEFAULT_DATA_RESOLUCAO)
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .imagem(DEFAULT_IMAGEM)
            .situacao(DEFAULT_SITUACAO)
            .complexidade(DEFAULT_COMPLEXIDADE)
            .protocolo(DEFAULT_PROTOCOLO);
        return ocorrencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocorrencia createUpdatedEntity(EntityManager em) {
        Ocorrencia ocorrencia = new Ocorrencia()
            .idSolicitante(UPDATED_ID_SOLICITANTE)
            .idDepartamento(UPDATED_ID_DEPARTAMENTO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataResolucao(UPDATED_DATA_RESOLUCAO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .situacao(UPDATED_SITUACAO)
            .complexidade(UPDATED_COMPLEXIDADE)
            .protocolo(UPDATED_PROTOCOLO);
        return ocorrencia;
    }

    @BeforeEach
    public void initTest() {
        ocorrencia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOcorrencia != null) {
            ocorrenciaRepository.delete(insertedOcorrencia);
            insertedOcorrencia = null;
        }
    }

    @Test
    @Transactional
    void createOcorrencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);
        var returnedOcorrenciaDTO = om.readValue(
            restOcorrenciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OcorrenciaDTO.class
        );

        // Validate the Ocorrencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOcorrencia = ocorrenciaMapper.toEntity(returnedOcorrenciaDTO);
        assertOcorrenciaUpdatableFieldsEquals(returnedOcorrencia, getPersistedOcorrencia(returnedOcorrencia));

        insertedOcorrencia = returnedOcorrencia;
    }

    @Test
    @Transactional
    void createOcorrenciaWithExistingId() throws Exception {
        // Create the Ocorrencia with an existing ID
        ocorrencia.setId(1L);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDepartamentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setIdDepartamento(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataCriacaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setDataCriacao(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataResolucaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setDataResolucao(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setTitulo(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setDescricao(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImagemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setImagem(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSituacaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setSituacao(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComplexidadeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setComplexidade(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProtocoloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ocorrencia.setProtocolo(null);

        // Create the Ocorrencia, which fails.
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        restOcorrenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOcorrencias() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get all the ocorrenciaList
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocorrencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSolicitante").value(hasItem(DEFAULT_ID_SOLICITANTE)))
            .andExpect(jsonPath("$.[*].idDepartamento").value(hasItem(DEFAULT_ID_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].dataResolucao").value(hasItem(DEFAULT_DATA_RESOLUCAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO)))
            .andExpect(jsonPath("$.[*].complexidade").value(hasItem(DEFAULT_COMPLEXIDADE)))
            .andExpect(jsonPath("$.[*].protocolo").value(hasItem(DEFAULT_PROTOCOLO)));
    }

    @Test
    @Transactional
    void getOcorrencia() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        // Get the ocorrencia
        restOcorrenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, ocorrencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocorrencia.getId().intValue()))
            .andExpect(jsonPath("$.idSolicitante").value(DEFAULT_ID_SOLICITANTE))
            .andExpect(jsonPath("$.idDepartamento").value(DEFAULT_ID_DEPARTAMENTO))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.dataResolucao").value(DEFAULT_DATA_RESOLUCAO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO))
            .andExpect(jsonPath("$.complexidade").value(DEFAULT_COMPLEXIDADE))
            .andExpect(jsonPath("$.protocolo").value(DEFAULT_PROTOCOLO));
    }

    @Test
    @Transactional
    void getNonExistingOcorrencia() throws Exception {
        // Get the ocorrencia
        restOcorrenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOcorrencia() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ocorrencia
        Ocorrencia updatedOcorrencia = ocorrenciaRepository.findById(ocorrencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOcorrencia are not directly saved in db
        em.detach(updatedOcorrencia);
        updatedOcorrencia
            .idSolicitante(UPDATED_ID_SOLICITANTE)
            .idDepartamento(UPDATED_ID_DEPARTAMENTO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataResolucao(UPDATED_DATA_RESOLUCAO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .situacao(UPDATED_SITUACAO)
            .complexidade(UPDATED_COMPLEXIDADE)
            .protocolo(UPDATED_PROTOCOLO);
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(updatedOcorrencia);

        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ocorrenciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOcorrenciaToMatchAllProperties(updatedOcorrencia);
    }

    @Test
    @Transactional
    void putNonExistingOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ocorrencia using partial update
        Ocorrencia partialUpdatedOcorrencia = new Ocorrencia();
        partialUpdatedOcorrencia.setId(ocorrencia.getId());

        partialUpdatedOcorrencia
            .idSolicitante(UPDATED_ID_SOLICITANTE)
            .idDepartamento(UPDATED_ID_DEPARTAMENTO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .situacao(UPDATED_SITUACAO)
            .protocolo(UPDATED_PROTOCOLO);

        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOcorrenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOcorrencia, ocorrencia),
            getPersistedOcorrencia(ocorrencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateOcorrenciaWithPatch() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ocorrencia using partial update
        Ocorrencia partialUpdatedOcorrencia = new Ocorrencia();
        partialUpdatedOcorrencia.setId(ocorrencia.getId());

        partialUpdatedOcorrencia
            .idSolicitante(UPDATED_ID_SOLICITANTE)
            .idDepartamento(UPDATED_ID_DEPARTAMENTO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataResolucao(UPDATED_DATA_RESOLUCAO)
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .situacao(UPDATED_SITUACAO)
            .complexidade(UPDATED_COMPLEXIDADE)
            .protocolo(UPDATED_PROTOCOLO);

        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOcorrencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOcorrencia))
            )
            .andExpect(status().isOk());

        // Validate the Ocorrencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOcorrenciaUpdatableFieldsEquals(partialUpdatedOcorrencia, getPersistedOcorrencia(partialUpdatedOcorrencia));
    }

    @Test
    @Transactional
    void patchNonExistingOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ocorrenciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ocorrenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOcorrencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ocorrencia.setId(longCount.incrementAndGet());

        // Create the Ocorrencia
        OcorrenciaDTO ocorrenciaDTO = ocorrenciaMapper.toDto(ocorrencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOcorrenciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ocorrenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ocorrencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOcorrencia() throws Exception {
        // Initialize the database
        insertedOcorrencia = ocorrenciaRepository.saveAndFlush(ocorrencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ocorrencia
        restOcorrenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ocorrencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ocorrenciaRepository.count();
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

    protected Ocorrencia getPersistedOcorrencia(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.findById(ocorrencia.getId()).orElseThrow();
    }

    protected void assertPersistedOcorrenciaToMatchAllProperties(Ocorrencia expectedOcorrencia) {
        assertOcorrenciaAllPropertiesEquals(expectedOcorrencia, getPersistedOcorrencia(expectedOcorrencia));
    }

    protected void assertPersistedOcorrenciaToMatchUpdatableProperties(Ocorrencia expectedOcorrencia) {
        assertOcorrenciaAllUpdatablePropertiesEquals(expectedOcorrencia, getPersistedOcorrencia(expectedOcorrencia));
    }
}
