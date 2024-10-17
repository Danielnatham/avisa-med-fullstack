package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A GestorHu.
 */
@Entity
@Table(name = "gestor_hu")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GestorHu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_colaborador", nullable = false)
    private Integer idColaborador;

    @JsonIgnoreProperties(value = { "usuario", "gestorDepartamento", "gestorHu", "equipeColaborador", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Colaborador colaborador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GestorHu id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdColaborador() {
        return this.idColaborador;
    }

    public GestorHu idColaborador(Integer idColaborador) {
        this.setIdColaborador(idColaborador);
        return this;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public GestorHu colaborador(Colaborador colaborador) {
        this.setColaborador(colaborador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestorHu)) {
            return false;
        }
        return getId() != null && getId().equals(((GestorHu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestorHu{" +
            "id=" + getId() +
            ", idColaborador=" + getIdColaborador() +
            "}";
    }
}
