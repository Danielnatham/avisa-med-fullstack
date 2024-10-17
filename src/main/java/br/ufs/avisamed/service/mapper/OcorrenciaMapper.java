package br.ufs.avisamed.service.mapper;

import br.ufs.avisamed.domain.CidadaoIdentificado;
import br.ufs.avisamed.domain.Departamento;
import br.ufs.avisamed.domain.Equipe;
import br.ufs.avisamed.domain.Ocorrencia;
import br.ufs.avisamed.service.dto.CidadaoIdentificadoDTO;
import br.ufs.avisamed.service.dto.DepartamentoDTO;
import br.ufs.avisamed.service.dto.EquipeDTO;
import br.ufs.avisamed.service.dto.OcorrenciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ocorrencia} and its DTO {@link OcorrenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface OcorrenciaMapper extends EntityMapper<OcorrenciaDTO, Ocorrencia> {
    @Mapping(target = "cidadaoIdentificado", source = "cidadaoIdentificado", qualifiedByName = "cidadaoIdentificadoId")
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoId")
    @Mapping(target = "equipe", source = "equipe", qualifiedByName = "equipeId")
    OcorrenciaDTO toDto(Ocorrencia s);

    @Named("cidadaoIdentificadoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CidadaoIdentificadoDTO toDtoCidadaoIdentificadoId(CidadaoIdentificado cidadaoIdentificado);

    @Named("departamentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartamentoDTO toDtoDepartamentoId(Departamento departamento);

    @Named("equipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EquipeDTO toDtoEquipeId(Equipe equipe);
}
