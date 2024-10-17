package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_ocorrencia", nullable = false)
    private Integer idOcorrencia;

    @NotNull
    @Column(name = "id_g", nullable = false)
    private Integer idG;

    @NotNull
    @Column(name = "data_atribuicao", nullable = false)
    private Instant dataAtribuicao;

    @NotNull
    @Column(name = "data_finalizacao", nullable = false)
    private Instant dataFinalizacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipe")
    @JsonIgnoreProperties(value = { "cidadaoIdentificado", "departamento", "equipe" }, allowSetters = true)
    private Set<Ocorrencia> ocorrencias = new HashSet<>();

    @JsonIgnoreProperties(value = { "equipe", "colaborador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "equipe")
    private EquipeColaborador equipeColaborador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "colaborador", "equipes", "departamento" }, allowSetters = true)
    private GestorDepartamento gestorDepartamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Equipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdOcorrencia() {
        return this.idOcorrencia;
    }

    public Equipe idOcorrencia(Integer idOcorrencia) {
        this.setIdOcorrencia(idOcorrencia);
        return this;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Integer getIdG() {
        return this.idG;
    }

    public Equipe idG(Integer idG) {
        this.setIdG(idG);
        return this;
    }

    public void setIdG(Integer idG) {
        this.idG = idG;
    }

    public Instant getDataAtribuicao() {
        return this.dataAtribuicao;
    }

    public Equipe dataAtribuicao(Instant dataAtribuicao) {
        this.setDataAtribuicao(dataAtribuicao);
        return this;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Instant getDataFinalizacao() {
        return this.dataFinalizacao;
    }

    public Equipe dataFinalizacao(Instant dataFinalizacao) {
        this.setDataFinalizacao(dataFinalizacao);
        return this;
    }

    public void setDataFinalizacao(Instant dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public Set<Ocorrencia> getOcorrencias() {
        return this.ocorrencias;
    }

    public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
        if (this.ocorrencias != null) {
            this.ocorrencias.forEach(i -> i.setEquipe(null));
        }
        if (ocorrencias != null) {
            ocorrencias.forEach(i -> i.setEquipe(this));
        }
        this.ocorrencias = ocorrencias;
    }

    public Equipe ocorrencias(Set<Ocorrencia> ocorrencias) {
        this.setOcorrencias(ocorrencias);
        return this;
    }

    public Equipe addOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.add(ocorrencia);
        ocorrencia.setEquipe(this);
        return this;
    }

    public Equipe removeOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencias.remove(ocorrencia);
        ocorrencia.setEquipe(null);
        return this;
    }

    public EquipeColaborador getEquipeColaborador() {
        return this.equipeColaborador;
    }

    public void setEquipeColaborador(EquipeColaborador equipeColaborador) {
        if (this.equipeColaborador != null) {
            this.equipeColaborador.setEquipe(null);
        }
        if (equipeColaborador != null) {
            equipeColaborador.setEquipe(this);
        }
        this.equipeColaborador = equipeColaborador;
    }

    public Equipe equipeColaborador(EquipeColaborador equipeColaborador) {
        this.setEquipeColaborador(equipeColaborador);
        return this;
    }

    public GestorDepartamento getGestorDepartamento() {
        return this.gestorDepartamento;
    }

    public void setGestorDepartamento(GestorDepartamento gestorDepartamento) {
        this.gestorDepartamento = gestorDepartamento;
    }

    public Equipe gestorDepartamento(GestorDepartamento gestorDepartamento) {
        this.setGestorDepartamento(gestorDepartamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", idOcorrencia=" + getIdOcorrencia() +
            ", idG=" + getIdG() +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            ", dataFinalizacao='" + getDataFinalizacao() + "'" +
            "}";
    }
}
