package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.Colaborador;
import br.ufs.avisamed.domain.GestorHu;
import br.ufs.avisamed.service.dto.ColaboradorDTO;
import br.ufs.avisamed.service.dto.GestorHuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GestorHu} and its DTO {@link GestorHuDTO}.
 */
@Mapper(componentModel = "spring")
public interface GestorHuMapper extends EntityMapper<GestorHuDTO, GestorHu> {
    @Mapping(target = "colaborador", source = "colaborador", qualifiedByName = "colaboradorId")
    GestorHuDTO toDto(GestorHu s);

    @Named("colaboradorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ColaboradorDTO toDtoColaboradorId(Colaborador colaborador);
}
