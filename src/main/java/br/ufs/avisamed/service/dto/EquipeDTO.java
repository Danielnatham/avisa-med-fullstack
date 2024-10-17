package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.Equipe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idOcorrencia;

    @NotNull
    private Integer idG;

    @NotNull
    private Instant dataAtribuicao;

    @NotNull
    private Instant dataFinalizacao;

    private GestorDepartamentoDTO gestorDepartamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Integer getIdG() {
        return idG;
    }

    public void setIdG(Integer idG) {
        this.idG = idG;
    }

    public Instant getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Instant getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Instant dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public GestorDepartamentoDTO getGestorDepartamento() {
        return gestorDepartamento;
    }

    public void setGestorDepartamento(GestorDepartamentoDTO gestorDepartamento) {
        this.gestorDepartamento = gestorDepartamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipeDTO)) {
            return false;
        }

        EquipeDTO equipeDTO = (EquipeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipeDTO{" +
            "id=" + getId() +
            ", idOcorrencia=" + getIdOcorrencia() +
            ", idG=" + getIdG() +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            ", dataFinalizacao='" + getDataFinalizacao() + "'" +
            ", gestorDepartamento=" + getGestorDepartamento() +
            "}";
    }
}
