package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.EquipeColaborador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EquipeColaborador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipeColaboradorRepository extends JpaRepository<EquipeColaborador, Long> {}
