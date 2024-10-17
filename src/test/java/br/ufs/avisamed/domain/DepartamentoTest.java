package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.DepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.GestorDepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.OcorrenciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departamento.class);
        Departamento departamento1 = getDepartamentoSample1();
        Departamento departamento2 = new Departamento();
        assertThat(departamento1).isNotEqualTo(departamento2);

        departamento2.setId(departamento1.getId());
        assertThat(departamento1).isEqualTo(departamento2);

        departamento2 = getDepartamentoSample2();
        assertThat(departamento1).isNotEqualTo(departamento2);
    }

    @Test
    void gestorDepartamentoTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        GestorDepartamento gestorDepartamentoBack = getGestorDepartamentoRandomSampleGenerator();

        departamento.setGestorDepartamento(gestorDepartamentoBack);
        assertThat(departamento.getGestorDepartamento()).isEqualTo(gestorDepartamentoBack);

        departamento.gestorDepartamento(null);
        assertThat(departamento.getGestorDepartamento()).isNull();
    }

    @Test
    void ocorrenciaTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        Ocorrencia ocorrenciaBack = getOcorrenciaRandomSampleGenerator();

        departamento.addOcorrencia(ocorrenciaBack);
        assertThat(departamento.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getDepartamento()).isEqualTo(departamento);

        departamento.removeOcorrencia(ocorrenciaBack);
        assertThat(departamento.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getDepartamento()).isNull();

        departamento.ocorrencias(new HashSet<>(Set.of(ocorrenciaBack)));
        assertThat(departamento.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getDepartamento()).isEqualTo(departamento);

        departamento.setOcorrencias(new HashSet<>());
        assertThat(departamento.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getDepartamento()).isNull();
    }

    @Test
    void colaboradorTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        Colaborador colaboradorBack = getColaboradorRandomSampleGenerator();

        departamento.addColaborador(colaboradorBack);
        assertThat(departamento.getColaboradors()).containsOnly(colaboradorBack);
        assertThat(colaboradorBack.getDepartamento()).isEqualTo(departamento);

        departamento.removeColaborador(colaboradorBack);
        assertThat(departamento.getColaboradors()).doesNotContain(colaboradorBack);
        assertThat(colaboradorBack.getDepartamento()).isNull();

        departamento.colaboradors(new HashSet<>(Set.of(colaboradorBack)));
        assertThat(departamento.getColaboradors()).containsOnly(colaboradorBack);
        assertThat(colaboradorBack.getDepartamento()).isEqualTo(departamento);

        departamento.setColaboradors(new HashSet<>());
        assertThat(departamento.getColaboradors()).doesNotContain(colaboradorBack);
        assertThat(colaboradorBack.getDepartamento()).isNull();
    }
}
