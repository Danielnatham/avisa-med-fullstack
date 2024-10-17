package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.EquipeColaboradorAsserts.*;
import static br.ufs.avisamed.domain.EquipeColaboradorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquipeColaboradorMapperTest {

    private EquipeColaboradorMapper equipeColaboradorMapper;

    @BeforeEach
    void setUp() {
        equipeColaboradorMapper = new EquipeColaboradorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEquipeColaboradorSample1();
        var actual = equipeColaboradorMapper.toEntity(equipeColaboradorMapper.toDto(expected));
        assertEquipeColaboradorAllPropertiesEquals(expected, actual);
    }
}
