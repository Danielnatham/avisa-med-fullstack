package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Departamento;
import br.ufs.avisamed.domain.GestorDepartamento;
import br.ufs.avisamed.service.dto.DepartamentoDTO;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Departamento} and its DTO {@link DepartamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartamentoMapper extends EntityMapper<DepartamentoDTO, Departamento> {
    @Mapping(target = "gestorDepartamento", source = "gestorDepartamento", qualifiedByName = "gestorDepartamentoId")
    DepartamentoDTO toDto(Departamento s);

    @Named("gestorDepartamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GestorDepartamentoDTO toDtoGestorDepartamentoId(GestorDepartamento gestorDepartamento);
}
