package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Equipe;
import br.ufs.avisamed.domain.GestorDepartamento;
import br.ufs.avisamed.service.dto.EquipeDTO;
import br.ufs.avisamed.service.dto.GestorDepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipe} and its DTO {@link EquipeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipeMapper extends EntityMapper<EquipeDTO, Equipe> {
    @Mapping(target = "gestorDepartamento", source = "gestorDepartamento", qualifiedByName = "gestorDepartamentoId")
    EquipeDTO toDto(Equipe s);

    @Named("gestorDepartamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GestorDepartamentoDTO toDtoGestorDepartamentoId(GestorDepartamento gestorDepartamento);
}
