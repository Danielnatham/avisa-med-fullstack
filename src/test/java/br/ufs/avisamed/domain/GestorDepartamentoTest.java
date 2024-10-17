package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.DepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.EquipeTestSamples.*;
import static br.ufs.avisamed.domain.GestorDepartamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GestorDepartamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestorDepartamento.class);
        GestorDepartamento gestorDepartamento1 = getGestorDepartamentoSample1();
        GestorDepartamento gestorDepartamento2 = new GestorDepartamento();
        assertThat(gestorDepartamento1).isNotEqualTo(gestorDepartamento2);

        gestorDepartamento2.setId(gestorDepartamento1.getId());
        assertThat(gestorDepartamento1).isEqualTo(gestorDepartamento2);

        gestorDepartamento2 = getGestorDepartamentoSample2();
        assertThat(gestorDepartamento1).isNotEqualTo(gestorDepartamento2);
    }

    @Test
    void colaboradorTest() {
        GestorDepartamento gestorDepartamento = getGestorDepartamentoRandomSampleGenerator();
        Colaborador colaboradorBack = getColaboradorRandomSampleGenerator();

        gestorDepartamento.setColaborador(colaboradorBack);
        assertThat(gestorDepartamento.getColaborador()).isEqualTo(colaboradorBack);

        gestorDepartamento.colaborador(null);
        assertThat(gestorDepartamento.getColaborador()).isNull();
    }

    @Test
    void equipeTest() {
        GestorDepartamento gestorDepartamento = getGestorDepartamentoRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        gestorDepartamento.addEquipe(equipeBack);
        assertThat(gestorDepartamento.getEquipes()).containsOnly(equipeBack);
        assertThat(equipeBack.getGestorDepartamento()).isEqualTo(gestorDepartamento);

        gestorDepartamento.removeEquipe(equipeBack);
        assertThat(gestorDepartamento.getEquipes()).doesNotContain(equipeBack);
        assertThat(equipeBack.getGestorDepartamento()).isNull();

        gestorDepartamento.equipes(new HashSet<>(Set.of(equipeBack)));
        assertThat(gestorDepartamento.getEquipes()).containsOnly(equipeBack);
        assertThat(equipeBack.getGestorDepartamento()).isEqualTo(gestorDepartamento);

        gestorDepartamento.setEquipes(new HashSet<>());
        assertThat(gestorDepartamento.getEquipes()).doesNotContain(equipeBack);
        assertThat(equipeBack.getGestorDepartamento()).isNull();
    }

    @Test
    void departamentoTest() {
        GestorDepartamento gestorDepartamento = getGestorDepartamentoRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        gestorDepartamento.setDepartamento(departamentoBack);
        assertThat(gestorDepartamento.getDepartamento()).isEqualTo(departamentoBack);
        assertThat(departamentoBack.getGestorDepartamento()).isEqualTo(gestorDepartamento);

        gestorDepartamento.departamento(null);
        assertThat(gestorDepartamento.getDepartamento()).isNull();
        assertThat(departamentoBack.getGestorDepartamento()).isNull();
    }
}
