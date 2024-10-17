package br.ufs.avisamed.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 50)
    @Column(name = "telefone", length = 50, nullable = false)
    private String telefone;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Size(max = 320)
    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @NotNull
    @Size(max = 14)
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    @NotNull
    @Size(max = 50)
    @Column(name = "senha", length = 50, nullable = false)
    private String senha;

    @JsonIgnoreProperties(value = { "usuario", "gestorDepartamento", "gestorHu", "equipeColaborador", "departamento" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
    private Colaborador colaborador;

    @JsonIgnoreProperties(value = { "usuario", "ocorrencias" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
    private CidadaoIdentificado cidadaoIdentificado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Usuario nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Usuario telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Usuario dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return this.email;
    }

    public Usuario email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Usuario cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return this.senha;
    }

    public Usuario senha(String senha) {
        this.setSenha(senha);
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        if (this.colaborador != null) {
            this.colaborador.setUsuario(null);
        }
        if (colaborador != null) {
            colaborador.setUsuario(this);
        }
        this.colaborador = colaborador;
    }

    public Usuario colaborador(Colaborador colaborador) {
        this.setColaborador(colaborador);
        return this;
    }

    public CidadaoIdentificado getCidadaoIdentificado() {
        return this.cidadaoIdentificado;
    }

    public void setCidadaoIdentificado(CidadaoIdentificado cidadaoIdentificado) {
        if (this.cidadaoIdentificado != null) {
            this.cidadaoIdentificado.setUsuario(null);
        }
        if (cidadaoIdentificado != null) {
            cidadaoIdentificado.setUsuario(this);
        }
        this.cidadaoIdentificado = cidadaoIdentificado;
    }

    public Usuario cidadaoIdentificado(CidadaoIdentificado cidadaoIdentificado) {
        this.setCidadaoIdentificado(cidadaoIdentificado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return getId() != null && getId().equals(((Usuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", email='" + getEmail() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", senha='" + getSenha() + "'" +
            "}";
    }
}
