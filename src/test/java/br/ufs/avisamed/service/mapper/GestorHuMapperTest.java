package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.GestorHuAsserts.*;
import static br.ufs.avisamed.domain.GestorHuTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestorHuMapperTest {

    private GestorHuMapper gestorHuMapper;

    @BeforeEach
    void setUp() {
        gestorHuMapper = new GestorHuMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGestorHuSample1();
        var actual = gestorHuMapper.toEntity(gestorHuMapper.toDto(expected));
        assertGestorHuAllPropertiesEquals(expected, actual);
    }
}
