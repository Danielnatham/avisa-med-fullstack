package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.CidadaoIdentificado}.
 */
public interface CidadaoIdentificadoService {
    /**
     * Save a cidadaoIdentificado.
     *
     * @param cidadaoIdentificadoDTO the entity to save.
     * @return the persisted entity.
     */
    CidadaoIdentificadoDTO save(CidadaoIdentificadoDTO cidadaoIdentificadoDTO);

    /**
     * Updates a cidadaoIdentificado.
     *
     * @param cidadaoIdentificadoDTO the entity to update.
     * @return the persisted entity.
     */
    CidadaoIdentificadoDTO update(CidadaoIdentificadoDTO cidadaoIdentificadoDTO);

    /**
     * Partially updates a cidadaoIdentificado.
     *
     * @param cidadaoIdentificadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CidadaoIdentificadoDTO> partialUpdate(CidadaoIdentificadoDTO cidadaoIdentificadoDTO);

    /**
     * Get all the cidadaoIdentificados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CidadaoIdentificadoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cidadaoIdentificado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CidadaoIdentificadoDTO> findOne(Long id);

    /**
     * Delete the "id" cidadaoIdentificado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
