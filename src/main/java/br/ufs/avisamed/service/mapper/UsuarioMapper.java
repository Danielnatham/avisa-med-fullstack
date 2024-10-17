package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Usuario;
import br.ufs.avisamed.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {}
