package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.EquipeAsserts.*;
import static br.ufs.avisamed.domain.EquipeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquipeMapperTest {

    private EquipeMapper equipeMapper;

    @BeforeEach
    void setUp() {
        equipeMapper = new EquipeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEquipeSample1();
        var actual = equipeMapper.toEntity(equipeMapper.toDto(expected));
        assertEquipeAllPropertiesEquals(expected, actual);
    }
}
