package br.ufs.avisamed.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CidadaoIdentificadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CidadaoIdentificadoDTO.class);
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO1 = new CidadaoIdentificadoDTO();
        cidadaoIdentificadoDTO1.setId(1L);
        CidadaoIdentificadoDTO cidadaoIdentificadoDTO2 = new CidadaoIdentificadoDTO();
        assertThat(cidadaoIdentificadoDTO1).isNotEqualTo(cidadaoIdentificadoDTO2);
        cidadaoIdentificadoDTO2.setId(cidadaoIdentificadoDTO1.getId());
        assertThat(cidadaoIdentificadoDTO1).isEqualTo(cidadaoIdentificadoDTO2);
        cidadaoIdentificadoDTO2.setId(2L);
        assertThat(cidadaoIdentificadoDTO1).isNotEqualTo(cidadaoIdentificadoDTO2);
        cidadaoIdentificadoDTO1.setId(null);
        assertThat(cidadaoIdentificadoDTO1).isNotEqualTo(cidadaoIdentificadoDTO2);
    }
}
