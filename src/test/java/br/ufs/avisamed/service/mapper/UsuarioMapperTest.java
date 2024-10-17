package br.ufs.avisamed.service.mapper;

import static br.ufs.avisamed.domain.UsuarioAsserts.*;
import static br.ufs.avisamed.domain.UsuarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        usuarioMapper = new UsuarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioSample1();
        var actual = usuarioMapper.toEntity(usuarioMapper.toDto(expected));
        assertUsuarioAllPropertiesEquals(expected, actual);
    }
}
