package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.DepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.EquipeColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.GestorDepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.GestorHuTestSamples.*;
import static br.ufs.avisamed.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ColaboradorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colaborador.class);
        Colaborador colaborador1 = getColaboradorSample1();
        Colaborador colaborador2 = new Colaborador();
        assertThat(colaborador1).isNotEqualTo(colaborador2);

        colaborador2.setId(colaborador1.getId());
        assertThat(colaborador1).isEqualTo(colaborador2);

        colaborador2 = getColaboradorSample2();
        assertThat(colaborador1).isNotEqualTo(colaborador2);
    }

    @Test
    void usuarioTest() {
        Colaborador colaborador = getColaboradorRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        colaborador.setUsuario(usuarioBack);
        assertThat(colaborador.getUsuario()).isEqualTo(usuarioBack);

        colaborador.usuario(null);
        assertThat(colaborador.getUsuario()).isNull();
    }

    @Test
    void gestorDepartamentoTest() {
        Colaborador colaborador = getColaboradorRandomSampleGenerator();
        GestorDepartamento gestorDepartamentoBack = getGestorDepartamentoRandomSampleGenerator();

        colaborador.setGestorDepartamento(gestorDepartamentoBack);
        assertThat(colaborador.getGestorDepartamento()).isEqualTo(gestorDepartamentoBack);
        assertThat(gestorDepartamentoBack.getColaborador()).isEqualTo(colaborador);

        colaborador.gestorDepartamento(null);
        assertThat(colaborador.getGestorDepartamento()).isNull();
        assertThat(gestorDepartamentoBack.getColaborador()).isNull();
    }

    @Test
    void gestorHuTest() {
        Colaborador colaborador = getColaboradorRandomSampleGenerator();
        GestorHu gestorHuBack = getGestorHuRandomSampleGenerator();

        colaborador.setGestorHu(gestorHuBack);
        assertThat(colaborador.getGestorHu()).isEqualTo(gestorHuBack);
        assertThat(gestorHuBack.getColaborador()).isEqualTo(colaborador);

        colaborador.gestorHu(null);
        assertThat(colaborador.getGestorHu()).isNull();
        assertThat(gestorHuBack.getColaborador()).isNull();
    }

    @Test
    void equipeColaboradorTest() {
        Colaborador colaborador = getColaboradorRandomSampleGenerator();
        EquipeColaborador equipeColaboradorBack = getEquipeColaboradorRandomSampleGenerator();

        colaborador.setEquipeColaborador(equipeColaboradorBack);
        assertThat(colaborador.getEquipeColaborador()).isEqualTo(equipeColaboradorBack);
        assertThat(equipeColaboradorBack.getColaborador()).isEqualTo(colaborador);

        colaborador.equipeColaborador(null);
        assertThat(colaborador.getEquipeColaborador()).isNull();
        assertThat(equipeColaboradorBack.getColaborador()).isNull();
    }

    @Test
    void departamentoTest() {
        Colaborador colaborador = getColaboradorRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        colaborador.setDepartamento(departamentoBack);
        assertThat(colaborador.getDepartamento()).isEqualTo(departamentoBack);

        colaborador.departamento(null);
        assertThat(colaborador.getDepartamento()).isNull();
    }
}
