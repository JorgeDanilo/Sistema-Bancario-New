package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import br.com.sistemabancario.dao.ClienteDao;
import br.com.sistemabancario.excecoes.CamposObrigatoriosException;
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
	private ClienteDao clienteDao;
	
	/**
	 * Metódo responsável por obter o cliente a partir do seu identificador.
	 * @author Jorge Danilo Gomes da Silva
	 * @param identificador
	 * @return Cliente
	 */
	public Cliente obter( Long identificador ) {
		return this.getClienteDao().obter(identificador);
	}

	
	/**
	 * Método responsável por salvar a entidade cliente.
	 * @author Jorge Danilo Gomes da Silva 
	 * @param cliente
	 * @throws CamposObrigatoriosException 
	 */
	@Transacional
	public void salvar(Cliente cliente) throws CamposObrigatoriosException {
		this.validarCamposObrigatorios(cliente);
		cliente.setDtCadastro(new Date());
		this.getClienteDao().salvar(cliente);
		
	}
	

	/**
	 * Metodo responsável por listar todos os clientes.
	 * @author Jorge Danilo Gomes da Silva
	 * @return Collection<Cliente>
	 */
	public Collection<Cliente> listaTodosClientes() {
		return this.getClienteDao().listar();
	}


	/**
	 * Método responsável por atualizar a entidade cliente.
	 * @author Jorge Danilo Gomes da Silva
	 * @param cliente
	 * @throws CamposObrigatoriosException 
	 */
	@Transacional
	public void atualizar(Cliente cliente) throws CamposObrigatoriosException {
		this.validarCamposObrigatorios(cliente);
		this.getClienteDao().salvar(cliente);
	}

	/**
	 * Método responsável por excluir um cliente.
	 * @author Jorge Danilo Gomes da Silva
	 * @param cliente
	 */
	@Transacional
	public void excluir(Cliente cliente) {
		this.getClienteDao().remover(cliente);
	}

	private void validarCamposObrigatorios(Cliente cliente) throws CamposObrigatoriosException {
		
		if (cliente == null || cliente.getNome().equals("")
				|| cliente.getCpf().replace("-", "").replace(".", "").trim().equals("") 
				|| cliente.getDtNascimento().equals("") || cliente.getEndereco().equals("") 
				|| cliente.getEmail().equals("") ) {
			
			throw new CamposObrigatoriosException();
		}
	}
	
	/**
	 * Responsável por retornar a instancia do objeto ClienteDao.
	 * @return ClienteDao
	 */
	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	
}
