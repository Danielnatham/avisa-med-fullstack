package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.GestorHu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestorHuDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idColaborador;

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
        if (!(o instanceof GestorHuDTO)) {
            return false;
        }

        GestorHuDTO gestorHuDTO = (GestorHuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gestorHuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestorHuDTO{" +
            "id=" + getId() +
            ", idColaborador=" + getIdColaborador() +
            ", colaborador=" + getColaborador() +
            "}";
    }
}
