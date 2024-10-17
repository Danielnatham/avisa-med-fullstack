package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.Colaborador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ColaboradorDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idDepartamento;

    @NotNull
    private Integer idUsuario;

    private UsuarioDTO usuario;

    private DepartamentoDTO departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColaboradorDTO)) {
            return false;
        }

        ColaboradorDTO colaboradorDTO = (ColaboradorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, colaboradorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ColaboradorDTO{" +
            "id=" + getId() +
            ", idDepartamento=" + getIdDepartamento() +
            ", idUsuario=" + getIdUsuario() +
            ", usuario=" + getUsuario() +
            ", departamento=" + getDepartamento() +
            "}";
    }
}
