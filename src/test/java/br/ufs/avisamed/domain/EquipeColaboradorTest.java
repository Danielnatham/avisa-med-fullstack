package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.EquipeColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.EquipeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipeColaboradorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipeColaborador.class);
        EquipeColaborador equipeColaborador1 = getEquipeColaboradorSample1();
        EquipeColaborador equipeColaborador2 = new EquipeColaborador();
        assertThat(equipeColaborador1).isNotEqualTo(equipeColaborador2);

        equipeColaborador2.setId(equipeColaborador1.getId());
        assertThat(equipeColaborador1).isEqualTo(equipeColaborador2);

        equipeColaborador2 = getEquipeColaboradorSample2();
        assertThat(equipeColaborador1).isNotEqualTo(equipeColaborador2);
    }

    @Test
    void equipeTest() {
        EquipeColaborador equipeColaborador = getEquipeColaboradorRandomSampleGenerator();
        Equipe equipeBack = getEquipeRandomSampleGenerator();

        equipeColaborador.setEquipe(equipeBack);
        assertThat(equipeColaborador.getEquipe()).isEqualTo(equipeBack);

        equipeColaborador.equipe(null);
        assertThat(equipeColaborador.getEquipe()).isNull();
    }

    @Test
    void colaboradorTest() {
        EquipeColaborador equipeColaborador = getEquipeColaboradorRandomSampleGenerator();
        Colaborador colaboradorBack = getColaboradorRandomSampleGenerator();

        equipeColaborador.setColaborador(colaboradorBack);
        assertThat(equipeColaborador.getColaborador()).isEqualTo(colaboradorBack);

        equipeColaborador.colaborador(null);
        assertThat(equipeColaborador.getColaborador()).isNull();
    }
}
