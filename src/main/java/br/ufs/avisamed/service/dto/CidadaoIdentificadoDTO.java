package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.CidadaoIdentificado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadaoIdentificadoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idUsuario;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadaoIdentificadoDTO)) {
            return false;
        }

        CidadaoIdentificadoDTO cidadaoIdentificadoDTO = (CidadaoIdentificadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadaoIdentificadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadaoIdentificadoDTO{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
