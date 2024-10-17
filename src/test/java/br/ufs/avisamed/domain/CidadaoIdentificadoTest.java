package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.CidadaoIdentificadoTestSamples.*;
import static br.ufs.avisamed.domain.OcorrenciaTestSamples.*;
import static br.ufs.avisamed.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CidadaoIdentificadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CidadaoIdentificado.class);
        CidadaoIdentificado cidadaoIdentificado1 = getCidadaoIdentificadoSample1();
        CidadaoIdentificado cidadaoIdentificado2 = new CidadaoIdentificado();
        assertThat(cidadaoIdentificado1).isNotEqualTo(cidadaoIdentificado2);

        cidadaoIdentificado2.setId(cidadaoIdentificado1.getId());
        assertThat(cidadaoIdentificado1).isEqualTo(cidadaoIdentificado2);

        cidadaoIdentificado2 = getCidadaoIdentificadoSample2();
        assertThat(cidadaoIdentificado1).isNotEqualTo(cidadaoIdentificado2);
    }

    @Test
    void usuarioTest() {
        CidadaoIdentificado cidadaoIdentificado = getCidadaoIdentificadoRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        cidadaoIdentificado.setUsuario(usuarioBack);
        assertThat(cidadaoIdentificado.getUsuario()).isEqualTo(usuarioBack);

        cidadaoIdentificado.usuario(null);
        assertThat(cidadaoIdentificado.getUsuario()).isNull();
    }

    @Test
    void ocorrenciaTest() {
        CidadaoIdentificado cidadaoIdentificado = getCidadaoIdentificadoRandomSampleGenerator();
        Ocorrencia ocorrenciaBack = getOcorrenciaRandomSampleGenerator();

        cidadaoIdentificado.addOcorrencia(ocorrenciaBack);
        assertThat(cidadaoIdentificado.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getCidadaoIdentificado()).isEqualTo(cidadaoIdentificado);

        cidadaoIdentificado.removeOcorrencia(ocorrenciaBack);
        assertThat(cidadaoIdentificado.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getCidadaoIdentificado()).isNull();

        cidadaoIdentificado.ocorrencias(new HashSet<>(Set.of(ocorrenciaBack)));
        assertThat(cidadaoIdentificado.getOcorrencias()).containsOnly(ocorrenciaBack);
        assertThat(ocorrenciaBack.getCidadaoIdentificado()).isEqualTo(cidadaoIdentificado);

        cidadaoIdentificado.setOcorrencias(new HashSet<>());
        assertThat(cidadaoIdentificado.getOcorrencias()).doesNotContain(ocorrenciaBack);
        assertThat(ocorrenciaBack.getCidadaoIdentificado()).isNull();
    }
}
