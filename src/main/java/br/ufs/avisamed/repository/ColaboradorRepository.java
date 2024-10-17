package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.Colaborador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Colaborador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {}
