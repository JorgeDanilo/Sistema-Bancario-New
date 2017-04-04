package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.modelo.Cliente;
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

	
	/**
	 * Método responsável por buscar conta pelo cliente.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param cliente
	 * @return conta
	 */
	public Conta buscarContaPorCliente(Cliente cliente) {
		
		for (Conta  conta : this.listar()) {
			
			if (conta.getTitular().equals(cliente)) {
				
				return conta;
			}
		}
		
		return null;
	}
	
	public Collection<Conta> listar() {
		
		Session session = (Session) manager.getDelegate();
		
		Criteria criteria = session.createCriteria(Conta.class);
		
		return criteria.list();
	}

}
