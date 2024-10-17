package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.EquipeColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.EquipeTestSamples.*;
import static br.ufs.avisamed.domain.GestorDepartamentoTestSamples.*;
import static br.ufs.avisamed.domain.OcorrenciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipe.class);
        Equipe equipe1 = getEquipeSample1();
        Equipe equipe2 = new Equipe();
        assertThat(equipe1).isNotEqualTo(equipe2);

        equipe2.setId(equipe1.getId());
        assertThat(equipe1).isEqualTo(equipe2);

        equipe2 = getEquipeSample2();
        assertThat(equipe1).isNotEqualTo(equipe2);
    }

    @Test
    void ocorrenciaTest() {
        Equipe equipe = getEquipeRandomSampleGenerator();
        Ocorrencia ocorrenciaBack = getOcorrenciaRandomSampleGenerator();

        equipe.addOcorrencia(ocorrenciaBack);
        assertThat(equipe.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getEquipe()).isEqualTo(equipe);

        equipe.removeOcorrencia(ocorrenciaBack);
        assertThat(equipe.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getEquipe()).isNull();

        equipe.ocorrencias(new HashSet<>(Set.of(ocorrenciaBack)));
        assertThat(equipe.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getEquipe()).isEqualTo(equipe);

        equipe.setOcorrencias(new HashSet<>());
        assertThat(equipe.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getEquipe()).isNull();
    }

    @Test
    void equipeColaboradorTest() {
        Equipe equipe = getEquipeRandomSampleGenerator();
        EquipeColaborador equipeColaboradorBack = getEquipeColaboradorRandomSampleGenerator();

        equipe.setEquipeColaborador(equipeColaboradorBack);
        assertThat(equipe.getEquipeColaborador()).isEqualTo(equipeColaboradorBack);
        assertThat(equipeColaboradorBack.getEquipe()).isEqualTo(equipe);

        equipe.equipeColaborador(null);
        assertThat(equipe.getEquipeColaborador()).isNull();
        assertThat(equipeColaboradorBack.getEquipe()).isNull();
    }

    @Test
    void gestorDepartamentoTest() {
        Equipe equipe = getEquipeRandomSampleGenerator();
        GestorDepartamento gestorDepartamentoBack = getGestorDepartamentoRandomSampleGenerator();

        equipe.setGestorDepartamento(gestorDepartamentoBack);
        assertThat(equipe.getGestorDepartamento()).isEqualTo(gestorDepartamentoBack);

        equipe.gestorDepartamento(null);
        assertThat(equipe.getGestorDepartamento()).isNull();
    }
}
