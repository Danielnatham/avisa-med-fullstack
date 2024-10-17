package br.ufs.avisamed.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.ufs.avisamed.domain.Ocorrencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OcorrenciaDTO implements Serializable {

    private Long id;

    private Integer idSolicitante;

    @NotNull
    private Integer idDepartamento;

    @NotNull
    private Instant dataCriacao;

    @NotNull
    private Instant dataResolucao;

    @NotNull
    @Size(max = 50)
    private String titulo;

    @NotNull
    @Size(max = 200)
    private String descricao;

    @NotNull
    @Size(max = 100)
    private String imagem;

    @NotNull
    @Size(max = 10)
    private String situacao;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer complexidade;

    @NotNull
    @Size(max = 12)
    private String protocolo;

    private CidadaoIdentificadoDTO cidadaoIdentificado;

    private DepartamentoDTO departamento;

    private EquipeDTO equipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(Instant dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(Integer complexidade) {
        this.complexidade = complexidade;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public CidadaoIdentificadoDTO getCidadaoIdentificado() {
        return cidadaoIdentificado;
    }

    public void setCidadaoIdentificado(CidadaoIdentificadoDTO cidadaoIdentificado) {
        this.cidadaoIdentificado = cidadaoIdentificado;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public EquipeDTO getEquipe() {
        return equipe;
    }

    public void setEquipe(EquipeDTO equipe) {
        this.equipe = equipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcorrenciaDTO)) {
            return false;
        }

        OcorrenciaDTO ocorrenciaDTO = (OcorrenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ocorrenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcorrenciaDTO{" +
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
            ", cidadaoIdentificado=" + getCidadaoIdentificado() +
            ", departamento=" + getDepartamento() +
            ", equipe=" + getEquipe() +
            "}";
    }
}
