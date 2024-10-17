package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Colaborador.
 */
@Entity
@Table(name = "colaborador")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Colaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_departamento", nullable = false)
    private Integer idDepartamento;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @JsonIgnoreProperties(value = { "colaborador", "cidadaoIdentificado" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Usuario usuario;

    @JsonIgnoreProperties(value = { "colaborador", "equipes", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "colaborador")
    private GestorDepartamento gestorDepartamento;

    @JsonIgnoreProperties(value = { "colaborador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "colaborador")
    private GestorHu gestorHu;

    @JsonIgnoreProperties(value = { "equipe", "colaborador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "colaborador")
    private EquipeColaborador equipeColaborador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "gestorDepartamento", "ocorrencias", "colaboradors" }, allowSetters = true)
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Colaborador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDepartamento() {
        return this.idDepartamento;
    }

    public Colaborador idDepartamento(Integer idDepartamento) {
        this.setIdDepartamento(idDepartamento);
        return this;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public Colaborador idUsuario(Integer idUsuario) {
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

    public Colaborador usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public GestorDepartamento getGestorDepartamento() {
        return this.gestorDepartamento;
    }

    public void setGestorDepartamento(GestorDepartamento gestorDepartamento) {
        if (this.gestorDepartamento != null) {
            this.gestorDepartamento.setColaborador(null);
        }
        if (gestorDepartamento != null) {
            gestorDepartamento.setColaborador(this);
        }
        this.gestorDepartamento = gestorDepartamento;
    }

    public Colaborador gestorDepartamento(GestorDepartamento gestorDepartamento) {
        this.setGestorDepartamento(gestorDepartamento);
        return this;
    }

    public GestorHu getGestorHu() {
        return this.gestorHu;
    }

    public void setGestorHu(GestorHu gestorHu) {
        if (this.gestorHu != null) {
            this.gestorHu.setColaborador(null);
        }
        if (gestorHu != null) {
            gestorHu.setColaborador(this);
        }
        this.gestorHu = gestorHu;
    }

    public Colaborador gestorHu(GestorHu gestorHu) {
        this.setGestorHu(gestorHu);
        return this;
    }

    public EquipeColaborador getEquipeColaborador() {
        return this.equipeColaborador;
    }

    public void setEquipeColaborador(EquipeColaborador equipeColaborador) {
        if (this.equipeColaborador != null) {
            this.equipeColaborador.setColaborador(null);
        }
        if (equipeColaborador != null) {
            equipeColaborador.setColaborador(this);
        }
        this.equipeColaborador = equipeColaborador;
    }

    public Colaborador equipeColaborador(EquipeColaborador equipeColaborador) {
        this.setEquipeColaborador(equipeColaborador);
        return this;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Colaborador departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaborador)) {
            return false;
        }
        return getId() != null && getId().equals(((Colaborador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colaborador{" +
            "id=" + getId() +
            ", idDepartamento=" + getIdDepartamento() +
            ", idUsuario=" + getIdUsuario() +
            "}";
    }
}
