package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Ocorrencia.
 */
@Entity
@Table(name = "ocorrencia")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_solicitante")
    private Integer idSolicitante;

    @NotNull
    @Column(name = "id_departamento", nullable = false)
    private Integer idDepartamento;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    private Instant dataCriacao;

    @NotNull
    @Column(name = "data_resolucao", nullable = false)
    private Instant dataResolucao;

    @NotNull
    @Size(max = 50)
    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @NotNull
    @Size(max = 200)
    @Column(name = "descricao", length = 200, nullable = false)
    private String descricao;

    @NotNull
    @Size(max = 100)
    @Column(name = "imagem", length = 100, nullable = false)
    private String imagem;

    @NotNull
    @Size(max = 10)
    @Column(name = "situacao", length = 10, nullable = false)
    private String situacao;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "complexidade", nullable = false)
    private Integer complexidade;

    @NotNull
    @Size(max = 12)
    @Column(name = "protocolo", length = 12, nullable = false)
    private String protocolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "usuario", "ocorrencias" }, allowSetters = true)
    private CidadaoIdentificado cidadaoIdentificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "gestorDepartamento", "ocorrencias", "colaboradors" }, allowSetters = true)
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ocorrencias", "equipeColaborador", "gestorDepartamento" }, allowSetters = true)
    private Equipe equipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ocorrencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSolicitante() {
        return this.idSolicitante;
    }

    public Ocorrencia idSolicitante(Integer idSolicitante) {
        this.setIdSolicitante(idSolicitante);
        return this;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Integer getIdDepartamento() {
        return this.idDepartamento;
    }

    public Ocorrencia idDepartamento(Integer idDepartamento) {
        this.setIdDepartamento(idDepartamento);
        return this;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Instant getDataCriacao() {
        return this.dataCriacao;
    }

    public Ocorrencia dataCriacao(Instant dataCriacao) {
        this.setDataCriacao(dataCriacao);
        return this;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataResolucao() {
        return this.dataResolucao;
    }

    public Ocorrencia dataResolucao(Instant dataResolucao) {
        this.setDataResolucao(dataResolucao);
        return this;
    }

    public void setDataResolucao(Instant dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Ocorrencia titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Ocorrencia descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return this.imagem;
    }

    public Ocorrencia imagem(String imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public Ocorrencia situacao(String situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getComplexidade() {
        return this.complexidade;
    }

    public Ocorrencia complexidade(Integer complexidade) {
        this.setComplexidade(complexidade);
        return this;
    }

    public void setComplexidade(Integer complexidade) {
        this.complexidade = complexidade;
    }

    public String getProtocolo() {
        return this.protocolo;
    }

    public Ocorrencia protocolo(String protocolo) {
        this.setProtocolo(protocolo);
        return this;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public CidadaoIdentificado getCidadaoIdentificado() {
        return this.cidadaoIdentificado;
    }

    public void setCidadaoIdentificado(CidadaoIdentificado cidadaoIdentificado) {
        this.cidadaoIdentificado = cidadaoIdentificado;
    }

    public Ocorrencia cidadaoIdentificado(CidadaoIdentificado cidadaoIdentificado) {
        this.setCidadaoIdentificado(cidadaoIdentificado);
        return this;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Ocorrencia departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Ocorrencia equipe(Equipe equipe) {
        this.setEquipe(equipe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ocorrencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Ocorrencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ocorrencia{" +
            "id=" + getId() +
            ", idSolicitante=" + getIdSolicitante() +
            ", idDepartamento=" + getIdDepartamento() +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", dataResolucao='" + getDataResolucao() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", complexidade=" + getComplexidade() +
            ", protocolo='" + getProtocolo() + "'" +
            "}";
    }
}
