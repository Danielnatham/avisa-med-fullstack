package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.ColaboradorAsserts.*;
import static br.ufs.avisamed.domain.ColaboradorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorMapperTest {

    private ColaboradorMapper colaboradorMapper;

    @BeforeEach
    void setUp() {
        colaboradorMapper = new ColaboradorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getColaboradorSample1();
        var actual = colaboradorMapper.toEntity(colaboradorMapper.toDto(expected));
        assertColaboradorAllPropertiesEquals(expected, actual);
    }
}
