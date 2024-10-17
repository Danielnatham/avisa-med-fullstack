package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.domain.Departamento;
import br.ufs.avisamed.domain.Usuario;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.dto.DepartamentoDTO;
import br.ufs.avisamed.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Colaborador} and its DTO {@link ColaboradorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ColaboradorMapper extends EntityMapper<ColaboradorDTO, Colaborador> {
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoId")
    ColaboradorDTO toDto(Colaborador s);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);

    @Named("departamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartamentoDTO toDtoDepartamentoId(Departamento departamento);
}
