package br.com.sistemabancario.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.modelo.Cliente;


/**
 * Classe para manipulação das entidades.
 * 
 * @author Jorge Danilo Gomes da Silva
 */
public class ClienteDao implements Dao<Cliente>, Serializable {

	private static final long serialVersionUID = 8980253129766932309L;
	
	@Inject
	private EntityManager manager;

	
	/**
	 * Obtem dados da entidade.
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 */
	@Override
	public Cliente obter(Long identificador) {
		return this.manager.find(Cliente.class, identificador);
	}

	
	/**
	 * Salva ou atualizar entidade
	 * @author Jorge Danilo Gomes da Silva
	 */
	@Override
	public Cliente salvar(Cliente entidade) {
		return this.manager.merge(entidade);
	}

	
	/**
	 * Lista todos os registros da entidade.
	 * @author Jorge Danilo Gomes da Silva
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Cliente> listar() {
		Session session = (Session) this.manager.getDelegate();
		Criteria criteria = session.createCriteria(Cliente.class);
		return criteria.list();
	}

	
	/**
	 * Método responsável por excluir um cliente.

	 * @author Jorge Danilo Gomes da Silva
	 */
	@Override
	public void remover(Cliente entidade) {
		this.obter(entidade.getIdentificador());
		this.manager.remove(entidade);
	}
	
	
}
