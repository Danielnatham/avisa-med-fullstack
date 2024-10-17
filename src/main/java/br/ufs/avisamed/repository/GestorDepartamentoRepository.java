package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.GestorDepartamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GestorDepartamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestorDepartamentoRepository extends JpaRepository<GestorDepartamento, Long> {}
