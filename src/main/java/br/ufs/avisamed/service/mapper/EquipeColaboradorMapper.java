package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.domain.Equipe;
import br.ufs.avisamed.domain.EquipeColaborador;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.dto.EquipeColaboradorDTO;
import br.ufs.avisamed.service.dto.EquipeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EquipeColaborador} and its DTO {@link EquipeColaboradorDTO}.
 */
@Mapper(componentModel = "spring")
public interface EquipeColaboradorMapper extends EntityMapper<EquipeColaboradorDTO, EquipeColaborador> {
    @Mapping(target = "equipe", source = "equipe", qualifiedByName = "equipeId")
    @Mapping(target = "colaborador", source = "colaborador", qualifiedByName = "colaboradorId")
    EquipeColaboradorDTO toDto(EquipeColaborador s);

    @Named("equipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipeDTO toDtoEquipeId(Equipe equipe);

    @Named("colaboradorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ColaboradorDTO toDtoColaboradorId(Colaborador colaborador);
}
