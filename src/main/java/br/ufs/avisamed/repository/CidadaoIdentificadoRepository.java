package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.CidadaoIdentificado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CidadaoIdentificado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CidadaoIdentificadoRepository extends JpaRepository<CidadaoIdentificado, Long> {}
