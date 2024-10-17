package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.EquipeColaboradorRepository;
import br.ufs.avisamed.service.EquipeColaboradorService;
import br.ufs.avisamed.service.dto.EquipeColaboradorDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.EquipeColaborador}.
 */
@RestController
@RequestMapping("/api/equipe-colaboradors")
public class EquipeColaboradorResource {

    private static final Logger log = LoggerFactory.getLogger(EquipeColaboradorResource.class);

    private static final String ENTITY_NAME = "equipeColaborador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipeColaboradorService equipeColaboradorService;

    private final EquipeColaboradorRepository equipeColaboradorRepository;

    public EquipeColaboradorResource(
        EquipeColaboradorService equipeColaboradorService,
        EquipeColaboradorRepository equipeColaboradorRepository
    ) {
        this.equipeColaboradorService = equipeColaboradorService;
        this.equipeColaboradorRepository = equipeColaboradorRepository;
    }

    /**
     * {@code POST  /equipe-colaboradors} : Create a new equipeColaborador.
     *
     * @param equipeColaboradorDTO the equipeColaboradorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipeColaboradorDTO, or with status {@code 400 (Bad Request)} if the equipeColaborador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EquipeColaboradorDTO> createEquipeColaborador(@Valid @RequestBody EquipeColaboradorDTO equipeColaboradorDTO)
        throws URISyntaxException {
        log.debug("REST request to save EquipeColaborador : {}", equipeColaboradorDTO);
        if (equipeColaboradorDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipeColaborador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipeColaboradorDTO = equipeColaboradorService.save(equipeColaboradorDTO);
        return ResponseEntity.created(new URI("/api/equipe-colaboradors/" + equipeColaboradorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, equipeColaboradorDTO.getId().toString()))
            .body(equipeColaboradorDTO);
    }

    /**
     * {@code PUT  /equipe-colaboradors/:id} : Updates an existing equipeColaborador.
     *
     * @param id the id of the equipeColaboradorDTO to save.
     * @param equipeColaboradorDTO the equipeColaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipeColaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the equipeColaboradorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipeColaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EquipeColaboradorDTO> updateEquipeColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EquipeColaboradorDTO equipeColaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EquipeColaborador : {}, {}", id, equipeColaboradorDTO);
        if (equipeColaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipeColaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeColaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipeColaboradorDTO = equipeColaboradorService.update(equipeColaboradorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipeColaboradorDTO.getId().toString()))
            .body(equipeColaboradorDTO);
    }

    /**
     * {@code PATCH  /equipe-colaboradors/:id} : Partial updates given fields of an existing equipeColaborador, field will ignore if it is null
     *
     * @param id the id of the equipeColaboradorDTO to save.
     * @param equipeColaboradorDTO the equipeColaboradorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipeColaboradorDTO,
     * or with status {@code 400 (Bad Request)} if the equipeColaboradorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the equipeColaboradorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipeColaboradorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipeColaboradorDTO> partialUpdateEquipeColaborador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EquipeColaboradorDTO equipeColaboradorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EquipeColaborador partially : {}, {}", id, equipeColaboradorDTO);
        if (equipeColaboradorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipeColaboradorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeColaboradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipeColaboradorDTO> result = equipeColaboradorService.partialUpdate(equipeColaboradorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipeColaboradorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /equipe-colaboradors} : get all the equipeColaboradors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipeColaboradors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EquipeColaboradorDTO>> getAllEquipeColaboradors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EquipeColaboradors");
        Page<EquipeColaboradorDTO> page = equipeColaboradorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipe-colaboradors/:id} : get the "id" equipeColaborador.
     *
     * @param id the id of the equipeColaboradorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipeColaboradorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EquipeColaboradorDTO> getEquipeColaborador(@PathVariable("id") Long id) {
        log.debug("REST request to get EquipeColaborador : {}", id);
        Optional<EquipeColaboradorDTO> equipeColaboradorDTO = equipeColaboradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipeColaboradorDTO);
    }

    /**
     * {@code DELETE  /equipe-colaboradors/:id} : delete the "id" equipeColaborador.
     *
     * @param id the id of the equipeColaboradorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipeColaborador(@PathVariable("id") Long id) {
        log.debug("REST request to delete EquipeColaborador : {}", id);
        equipeColaboradorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
