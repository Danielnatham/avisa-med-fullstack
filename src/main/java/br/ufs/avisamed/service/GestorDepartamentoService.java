package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.GestorDepartamento}.
 */
public interface GestorDepartamentoService {
    /**
     * Save a gestorDepartamento.
     *
     * @param gestorDepartamentoDTO the entity to save.
     * @return the persisted entity.
     */
    GestorDepartamentoDTO save(GestorDepartamentoDTO gestorDepartamentoDTO);

    /**
     * Updates a gestorDepartamento.
     *
     * @param gestorDepartamentoDTO the entity to update.
     * @return the persisted entity.
     */
    GestorDepartamentoDTO update(GestorDepartamentoDTO gestorDepartamentoDTO);

    /**
     * Partially updates a gestorDepartamento.
     *
     * @param gestorDepartamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GestorDepartamentoDTO> partialUpdate(GestorDepartamentoDTO gestorDepartamentoDTO);

    /**
     * Get all the gestorDepartamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GestorDepartamentoDTO> findAll(Pageable pageable);

    /**
     * Get all the GestorDepartamentoDTO where Departamento is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<GestorDepartamentoDTO> findAllWhereDepartamentoIsNull();

    /**
     * Get the "id" gestorDepartamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GestorDepartamentoDTO> findOne(Long id);

    /**
     * Delete the "id" gestorDepartamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
