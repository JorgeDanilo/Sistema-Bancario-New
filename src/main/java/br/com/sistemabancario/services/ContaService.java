package br.com.sistemabancario.services;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.util.Transacional;

public class ContaService implements Serializable {

	private static final long serialVersionUID = -3052031878124202391L;
	
	@Inject
	private EntityManager manager;
	
	@Transacional
	public void salvar(Conta conta) {
		
		this.manager.persist(conta);
	}

}
