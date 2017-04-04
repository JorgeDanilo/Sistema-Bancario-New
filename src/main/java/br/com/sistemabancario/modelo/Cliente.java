package br.com.sistemabancario.modelo;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Cliente extends Pessoa implements Comparable<Cliente> {

	private static final long serialVersionUID = 148437290978551297L;

	private String cpf;

	public Cliente() {
		
	}

	public Cliente(String nome, String cpf) {

		super(nome);

		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
	@Override
	public int compareTo(Cliente c) {
		return getNome().compareTo(c.getNome());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

}
