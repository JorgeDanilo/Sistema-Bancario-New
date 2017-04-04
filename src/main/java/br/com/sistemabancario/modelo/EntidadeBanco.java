package br.com.sistemabancario.modelo;

import java.io.Serializable;

public abstract class EntidadeBanco implements Entidade, Serializable {

	private static final long serialVersionUID = -6737725299424213433L;
	
	public abstract Long getIdentificador();

}
