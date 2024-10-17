package br.ufs.avisamed.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.ufs.avisamed.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EquipeColaboradorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipeColaboradorDTO.class);
        EquipeColaboradorDTO equipeColaboradorDTO1 = new EquipeColaboradorDTO();
        equipeColaboradorDTO1.setId(1L);
        EquipeColaboradorDTO equipeColaboradorDTO2 = new EquipeColaboradorDTO();
        assertThat(equipeColaboradorDTO1).isNotEqualTo(equipeColaboradorDTO2);
        equipeColaboradorDTO2.setId(equipeColaboradorDTO1.getId());
        assertThat(equipeColaboradorDTO1).isEqualTo(equipeColaboradorDTO2);
        equipeColaboradorDTO2.setId(2L);
        assertThat(equipeColaboradorDTO1).isNotEqualTo(equipeColaboradorDTO2);
        equipeColaboradorDTO1.setId(null);
        assertThat(equipeColaboradorDTO1).isNotEqualTo(equipeColaboradorDTO2);
    }
}
