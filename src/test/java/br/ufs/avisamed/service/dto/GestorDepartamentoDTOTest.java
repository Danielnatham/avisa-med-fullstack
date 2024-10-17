package br.ufs.avisamed.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GestorDepartamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestorDepartamentoDTO.class);
        GestorDepartamentoDTO gestorDepartamentoDTO1 = new GestorDepartamentoDTO();
        gestorDepartamentoDTO1.setId(1L);
        GestorDepartamentoDTO gestorDepartamentoDTO2 = new GestorDepartamentoDTO();
        assertThat(gestorDepartamentoDTO1).isNotEqualTo(gestorDepartamentoDTO2);
        gestorDepartamentoDTO2.setId(gestorDepartamentoDTO1.getId());
        assertThat(gestorDepartamentoDTO1).isEqualTo(gestorDepartamentoDTO2);
        gestorDepartamentoDTO2.setId(2L);
        assertThat(gestorDepartamentoDTO1).isNotEqualTo(gestorDepartamentoDTO2);
        gestorDepartamentoDTO1.setId(null);
        assertThat(gestorDepartamentoDTO1).isNotEqualTo(gestorDepartamentoDTO2);
    }
}
