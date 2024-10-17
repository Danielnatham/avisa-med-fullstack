package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.GestorDepartamentoAsserts.*;
import static br.ufs.avisamed.domain.GestorDepartamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestorDepartamentoMapperTest {

    private GestorDepartamentoMapper gestorDepartamentoMapper;

    @BeforeEach
    void setUp() {
        gestorDepartamentoMapper = new GestorDepartamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGestorDepartamentoSample1();
        var actual = gestorDepartamentoMapper.toEntity(gestorDepartamentoMapper.toDto(expected));
        assertGestorDepartamentoAllPropertiesEquals(expected, actual);
    }
}
