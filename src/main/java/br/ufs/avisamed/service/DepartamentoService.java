package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.DepartamentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.Departamento}.
 */
public interface DepartamentoService {
    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save.
     * @return the persisted entity.
     */
    DepartamentoDTO save(DepartamentoDTO departamentoDTO);

    /**
     * Updates a departamento.
     *
     * @param departamentoDTO the entity to update.
     * @return the persisted entity.
     */
    DepartamentoDTO update(DepartamentoDTO departamentoDTO);

    /**
     * Partially updates a departamento.
     *
     * @param departamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DepartamentoDTO> partialUpdate(DepartamentoDTO departamentoDTO);

    /**
     * Get all the departamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepartamentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" departamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartamentoDTO> findOne(Long id);

    /**
     * Delete the "id" departamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
