package br.ufs.avisamed.domain;

import static br.ufs.avisamed.domain.CidadaoIdentificadoTestSamples.*;
import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;
import static br.ufs.avisamed.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = getUsuarioSample1();
        Usuario usuario2 = new Usuario();
        assertThat(usuario1).isNotEqualTo(usuario2);

        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);

        usuario2 = getUsuarioSample2();
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    void colaboradorTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Colaborador colaboradorBack = getColaboradorRandomSampleGenerator();

        usuario.setColaborador(colaboradorBack);
        assertThat(usuario.getColaborador()).isEqualTo(colaboradorBack);
        assertThat(colaboradorBack.getUsuario()).isEqualTo(usuario);

        usuario.colaborador(null);
        assertThat(usuario.getColaborador()).isNull();
        assertThat(colaboradorBack.getUsuario()).isNull();
    }

    @Test
    void cidadaoIdentificadoTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        CidadaoIdentificado cidadaoIdentificadoBack = getCidadaoIdentificadoRandomSampleGenerator();

        usuario.setCidadaoIdentificado(cidadaoIdentificadoBack);
        assertThat(usuario.getCidadaoIdentificado()).isEqualTo(cidadaoIdentificadoBack);
        assertThat(cidadaoIdentificadoBack.getUsuario()).isEqualTo(usuario);

        usuario.cidadaoIdentificado(null);
        assertThat(usuario.getCidadaoIdentificado()).isNull();
        assertThat(cidadaoIdentificadoBack.getUsuario()).isNull();
    }
}
