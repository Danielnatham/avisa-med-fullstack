package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.EquipeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.Equipe}.
 */
public interface EquipeService {
    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save.
     * @return the persisted entity.
     */
    EquipeDTO save(EquipeDTO equipeDTO);

    /**
     * Updates a equipe.
     *
     * @param equipeDTO the entity to update.
     * @return the persisted entity.
     */
    EquipeDTO update(EquipeDTO equipeDTO);

    /**
     * Partially updates a equipe.
     *
     * @param equipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EquipeDTO> partialUpdate(EquipeDTO equipeDTO);

    /**
     * Get all the equipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EquipeDTO> findAll(Pageable pageable);

    /**
     * Get all the EquipeDTO where EquipeColaborador is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EquipeDTO> findAllWhereEquipeColaboradorIsNull();

    /**
     * Get the "id" equipe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquipeDTO> findOne(Long id);

    /**
     * Delete the "id" equipe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
