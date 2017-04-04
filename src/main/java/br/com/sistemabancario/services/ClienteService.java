package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.util.Transacional;


/**
 * Classe de Serviço Cliente. 
 * 
 * @author Jorge Danilo Gomes da Silva.
 *
 */
public class ClienteService implements Serializable {
	
	private static final long serialVersionUID = 3879189537901714457L;
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Metódo responsável por obter o cliente a partir do seu identificador.
	 * @author Jorge Danilo Gomes da Silva
	 * @param identificador
	 * @return Cliente
	 */
	public Cliente obter( Long identificador ) {
		
		return manager.find(Cliente.class, identificador);
	}

	/**
	 * Metodo responsável por listar todos os clientes.
	 * @author Jorge Danilo Gomes da Silva
	 * @return Collection<Cliente>
	 */
	public Collection<Cliente> listaTodosClientes() {
		
		Session session = (Session) this.manager.getDelegate();
		
		Criteria criteria = session.createCriteria(Cliente.class);
		
		return criteria.list();
	}

	/**
	 * Método responsável por salvar a entidade cliente.
	 * @author Jorge Danilo Gomes da Silva 
	 * @param cliente
	 */
	@Transacional
	public void salvar(Cliente cliente) {
		
		cliente.setDtCadastro(new Date());
		
		this.manager.persist(cliente);
	}

	/**
	 * Método responsável por atualizar a entidade cliente.
	 * @author Jorge Danilo Gomes da Silva
	 * @param cliente
	 */
	@Transacional
	public void atualizar(Cliente cliente) {
		
		this.manager.merge(cliente);
	}

	/**
	 * Método responsável por excluir um cliente.
	 * @author Jorge Danilo Gomes da Silva
	 * @param cliente
	 */
	public void excluir(Cliente cliente) {
		
		this.obter(cliente.getIdentificador());
		
		this.manager.remove(cliente);
	}

}
