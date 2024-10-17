package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.GestorDepartamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestorDepartamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    private ColaboradorDTO colaborador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(o instanceof GestorDepartamentoDTO)) {
            return false;
        }

        GestorDepartamentoDTO gestorDepartamentoDTO = (GestorDepartamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gestorDepartamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestorDepartamentoDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", colaborador=" + getColaborador() +
            "}";
    }
}
