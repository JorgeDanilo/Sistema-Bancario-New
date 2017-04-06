package br.com.sistemabancario.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa extends EntidadeBanco {

	private static final long serialVersionUID = 1030244923614760581L;

	@Id
	@GeneratedValue
	@Column(name = "id_pessoa")
	private Long identificador;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "telefone", nullable = true)
	private String telefone;

	@Column(name = "endereco", nullable = true)
	private String endereco;

	@Column(name = "email", nullable = true)
	private String email;

	@Past(message = "Data futura não é permitida")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNascimento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCadastro;

	public Pessoa() {
		
	}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}

	@Override
	public Long getIdentificador() {
		return identificador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

}
