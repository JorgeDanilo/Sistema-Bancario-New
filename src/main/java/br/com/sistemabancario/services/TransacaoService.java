package br.com.sistemabancario.services;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.util.Transacional;

public class TransacaoService implements Serializable {

	private static final long serialVersionUID = 8831867423603577817L;
	
	@Inject
	private EntityManager manager;
	
	@Transacional
	public void salvar(Transacao transacao) {
		this.manager.merge(transacao);
	}

}
