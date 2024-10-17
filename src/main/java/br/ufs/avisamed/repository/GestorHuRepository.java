package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.GestorHu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GestorHu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestorHuRepository extends JpaRepository<GestorHu, Long> {}
