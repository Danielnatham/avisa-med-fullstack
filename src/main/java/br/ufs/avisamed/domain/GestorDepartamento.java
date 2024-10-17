package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GestorDepartamento.
 */
@Entity
@Table(name = "gestor_departamento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestorDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @JsonIgnoreProperties(value = { "usuario", "gestorDepartamento", "gestorHu", "equipeColaborador", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Colaborador colaborador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gestorDepartamento")
    @JsonIgnoreProperties(value = { "ocorrencias", "equipeColaborador", "gestorDepartamento" }, allowSetters = true)
    private Set<Equipe> equipes = new HashSet<>();

    @JsonIgnoreProperties(value = { "gestorDepartamento", "ocorrencias", "colaboradors" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gestorDepartamento")
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GestorDepartamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public GestorDepartamento titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public GestorDepartamento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public GestorDepartamento colaborador(Colaborador colaborador) {
        this.setColaborador(colaborador);
        return this;
    }

    public Set<Equipe> getEquipes() {
        return this.equipes;
    }

    public void setEquipes(Set<Equipe> equipes) {
        if (this.equipes != null) {
            this.equipes.forEach(i -> i.setGestorDepartamento(null));
        }
        if (equipes != null) {
            equipes.forEach(i -> i.setGestorDepartamento(this));
        }
        this.equipes = equipes;
    }

    public GestorDepartamento equipes(Set<Equipe> equipes) {
        this.setEquipes(equipes);
        return this;
    }

    public GestorDepartamento addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
        equipe.setGestorDepartamento(this);
        return this;
    }

    public GestorDepartamento removeEquipe(Equipe equipe) {
        this.equipes.remove(equipe);
        equipe.setGestorDepartamento(null);
        return this;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        if (this.departamento != null) {
            this.departamento.setGestorDepartamento(null);
        }
        if (departamento != null) {
            departamento.setGestorDepartamento(this);
        }
        this.departamento = departamento;
    }

    public GestorDepartamento departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestorDepartamento)) {
            return false;
        }
        return getId() != null && getId().equals(((GestorDepartamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestorDepartamento{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
