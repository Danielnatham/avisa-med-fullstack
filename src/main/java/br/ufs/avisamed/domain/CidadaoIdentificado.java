package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CidadaoIdentificado.
 */
@Entity
@Table(name = "cidadao_identificado")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadaoIdentificado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @JsonIgnoreProperties(value = { "colaborador", "cidadaoIdentificado" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidadaoIdentificado")
    @JsonIgnoreProperties(value = { "cidadaoIdentificado", "departamento", "equipe" }, allowSetters = true)
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CidadaoIdentificado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public CidadaoIdentificado idUsuario(Integer idUsuario) {
        this.setIdUsuario(idUsuario);
        return this;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CidadaoIdentificado usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setCidadaoIdentificado(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setCidadaoIdentificado(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public CidadaoIdentificado ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public CidadaoIdentificado addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setCidadaoIdentificado(this);
        return this;
    }

    public CidadaoIdentificado removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setCidadaoIdentificado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadaoIdentificado)) {
            return false;
        }
        return getId() != null && getId().equals(((CidadaoIdentificado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadaoIdentificado{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            "}";
    }
}
