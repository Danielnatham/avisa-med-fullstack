package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.EquipeColaborador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipeColaboradorDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idColaborador;

    @NotNull
    private Integer idEquipe;

    private EquipeDTO equipe;

    private ColaboradorDTO colaborador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public EquipeDTO getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeDTO equipe) {
        this.equipe = equipe;
    }

    public ColaboradorDTO getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorDTO colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipeColaboradorDTO)) {
            return false;
        }

        EquipeColaboradorDTO equipeColaboradorDTO = (EquipeColaboradorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipeColaboradorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipeColaboradorDTO{" +
            "id=" + getId() +
            ", idColaborador=" + getIdColaborador() +
            ", idEquipe=" + getIdEquipe() +
            ", equipe=" + getEquipe() +
            ", colaborador=" + getColaborador() +
            "}";
    }
}
