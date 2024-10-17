package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.GestorDepartamentoRepository;
import br.ufs.avisamed.service.GestorDepartamentoService;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.ufs.avisamed.domain.GestorDepartamento}.
 */
@RestController
@RequestMapping("/api/gestor-departamentos")
public class GestorDepartamentoResource {

    private static final Logger log = LoggerFactory.getLogger(GestorDepartamentoResource.class);

    private static final String ENTITY_NAME = "gestorDepartamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestorDepartamentoService gestorDepartamentoService;

    private final GestorDepartamentoRepository gestorDepartamentoRepository;

    public GestorDepartamentoResource(
        GestorDepartamentoService gestorDepartamentoService,
        GestorDepartamentoRepository gestorDepartamentoRepository
    ) {
        this.gestorDepartamentoService = gestorDepartamentoService;
        this.gestorDepartamentoRepository = gestorDepartamentoRepository;
    }

    /**
     * {@code POST  /gestor-departamentos} : Create a new gestorDepartamento.
     *
     * @param gestorDepartamentoDTO the gestorDepartamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestorDepartamentoDTO, or with status {@code 400 (Bad Request)} if the gestorDepartamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GestorDepartamentoDTO> createGestorDepartamento(@Valid @RequestBody GestorDepartamentoDTO gestorDepartamentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save GestorDepartamento : {}", gestorDepartamentoDTO);
        if (gestorDepartamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gestorDepartamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gestorDepartamentoDTO = gestorDepartamentoService.save(gestorDepartamentoDTO);
        return ResponseEntity.created(new URI("/api/gestor-departamentos/" + gestorDepartamentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gestorDepartamentoDTO.getId().toString()))
            .body(gestorDepartamentoDTO);
    }

    /**
     * {@code PUT  /gestor-departamentos/:id} : Updates an existing gestorDepartamento.
     *
     * @param id the id of the gestorDepartamentoDTO to save.
     * @param gestorDepartamentoDTO the gestorDepartamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestorDepartamentoDTO,
     * or with status {@code 400 (Bad Request)} if the gestorDepartamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestorDepartamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GestorDepartamentoDTO> updateGestorDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GestorDepartamentoDTO gestorDepartamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GestorDepartamento : {}, {}", id, gestorDepartamentoDTO);
        if (gestorDepartamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestorDepartamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestorDepartamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gestorDepartamentoDTO = gestorDepartamentoService.update(gestorDepartamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gestorDepartamentoDTO.getId().toString()))
            .body(gestorDepartamentoDTO);
    }

    /**
     * {@code PATCH  /gestor-departamentos/:id} : Partial updates given fields of an existing gestorDepartamento, field will ignore if it is null
     *
     * @param id the id of the gestorDepartamentoDTO to save.
     * @param gestorDepartamentoDTO the gestorDepartamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestorDepartamentoDTO,
     * or with status {@code 400 (Bad Request)} if the gestorDepartamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gestorDepartamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gestorDepartamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GestorDepartamentoDTO> partialUpdateGestorDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GestorDepartamentoDTO gestorDepartamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GestorDepartamento partially : {}, {}", id, gestorDepartamentoDTO);
        if (gestorDepartamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestorDepartamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestorDepartamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GestorDepartamentoDTO> result = gestorDepartamentoService.partialUpdate(gestorDepartamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gestorDepartamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gestor-departamentos} : get all the gestorDepartamentos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestorDepartamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GestorDepartamentoDTO>> getAllGestorDepartamentos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("departamento-is-null".equals(filter)) {
            log.debug("REST request to get all GestorDepartamentos where departamento is null");
            return new ResponseEntity<>(gestorDepartamentoService.findAllWhereDepartamentoIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of GestorDepartamentos");
        Page<GestorDepartamentoDTO> page = gestorDepartamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gestor-departamentos/:id} : get the "id" gestorDepartamento.
     *
     * @param id the id of the gestorDepartamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestorDepartamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GestorDepartamentoDTO> getGestorDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to get GestorDepartamento : {}", id);
        Optional<GestorDepartamentoDTO> gestorDepartamentoDTO = gestorDepartamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gestorDepartamentoDTO);
    }

    /**
     * {@code DELETE  /gestor-departamentos/:id} : delete the "id" gestorDepartamento.
     *
     * @param id the id of the gestorDepartamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGestorDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete GestorDepartamento : {}", id);
        gestorDepartamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
