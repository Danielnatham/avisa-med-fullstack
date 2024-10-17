package br.ufs.avisamed.repository;

import br.ufs.avisamed.domain.Ocorrencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ocorrencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {}
