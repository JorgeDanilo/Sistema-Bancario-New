package br.com.sistemabancario.modelo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Banco extends EntidadeBanco {

	private static final long serialVersionUID = -3796561563626216293L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_banco")
	private Long identificador;

	private int numero;

	private String nome;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "banco", cascade = CascadeType.ALL)
	private Collection<Agencia> agencias;

	public Banco() {

		agencias = new ArrayList<Agencia>();
	}

	public Banco(int numero) {

		this();

		this.numero = numero;

	}

	@Override
	public Long getIdentificador() {
		return identificador;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Agencia> getAgencias() {
		return agencias;
	}

	public void setAgencias(Collection<Agencia> agencias) {
		this.agencias = agencias;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

}
