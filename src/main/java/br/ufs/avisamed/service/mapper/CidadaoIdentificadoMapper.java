package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.CidadaoIdentificado;
import br.ufs.avisamed.domain.Usuario;
import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
import br.ufs.avisamed.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CidadaoIdentificado} and its DTO {@link CidadaoIdentificadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CidadaoIdentificadoMapper extends EntityMapper<CidadaoIdentificadoDTO, CidadaoIdentificado> {
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    CidadaoIdentificadoDTO toDto(CidadaoIdentificado s);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
