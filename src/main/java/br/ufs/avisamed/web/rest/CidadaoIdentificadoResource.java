package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.CidadaoIdentificadoRepository;
import br.ufs.avisamed.service.CidadaoIdentificadoService;
import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.CidadaoIdentificado}.
 */
@RestController
@RequestMapping("/api/cidadao-identificados")
public class CidadaoIdentificadoResource {

    private static final Logger log = LoggerFactory.getLogger(CidadaoIdentificadoResource.class);

    private static final String ENTITY_NAME = "cidadaoIdentificado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CidadaoIdentificadoService cidadaoIdentificadoService;

    private final CidadaoIdentificadoRepository cidadaoIdentificadoRepository;

    public CidadaoIdentificadoResource(
        CidadaoIdentificadoService cidadaoIdentificadoService,
        CidadaoIdentificadoRepository cidadaoIdentificadoRepository
    ) {
        this.cidadaoIdentificadoService = cidadaoIdentificadoService;
        this.cidadaoIdentificadoRepository = cidadaoIdentificadoRepository;
    }

    /**
     * {@code POST  /cidadao-identificados} : Create a new cidadaoIdentificado.
     *
     * @param cidadaoIdentificadoDTO the cidadaoIdentificadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cidadaoIdentificadoDTO, or with status {@code 400 (Bad Request)} if the cidadaoIdentificado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CidadaoIdentificadoDTO> createCidadaoIdentificado(
        @Valid @RequestBody CidadaoIdentificadoDTO cidadaoIdentificadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CidadaoIdentificado : {}", cidadaoIdentificadoDTO);
        if (cidadaoIdentificadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cidadaoIdentificado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cidadaoIdentificadoDTO = cidadaoIdentificadoService.save(cidadaoIdentificadoDTO);
        return ResponseEntity.created(new URI("/api/cidadao-identificados/" + cidadaoIdentificadoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cidadaoIdentificadoDTO.getId().toString()))
            .body(cidadaoIdentificadoDTO);
    }

    /**
     * {@code PUT  /cidadao-identificados/:id} : Updates an existing cidadaoIdentificado.
     *
     * @param id the id of the cidadaoIdentificadoDTO to save.
     * @param cidadaoIdentificadoDTO the cidadaoIdentificadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadaoIdentificadoDTO,
     * or with status {@code 400 (Bad Request)} if the cidadaoIdentificadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cidadaoIdentificadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CidadaoIdentificadoDTO> updateCidadaoIdentificado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CidadaoIdentificadoDTO cidadaoIdentificadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CidadaoIdentificado : {}, {}", id, cidadaoIdentificadoDTO);
        if (cidadaoIdentificadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadaoIdentificadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadaoIdentificadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cidadaoIdentificadoDTO = cidadaoIdentificadoService.update(cidadaoIdentificadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cidadaoIdentificadoDTO.getId().toString()))
            .body(cidadaoIdentificadoDTO);
    }

    /**
     * {@code PATCH  /cidadao-identificados/:id} : Partial updates given fields of an existing cidadaoIdentificado, field will ignore if it is null
     *
     * @param id the id of the cidadaoIdentificadoDTO to save.
     * @param cidadaoIdentificadoDTO the cidadaoIdentificadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadaoIdentificadoDTO,
     * or with status {@code 400 (Bad Request)} if the cidadaoIdentificadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cidadaoIdentificadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cidadaoIdentificadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CidadaoIdentificadoDTO> partialUpdateCidadaoIdentificado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CidadaoIdentificadoDTO cidadaoIdentificadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CidadaoIdentificado partially : {}, {}", id, cidadaoIdentificadoDTO);
        if (cidadaoIdentificadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadaoIdentificadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadaoIdentificadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CidadaoIdentificadoDTO> result = cidadaoIdentificadoService.partialUpdate(cidadaoIdentificadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cidadaoIdentificadoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cidadao-identificados} : get all the cidadaoIdentificados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cidadaoIdentificados in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CidadaoIdentificadoDTO>> getAllCidadaoIdentificados(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CidadaoIdentificados");
        Page<CidadaoIdentificadoDTO> page = cidadaoIdentificadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cidadao-identificados/:id} : get the "id" cidadaoIdentificado.
     *
     * @param id the id of the cidadaoIdentificadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cidadaoIdentificadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CidadaoIdentificadoDTO> getCidadaoIdentificado(@PathVariable("id") Long id) {
        log.debug("REST request to get CidadaoIdentificado : {}", id);
        Optional<CidadaoIdentificadoDTO> cidadaoIdentificadoDTO = cidadaoIdentificadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cidadaoIdentificadoDTO);
    }

    /**
     * {@code DELETE  /cidadao-identificados/:id} : delete the "id" cidadaoIdentificado.
     *
     * @param id the id of the cidadaoIdentificadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidadaoIdentificado(@PathVariable("id") Long id) {
        log.debug("REST request to delete CidadaoIdentificado : {}", id);
        cidadaoIdentificadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
