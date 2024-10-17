package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.domain.GestorDepartamento;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GestorDepartamento} and its DTO {@link GestorDepartamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface GestorDepartamentoMapper extends EntityMapper<GestorDepartamentoDTO, GestorDepartamento> {
    @Mapping(target = "colaborador", source = "colaborador", qualifiedByName = "colaboradorId")
    GestorDepartamentoDTO toDto(GestorDepartamento s);

    @Named("colaboradorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ColaboradorDTO toDtoColaboradorId(Colaborador colaborador);
}
