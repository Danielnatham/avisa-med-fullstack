package br.ufs.avisamed.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ColaboradorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColaboradorDTO.class);
        ColaboradorDTO colaboradorDTO1 = new ColaboradorDTO();
        colaboradorDTO1.setId(1L);
        ColaboradorDTO colaboradorDTO2 = new ColaboradorDTO();
        assertThat(colaboradorDTO1).isNotEqualTo(colaboradorDTO2);
        colaboradorDTO2.setId(colaboradorDTO1.getId());
        assertThat(colaboradorDTO1).isEqualTo(colaboradorDTO2);
        colaboradorDTO2.setId(2L);
        assertThat(colaboradorDTO1).isNotEqualTo(colaboradorDTO2);
        colaboradorDTO1.setId(null);
        assertThat(colaboradorDTO1).isNotEqualTo(colaboradorDTO2);
    }
}
