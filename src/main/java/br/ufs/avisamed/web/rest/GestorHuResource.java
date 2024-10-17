package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.GestorHuRepository;
import br.ufs.avisamed.service.GestorHuService;
import br.ufs.avisamed.service.dto.GestorHuDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.GestorHu}.
 */
@RestController
@RequestMapping("/api/gestor-hus")
public class GestorHuResource {

    private static final Logger log = LoggerFactory.getLogger(GestorHuResource.class);

    private static final String ENTITY_NAME = "gestorHu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestorHuService gestorHuService;

    private final GestorHuRepository gestorHuRepository;

    public GestorHuResource(GestorHuService gestorHuService, GestorHuRepository gestorHuRepository) {
        this.gestorHuService = gestorHuService;
        this.gestorHuRepository = gestorHuRepository;
    }

    /**
     * {@code POST  /gestor-hus} : Create a new gestorHu.
     *
     * @param gestorHuDTO the gestorHuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestorHuDTO, or with status {@code 400 (Bad Request)} if the gestorHu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GestorHuDTO> createGestorHu(@Valid @RequestBody GestorHuDTO gestorHuDTO) throws URISyntaxException {
        log.debug("REST request to save GestorHu : {}", gestorHuDTO);
        if (gestorHuDTO.getId() != null) {
            throw new BadRequestAlertException("A new gestorHu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gestorHuDTO = gestorHuService.save(gestorHuDTO);
        return ResponseEntity.created(new URI("/api/gestor-hus/" + gestorHuDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gestorHuDTO.getId().toString()))
            .body(gestorHuDTO);
    }

    /**
     * {@code PUT  /gestor-hus/:id} : Updates an existing gestorHu.
     *
     * @param id the id of the gestorHuDTO to save.
     * @param gestorHuDTO the gestorHuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestorHuDTO,
     * or with status {@code 400 (Bad Request)} if the gestorHuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestorHuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GestorHuDTO> updateGestorHu(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GestorHuDTO gestorHuDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GestorHu : {}, {}", id, gestorHuDTO);
        if (gestorHuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestorHuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestorHuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gestorHuDTO = gestorHuService.update(gestorHuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gestorHuDTO.getId().toString()))
            .body(gestorHuDTO);
    }

    /**
     * {@code PATCH  /gestor-hus/:id} : Partial updates given fields of an existing gestorHu, field will ignore if it is null
     *
     * @param id the id of the gestorHuDTO to save.
     * @param gestorHuDTO the gestorHuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestorHuDTO,
     * or with status {@code 400 (Bad Request)} if the gestorHuDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gestorHuDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gestorHuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GestorHuDTO> partialUpdateGestorHu(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GestorHuDTO gestorHuDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GestorHu partially : {}, {}", id, gestorHuDTO);
        if (gestorHuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestorHuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestorHuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GestorHuDTO> result = gestorHuService.partialUpdate(gestorHuDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gestorHuDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gestor-hus} : get all the gestorHus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestorHus in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GestorHuDTO>> getAllGestorHus(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GestorHus");
        Page<GestorHuDTO> page = gestorHuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gestor-hus/:id} : get the "id" gestorHu.
     *
     * @param id the id of the gestorHuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestorHuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GestorHuDTO> getGestorHu(@PathVariable("id") Long id) {
        log.debug("REST request to get GestorHu : {}", id);
        Optional<GestorHuDTO> gestorHuDTO = gestorHuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gestorHuDTO);
    }

    /**
     * {@code DELETE  /gestor-hus/:id} : delete the "id" gestorHu.
     *
     * @param id the id of the gestorHuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGestorHu(@PathVariable("id") Long id) {
        log.debug("REST request to delete GestorHu : {}", id);
        gestorHuService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
