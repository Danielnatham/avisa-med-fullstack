package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.CidadaoIdentificadoAsserts.*;
import static br.ufs.avisamed.domain.CidadaoIdentificadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CidadaoIdentificadoMapperTest {

    private CidadaoIdentificadoMapper cidadaoIdentificadoMapper;

    @BeforeEach
    void setUp() {
        cidadaoIdentificadoMapper = new CidadaoIdentificadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCidadaoIdentificadoSample1();
        var actual = cidadaoIdentificadoMapper.toEntity(cidadaoIdentificadoMapper.toDto(expected));
        assertCidadaoIdentificadoAllPropertiesEquals(expected, actual);
    }
}
