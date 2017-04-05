package br.com.sistemabancario.controllers;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.services.ClienteService;
import br.com.sistemabancario.services.ContaService;
import br.com.sistemabancario.services.TransacaoService;
import br.com.sistemabancario.util.FacesMessages;

@Named
@SessionScoped
public class ManterClienteBean implements Serializable {

	private static final long serialVersionUID = -56239607509019335L;
	
	@Inject
	private FacesMessages messages;
	
	private Cliente cliente = new Cliente();
	
	@Inject
	private ClienteService service;
	
	@Inject
	private ContaService contaService;
	
	@Inject
	private OperacoesBancariasBean operacoesBancariasBean = new OperacoesBancariasBean();
	
	@Inject
	private TransacaoService transacaoService;
	
	private Collection<Cliente> clientesCadastrados;
	
	private Cliente ClienteSelecionado;

	private Conta contaCliente = new Conta();
	
	private Collection<Transacao> transacaoConta;
	
	private static final String MSG_ALTERADO = "Cliente alterado com sucesso";

	private static final String MSG_CADASTRO = "Cliente cadastrado com sucesso";
	
	@PostConstruct
	private void inicializaLista() {
		
		this.clientesCadastrados = this.getService().listaTodosClientes();
		
		this.cliente = new Cliente();
	}
	
	
	
	/**
	 * Método responsavel por salvar a entidade cliente.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void salvar() {
		
		if ( this.cliente.getIdentificador() == null ) {
				
			this.service.salvar(this.cliente);
				
			messages.info(MSG_CADASTRO);
			
		} else {
				
			this.service.atualizar(this.cliente);
			
			messages.info(MSG_ALTERADO);
		}
			
			
		this.inicializaLista();
		
		this.limpar();
	}
	
	
	/**
	 * Método responsável por excluir a entidade cliente.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void excluir() {
		
		this.service.excluir(this.cliente);
		
		this.limpar();
		
		this.inicializaLista();
	}

	
	/**
	 *  Método responsável por buscar o a conta do cliente.
	 */
	public void inicializarOperacoesBancarias() {
		
		this.contaCliente = this.contaService.buscarContaPorCliente(ClienteSelecionado);
		
		if ( ClienteSelecionado != null && this.contaCliente != null ) {
			
			this.operacoesBancariasBean.setClienteSelecionado(ClienteSelecionado);
			
			this.operacoesBancariasBean.setContaCliente(new Conta());
			
			this.setTransacaoConta(this.transacaoService.listarTransacoesPorConta(contaCliente.getIdentificador()));
			
		}
		
	}
	
	/**
	 * Método responsável por limpar os dados.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void limpar() {
		
		this.cliente = new Cliente();
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Collection<Cliente> getClientesCadastrados() {
		return clientesCadastrados;
	}


	public void setClientesCadastrados(Collection<Cliente> clientesCadastrados) {
		this.clientesCadastrados = clientesCadastrados;
	}


	public ClienteService getService() {
		return service;
	}


	public Cliente getClienteSelecionado() {
		return ClienteSelecionado;
	}


	public void setClienteSelecionado(Cliente clienteSelecionado) {
		ClienteSelecionado = clienteSelecionado;
	}


	public Conta getContaCliente() {
		return contaCliente;
	}


	public void setContaCliente(Conta contaCliente) {
		this.contaCliente = contaCliente;
	}


	public Collection<Transacao> getTransacaoConta() {
		return transacaoConta;
	}


	public void setTransacaoConta(Collection<Transacao> transacaoConta) {
		this.transacaoConta = transacaoConta;
	}
	

}
