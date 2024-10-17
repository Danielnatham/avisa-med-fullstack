package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.GestorHuDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.GestorHu}.
 */
public interface GestorHuService {
    /**
     * Save a gestorHu.
     *
     * @param gestorHuDTO the entity to save.
     * @return the persisted entity.
     */
    GestorHuDTO save(GestorHuDTO gestorHuDTO);

    /**
     * Updates a gestorHu.
     *
     * @param gestorHuDTO the entity to update.
     * @return the persisted entity.
     */
    GestorHuDTO update(GestorHuDTO gestorHuDTO);

    /**
     * Partially updates a gestorHu.
     *
     * @param gestorHuDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GestorHuDTO> partialUpdate(GestorHuDTO gestorHuDTO);

    /**
     * Get all the gestorHus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GestorHuDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gestorHu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GestorHuDTO> findOne(Long id);

    /**
     * Delete the "id" gestorHu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
