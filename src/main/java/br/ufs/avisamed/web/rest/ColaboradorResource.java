package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.ColaboradorRepository;
import br.ufs.avisamed.service.ColaboradorService;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.Colaborador}.
 */
@RestController
@RequestMapping("/api/colaboradors")
public class ColaboradorResource {

    private static final Logger log = LoggerFactory.getLogger(ColaboradorResource.class);

    private static final String ENTITY_NAME = "colaborador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColaboradorService colaboradorService;

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorResource(ColaboradorService colaboradorService, ColaboradorRepository colaboradorRepository) {
        this.colaboradorService = colaboradorService;
        this.colaboradorRepository = colaboradorRepository;
    }

    /**
     * {@code POST  /colaboradors} : Create a new colaborador.
     *
     * @param colaboradorDTO the colaboradorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colaboradorDTO, or with status {@code 400 (Bad Request)} if the colaborador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ColaboradorDTO> createColaborador(@Valid @RequestBody ColaboradorDTO colaboradorDTO) throws URISyntaxException {
        log.debug("REST request to save Colaborador : {}", colaboradorDTO);
        if (colaboradorDTO.getId() != null) {
            throw new BadRequestAlertException("A new colaborador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        colaboradorDTO = colaboradorService.save(colaboradorDTO);
        return ResponseEntity.created(new URI("/api/colaboradors/" + colaboradorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, colaboradorDTO.getId().toString()))
            .body(colaboradorDTO);
    }

    /**
     * {@code PUT  /colaboradors/:id} : Updates an existing colaborador.
     *
     * @param id the id of the colaboradorDTO to save.
     * @param colaboradorDTO the colaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the colaboradorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ColaboradorDTO> updateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ColaboradorDTO colaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Colaborador : {}, {}", id, colaboradorDTO);
        if (colaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        colaboradorDTO = colaboradorService.update(colaboradorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, colaboradorDTO.getId().toString()))
            .body(colaboradorDTO);
    }

    /**
     * {@code PATCH  /colaboradors/:id} : Partial updates given fields of an existing colaborador, field will ignore if it is null
     *
     * @param id the id of the colaboradorDTO to save.
     * @param colaboradorDTO the colaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the colaboradorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the colaboradorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the colaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ColaboradorDTO> partialUpdateColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ColaboradorDTO colaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Colaborador partially : {}, {}", id, colaboradorDTO);
        if (colaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ColaboradorDTO> result = colaboradorService.partialUpdate(colaboradorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, colaboradorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /colaboradors} : get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colaboradors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ColaboradorDTO>> getAllColaboradors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("gestordepartamento-is-null".equals(filter)) {
            log.debug("REST request to get all Colaboradors where gestorDepartamento is null");
            return new ResponseEntity<>(colaboradorService.findAllWhereGestorDepartamentoIsNull(), HttpStatus.OK);
        }

        if ("gestorhu-is-null".equals(filter)) {
            log.debug("REST request to get all Colaboradors where gestorHu is null");
            return new ResponseEntity<>(colaboradorService.findAllWhereGestorHuIsNull(), HttpStatus.OK);
        }

        if ("equipecolaborador-is-null".equals(filter)) {
            log.debug("REST request to get all Colaboradors where equipeColaborador is null");
            return new ResponseEntity<>(colaboradorService.findAllWhereEquipeColaboradorIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Colaboradors");
        Page<ColaboradorDTO> page = colaboradorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colaboradors/:id} : get the "id" colaborador.
     *
     * @param id the id of the colaboradorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colaboradorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorDTO> getColaborador(@PathVariable("id") Long id) {
        log.debug("REST request to get Colaborador : {}", id);
        Optional<ColaboradorDTO> colaboradorDTO = colaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colaboradorDTO);
    }

    /**
     * {@code DELETE  /colaboradors/:id} : delete the "id" colaborador.
     *
     * @param id the id of the colaboradorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable("id") Long id) {
        log.debug("REST request to delete Colaborador : {}", id);
        colaboradorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
