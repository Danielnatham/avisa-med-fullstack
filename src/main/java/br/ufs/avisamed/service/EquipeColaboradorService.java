package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.EquipeColaboradorDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.EquipeColaborador}.
 */
public interface EquipeColaboradorService {
    /**
     * Save a equipeColaborador.
     *
     * @param equipeColaboradorDTO the entity to save.
     * @return the persisted entity.
     */
    EquipeColaboradorDTO save(EquipeColaboradorDTO equipeColaboradorDTO);

    /**
     * Updates a equipeColaborador.
     *
     * @param equipeColaboradorDTO the entity to update.
     * @return the persisted entity.
     */
    EquipeColaboradorDTO update(EquipeColaboradorDTO equipeColaboradorDTO);

    /**
     * Partially updates a equipeColaborador.
     *
     * @param equipeColaboradorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EquipeColaboradorDTO> partialUpdate(EquipeColaboradorDTO equipeColaboradorDTO);

    /**
     * Get all the equipeColaboradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EquipeColaboradorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" equipeColaborador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquipeColaboradorDTO> findOne(Long id);

    /**
     * Delete the "id" equipeColaborador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
