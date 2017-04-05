package br.com.sistemabancario.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.modelo.Conta;

public class ContaDao implements Dao<Conta>, Serializable {

	private static final long serialVersionUID = 2350508688817379771L;

	@Inject
	private EntityManager manager;

	
	@Override
	public Conta obter(Long identificador) {
		return this.manager.find(Conta.class, identificador);
	}
	
	@Override
	public Conta salvar(Conta entidade) {
		return this.manager.merge(entidade);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Conta> listar() {
		Session session = (Session) this.manager.getDelegate();
		Criteria criteria = session.createCriteria(Conta.class);
		return criteria.list();
	}
	
	@Override
	public void remover(Conta entidade) {
		this.obter(entidade.getIdentificador());
		this.manager.remove(entidade);
	}
	
}
