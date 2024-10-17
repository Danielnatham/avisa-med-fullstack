package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A EquipeColaborador.
 */
@Entity
@Table(name = "equipe_colaborador")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipeColaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_colaborador", nullable = false)
    private Integer idColaborador;

    @NotNull
    @Column(name = "id_equipe", nullable = false)
    private Integer idEquipe;

    @JsonIgnoreProperties(value = { "ocorrencias", "equipeColaborador", "gestorDepartamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Equipe equipe;

    @JsonIgnoreProperties(value = { "usuario", "gestorDepartamento", "gestorHu", "equipeColaborador", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Colaborador colaborador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EquipeColaborador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdColaborador() {
        return this.idColaborador;
    }

    public EquipeColaborador idColaborador(Integer idColaborador) {
        this.setIdColaborador(idColaborador);
        return this;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdEquipe() {
        return this.idEquipe;
    }

    public EquipeColaborador idEquipe(Integer idEquipe) {
        this.setIdEquipe(idEquipe);
        return this;
    }

    public void setIdEquipe(Integer idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public EquipeColaborador equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public EquipeColaborador colaborador(Colaborador colaborador) {
        this.setColaborador(colaborador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipeColaborador)) {
            return false;
        }
        return getId() != null && getId().equals(((EquipeColaborador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipeColaborador{" +
            "id=" + getId() +
            ", idColaborador=" + getIdColaborador() +
            ", idEquipe=" + getIdEquipe() +
            "}";
    }
}
