package br.ufs.avisamed.web.rest;

import br.ufs.avisamed.repository.UsuarioRepository;
import br.ufs.avisamed.service.UsuarioService;
import br.ufs.avisamed.service.dto.UsuarioDTO;
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
 * REST controller for managing {@link br.ufs.avisamed.domain.Usuario}.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private static final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private static final String ENTITY_NAME = "usuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    public UsuarioResource(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * {@code POST  /usuarios} : Create a new usuario.
     *
     * @param usuarioDTO the usuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioDTO, or with status {@code 400 (Bad Request)} if the usuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuarioDTO);
        if (usuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioDTO = usuarioService.save(usuarioDTO);
        return ResponseEntity.created(new URI("/api/usuarios/" + usuarioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, usuarioDTO.getId().toString()))
            .body(usuarioDTO);
    }

    /**
     * {@code PUT  /usuarios/:id} : Updates an existing usuario.
     *
     * @param id the id of the usuarioDTO to save.
     * @param usuarioDTO the usuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioDTO usuarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}, {}", id, usuarioDTO);
        if (usuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioDTO = usuarioService.update(usuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioDTO.getId().toString()))
            .body(usuarioDTO);
    }

    /**
     * {@code PATCH  /usuarios/:id} : Partial updates given fields of an existing usuario, field will ignore if it is null
     *
     * @param id the id of the usuarioDTO to save.
     * @param usuarioDTO the usuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioDTO> partialUpdateUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioDTO usuarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Usuario partially : {}, {}", id, usuarioDTO);
        if (usuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioDTO> result = usuarioService.partialUpdate(usuarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, usuarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /usuarios} : get all the usuarios.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("colaborador-is-null".equals(filter)) {
            log.debug("REST request to get all Usuarios where colaborador is null");
            return new ResponseEntity<>(usuarioService.findAllWhereColaboradorIsNull(), HttpStatus.OK);
        }

        if ("cidadaoidentificado-is-null".equals(filter)) {
            log.debug("REST request to get all Usuarios where cidadaoIdentificado is null");
            return new ResponseEntity<>(usuarioService.findAllWhereCidadaoIdentificadoIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Usuarios");
        Page<UsuarioDTO> page = usuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuarios/:id} : get the "id" usuario.
     *
     * @param id the id of the usuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<UsuarioDTO> usuarioDTO = usuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioDTO);
    }

    /**
     * {@code DELETE  /usuarios/:id} : delete the "id" usuario.
     *
     * @param id the id of the usuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to delete Usuario : {}", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
