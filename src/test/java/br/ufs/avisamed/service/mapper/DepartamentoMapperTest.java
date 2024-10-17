package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.DepartamentoAsserts.*;
import static br.ufs.avisamed.domain.DepartamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartamentoMapperTest {

    private DepartamentoMapper departamentoMapper;

    @BeforeEach
    void setUp() {
        departamentoMapper = new DepartamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepartamentoSample1();
        var actual = departamentoMapper.toEntity(departamentoMapper.toDto(expected));
        assertDepartamentoAllPropertiesEquals(expected, actual);
    }
}
