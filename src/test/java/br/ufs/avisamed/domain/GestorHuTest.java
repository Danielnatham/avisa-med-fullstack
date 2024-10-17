package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.GestorHuTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GestorHuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestorHu.class);
        GestorHu gestorHu1 = getGestorHuSample1();
        GestorHu gestorHu2 = new GestorHu();
        assertThat(gestorHu1).isNotEqualTo(gestorHu2);

        gestorHu2.setId(gestorHu1.getId());
        assertThat(gestorHu1).isEqualTo(gestorHu2);

        gestorHu2 = getGestorHuSample2();
        assertThat(gestorHu1).isNotEqualTo(gestorHu2);
    }

    @Test
    void colaboradorTest() {
        GestorHu gestorHu = getGestorHuRandomSampleGenerator();
        Colaborador colaboradorBack = getColaboradorRandomSampleGenerator();

        gestorHu.setColaborador(colaboradorBack);
        assertThat(gestorHu.getColaborador()).isEqualTo(colaboradorBack);

        gestorHu.colaborador(null);
        assertThat(gestorHu.getColaborador()).isNull();
    }
}
