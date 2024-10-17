package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.OcorrenciaAsserts.*;
import static br.ufs.avisamed.domain.OcorrenciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OcorrenciaMapperTest {

    private OcorrenciaMapper ocorrenciaMapper;

    @BeforeEach
    void setUp() {
        ocorrenciaMapper = new OcorrenciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOcorrenciaSample1();
        var actual = ocorrenciaMapper.toEntity(ocorrenciaMapper.toDto(expected));
        assertOcorrenciaAllPropertiesEquals(expected, actual);
    }
}
