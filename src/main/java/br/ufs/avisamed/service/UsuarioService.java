package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.UsuarioDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.Usuario}.
 */
public interface UsuarioService {
    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     * Updates a usuario.
     *
     * @param usuarioDTO the entity to update.
     * @return the persisted entity.
     */
    UsuarioDTO update(UsuarioDTO usuarioDTO);

    /**
     * Partially updates a usuario.
     *
     * @param usuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsuarioDTO> partialUpdate(UsuarioDTO usuarioDTO);

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioDTO> findAll(Pageable pageable);

    /**
     * Get all the UsuarioDTO where Colaborador is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UsuarioDTO> findAllWhereColaboradorIsNull();
    /**
     * Get all the UsuarioDTO where CidadaoIdentificado is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UsuarioDTO> findAllWhereCidadaoIdentificadoIsNull();

    /**
     * Get the "id" usuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioDTO> findOne(Long id);

    /**
     * Delete the "id" usuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
