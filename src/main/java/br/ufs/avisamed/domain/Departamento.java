package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_gestor_departamento", nullable = false)
    private Integer idGestorDepartamento;

    @NotNull
    @Size(max = 30)
    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 200)
    @Column(name = "descricao", length = 200, nullable = false)
    private String descricao;

    @JsonIgnoreProperties(value = { "colaborador", "equipes", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private GestorDepartamento gestorDepartamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @JsonIgnoreProperties(value = { "cidadaoIdentificado", "departamento", "equipe" }, allowSetters = true)
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @JsonIgnoreProperties(value = { "usuario", "gestorDepartamento", "gestorHu", "equipeColaborador", "departamento" }, allowSetters = true)
    private Set<Colaborador> colaboradors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdGestorDepartamento() {
        return this.idGestorDepartamento;
    }

    public Departamento idGestorDepartamento(Integer idGestorDepartamento) {
        this.setIdGestorDepartamento(idGestorDepartamento);
        return this;
    }

    public void setIdGestorDepartamento(Integer idGestorDepartamento) {
        this.idGestorDepartamento = idGestorDepartamento;
    }

    public String getNome() {
        return this.nome;
    }

    public Departamento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Departamento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GestorDepartamento getGestorDepartamento() {
        return this.gestorDepartamento;
    }

    public void setGestorDepartamento(GestorDepartamento gestorDepartamento) {
        this.gestorDepartamento = gestorDepartamento;
    }

    public Departamento gestorDepartamento(GestorDepartamento gestorDepartamento) {
        this.setGestorDepartamento(gestorDepartamento);
        return this;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setDepartamento(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setDepartamento(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public Departamento ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public Departamento addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setDepartamento(this);
        return this;
    }

    public Departamento removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setDepartamento(null);
        return this;
    }

    public Set<Colaborador> getColaboradors() {
        return this.colaboradors;
    }

    public void setColaboradors(Set<Colaborador> colaboradors) {
        if (this.colaboradors != null) {
            this.colaboradors.forEach(i -> i.setDepartamento(null));
        }
        if (colaboradors != null) {
            colaboradors.forEach(i -> i.setDepartamento(this));
        }
        this.colaboradors = colaboradors;
    }

    public Departamento colaboradors(Set<Colaborador> colaboradors) {
        this.setColaboradors(colaboradors);
        return this;
    }

    public Departamento addColaborador(Colaborador colaborador) {
        this.colaboradors.add(colaborador);
        colaborador.setDepartamento(this);
        return this;
    }

    public Departamento removeColaborador(Colaborador colaborador) {
        this.colaboradors.remove(colaborador);
        colaborador.setDepartamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Departamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", idGestorDepartamento=" + getIdGestorDepartamento() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
