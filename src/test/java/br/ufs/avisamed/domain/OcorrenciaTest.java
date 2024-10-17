package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.CidadaoIdentificadoTestSamples.*;
import static br.ufs.avisamed.domain.DepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.EquipeTestSamples.*;
import static br.ufs.avisamed.domain.OcorrenciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OcorrenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ocorrencia.class);
        Ocorrencia ocorrencia1 = getOcorrenciaSample1();
        Ocorrencia ocorrencia2 = new Ocorrencia();
        assertThat(ocorrencia1).isNotEqualTo(ocorrencia2);

        ocorrencia2.setId(ocorrencia1.getId());
        assertThat(ocorrencia1).isEqualTo(ocorrencia2);

        ocorrencia2 = getOcorrenciaSample2();
        assertThat(ocorrencia1).isNotEqualTo(ocorrencia2);
    }

    @Test
    void cidadaoIdentificadoTest() {
        Ocorrencia ocorrencia = getOcorrenciaRandomSampleGenerator();
        CidadaoIdentificado cidadaoIdentificadoBack = getCidadaoIdentificadoRandomSampleGenerator();

        ocorrencia.setCidadaoIdentificado(cidadaoIdentificadoBack);
        assertThat(ocorrencia.getCidadaoIdentificado()).isEqualTo(cidadaoIdentificadoBack);

        ocorrencia.cidadaoIdentificado(null);
        assertThat(ocorrencia.getCidadaoIdentificado()).isNull();
    }

    @Test
    void departamentoTest() {
        Ocorrencia ocorrencia = getOcorrenciaRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        ocorrencia.setDepartamento(departamentoBack);
        assertThat(ocorrencia.getDepartamento()).isEqualTo(departamentoBack);

        ocorrencia.departamento(null);
        assertThat(ocorrencia.getDepartamento()).isNull();
    }

    @Test
    void equipeTest() {
        Ocorrencia ocorrencia = getOcorrenciaRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        ocorrencia.setEquipe(equipeBack);
        assertThat(ocorrencia.getEquipe()).isEqualTo(equipeBack);

        ocorrencia.equipe(null);
        assertThat(ocorrencia.getEquipe()).isNull();
    }
}
