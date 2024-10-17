package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.OcorrenciaRepository;
import br.ufs.avisamed.service.OcorrenciaService;
import br.ufs.avisamed.service.dto.OcorrenciaDTO;
import br.ufs.avisamed.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.ufs.avisamed.domain.Ocorrencia}.
 */
@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaResource {

    private static final Logger log = LoggerFactory.getLogger(OcorrenciaResource.class);

    private static final String ENTITY_NAME = "ocorrencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcorrenciaService ocorrenciaService;

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaResource(OcorrenciaService ocorrenciaService, OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaService = ocorrenciaService;
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    /**
     * {@code POST  /ocorrencias} : Create a new ocorrencia.
     *
     * @param ocorrenciaDTO the ocorrenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ocorrenciaDTO, or with status {@code 400 (Bad Request)} if the ocorrencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OcorrenciaDTO> createOcorrencia(@Valid @RequestBody OcorrenciaDTO ocorrenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Ocorrencia : {}", ocorrenciaDTO);
        if (ocorrenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocorrencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ocorrenciaDTO = ocorrenciaService.save(ocorrenciaDTO);
        return ResponseEntity.created(new URI("/api/ocorrencias/" + ocorrenciaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ocorrenciaDTO.getId().toString()))
            .body(ocorrenciaDTO);
    }

    /**
     * {@code PUT  /ocorrencias/:id} : Updates an existing ocorrencia.
     *
     * @param id the id of the ocorrenciaDTO to save.
     * @param ocorrenciaDTO the ocorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the ocorrenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ocorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> updateOcorrencia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OcorrenciaDTO ocorrenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ocorrencia : {}, {}", id, ocorrenciaDTO);
        if (ocorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ocorrenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ocorrenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ocorrenciaDTO = ocorrenciaService.update(ocorrenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ocorrenciaDTO.getId().toString()))
            .body(ocorrenciaDTO);
    }

    /**
     * {@code PATCH  /ocorrencias/:id} : Partial updates given fields of an existing ocorrencia, field will ignore if it is null
     *
     * @param id the id of the ocorrenciaDTO to save.
     * @param ocorrenciaDTO the ocorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the ocorrenciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ocorrenciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ocorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OcorrenciaDTO> partialUpdateOcorrencia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OcorrenciaDTO ocorrenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ocorrencia partially : {}, {}", id, ocorrenciaDTO);
        if (ocorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ocorrenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ocorrenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OcorrenciaDTO> result = ocorrenciaService.partialUpdate(ocorrenciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ocorrenciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ocorrencias} : get all the ocorrencias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocorrencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OcorrenciaDTO>> getAllOcorrencias(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ocorrencias");
        Page<OcorrenciaDTO> page = ocorrenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ocorrencias/:id} : get the "id" ocorrencia.
     *
     * @param id the id of the ocorrenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ocorrenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> getOcorrencia(@PathVariable("id") Long id) {
        log.debug("REST request to get Ocorrencia : {}", id);
        Optional<OcorrenciaDTO> ocorrenciaDTO = ocorrenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocorrenciaDTO);
    }

    /**
     * {@code DELETE  /ocorrencias/:id} : delete the "id" ocorrencia.
     *
     * @param id the id of the ocorrenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOcorrencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ocorrencia : {}", id);
        ocorrenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
