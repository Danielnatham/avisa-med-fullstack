package br.ufs.avisamed.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GestorHuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestorHuDTO.class);
        GestorHuDTO gestorHuDTO1 = new GestorHuDTO();
        gestorHuDTO1.setId(1L);
        GestorHuDTO gestorHuDTO2 = new GestorHuDTO();
        assertThat(gestorHuDTO1).isNotEqualTo(gestorHuDTO2);
        gestorHuDTO2.setId(gestorHuDTO1.getId());
        assertThat(gestorHuDTO1).isEqualTo(gestorHuDTO2);
        gestorHuDTO2.setId(2L);
        assertThat(gestorHuDTO1).isNotEqualTo(gestorHuDTO2);
        gestorHuDTO1.setId(null);
        assertThat(gestorHuDTO1).isNotEqualTo(gestorHuDTO2);
    }
}
