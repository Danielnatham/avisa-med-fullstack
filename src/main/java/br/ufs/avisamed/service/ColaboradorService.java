package br.ufs.avisamed.service;

import br.ufs.avisamed.service.dto.ColaboradorDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.ufs.avisamed.domain.Colaborador}.
 */
public interface ColaboradorService {
    /**
     * Save a colaborador.
     *
     * @param colaboradorDTO the entity to save.
     * @return the persisted entity.
     */
    ColaboradorDTO save(ColaboradorDTO colaboradorDTO);

    /**
     * Updates a colaborador.
     *
     * @param colaboradorDTO the entity to update.
     * @return the persisted entity.
     */
    ColaboradorDTO update(ColaboradorDTO colaboradorDTO);

    /**
     * Partially updates a colaborador.
     *
     * @param colaboradorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ColaboradorDTO> partialUpdate(ColaboradorDTO colaboradorDTO);

    /**
     * Get all the colaboradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ColaboradorDTO> findAll(Pageable pageable);

    /**
     * Get all the ColaboradorDTO where GestorDepartamento is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ColaboradorDTO> findAllWhereGestorDepartamentoIsNull();
    /**
     * Get all the ColaboradorDTO where GestorHu is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ColaboradorDTO> findAllWhereGestorHuIsNull();
    /**
     * Get all the ColaboradorDTO where EquipeColaborador is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ColaboradorDTO> findAllWhereEquipeColaboradorIsNull();

    /**
     * Get the "id" colaborador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColaboradorDTO> findOne(Long id);

    /**
     * Delete the "id" colaborador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
