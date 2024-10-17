package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.DepartamentoRepository;
import br.ufs.avisamed.service.DepartamentoService;
import br.ufs.avisamed.service.dto.DepartamentoDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.Departamento}.
 */
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoResource {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoResource.class);

    private static final String ENTITY_NAME = "departamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentoService departamentoService;

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoResource(DepartamentoService departamentoService, DepartamentoRepository departamentoRepository) {
        this.departamentoService = departamentoService;
        this.departamentoRepository = departamentoRepository;
    }

    /**
     * {@code POST  /departamentos} : Create a new departamento.
     *
     * @param departamentoDTO the departamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentoDTO, or with status {@code 400 (Bad Request)} if the departamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DepartamentoDTO> createDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new departamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        departamentoDTO = departamentoService.save(departamentoDTO);
        return ResponseEntity.created(new URI("/api/departamentos/" + departamentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, departamentoDTO.getId().toString()))
            .body(departamentoDTO);
    }

    /**
     * {@code PUT  /departamentos/:id} : Updates an existing departamento.
     *
     * @param id the id of the departamentoDTO to save.
     * @param departamentoDTO the departamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoDTO,
     * or with status {@code 400 (Bad Request)} if the departamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> updateDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartamentoDTO departamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Departamento : {}, {}", id, departamentoDTO);
        if (departamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        departamentoDTO = departamentoService.update(departamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, departamentoDTO.getId().toString()))
            .body(departamentoDTO);
    }

    /**
     * {@code PATCH  /departamentos/:id} : Partial updates given fields of an existing departamento, field will ignore if it is null
     *
     * @param id the id of the departamentoDTO to save.
     * @param departamentoDTO the departamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoDTO,
     * or with status {@code 400 (Bad Request)} if the departamentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the departamentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartamentoDTO> partialUpdateDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepartamentoDTO departamentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Departamento partially : {}, {}", id, departamentoDTO);
        if (departamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartamentoDTO> result = departamentoService.partialUpdate(departamentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, departamentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /departamentos} : get all the departamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DepartamentoDTO>> getAllDepartamentos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Departamentos");
        Page<DepartamentoDTO> page = departamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamentos/:id} : get the "id" departamento.
     *
     * @param id the id of the departamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> getDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to get Departamento : {}", id);
        Optional<DepartamentoDTO> departamentoDTO = departamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departamentoDTO);
    }

    /**
     * {@code DELETE  /departamentos/:id} : delete the "id" departamento.
     *
     * @param id the id of the departamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Departamento : {}", id);
        departamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
