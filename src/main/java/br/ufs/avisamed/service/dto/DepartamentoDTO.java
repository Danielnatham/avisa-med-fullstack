package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.Departamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idGestorDepartamento;

    @NotNull
    @Size(max = 30)
    private String nome;

    @NotNull
    @Size(max = 200)
    private String descricao;

    private GestorDepartamentoDTO gestorDepartamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdGestorDepartamento() {
        return idGestorDepartamento;
    }

    public void setIdGestorDepartamento(Integer idGestorDepartamento) {
        this.idGestorDepartamento = idGestorDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(o instanceof DepartamentoDTO)) {
            return false;
        }

        DepartamentoDTO departamentoDTO = (DepartamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoDTO{" +
            "id=" + getId() +
            ", idGestorDepartamento=" + getIdGestorDepartamento() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", gestorDepartamento=" + getGestorDepartamento() +
            "}";
    }
}
