package br.com.sistemabancario.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemabancario.excecoes.ContaNaoExisteException;
import br.com.sistemabancario.excecoes.SaldoInsuficienteException;
import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.services.ContaService;
import br.com.sistemabancario.util.FacesMessages;

@Named
@SessionScoped
public class OperacoesBancariasBean implements Serializable {

	private static final long serialVersionUID = -8707024590728060564L;
	
	@Inject
	private FacesMessages messages;
	
	@Inject
	private ContaService contaService;
	
	private Conta contaCliente = new Conta();
	
	private Cliente clienteSelecionado;
	
	private Double valorSaque;
	
	private Double valorDeposito;
	
	private Double valorTransferencia;
	
	private int numeroContaDeposito;
	
	
	/**
	 * Método responsavel por abrir uma conta para o usuario.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void abrirConta() {
		
		this.contaCliente.setTitular(clienteSelecionado);
		
		this.contaService.salvar(contaCliente);
		
		messages.info("Conta criada com Sucesso !");
		
		this.inicializarOperacoesConta();
		
	}

	
	
	/**
	 * Método responsável por efetuar o saque da conta.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void efetuarSaque() {

		if (valorSaque != null && valorSaque > 0) {

			try {
				
				this.contaCliente = this.contaService.buscarContaPorCliente(clienteSelecionado);

				this.contaService.sacar(contaCliente, valorSaque);

				this.inicializarOperacoesConta();

				messages.info("Saque realizado com sucesso");
				
			} catch (SaldoInsuficienteException e) {
				
				messages.error(e.getMessage());
			}

		} else {

			messages.error("Valor válido não informado!");
		}
	}
	
	
	/**
	 * Método responsável por realizar a ação de deposito.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void efetuarDeposito() {
		
		if( valorDeposito != null && valorDeposito > 0 ) {
			
			try {
				
				Conta contaDestino = this.contaService.buscarContaPorNumero(numeroContaDeposito);
				
				Conta clienteOrigem = this.contaService.buscarContaPorCliente(clienteSelecionado);
				
				this.contaService.depositar(clienteOrigem, contaDestino, valorDeposito);
				
				this.inicializarOperacoesConta();
				
				messages.info("Depósito Realizado com sucesso");
				
			} catch ( ContaNaoExisteException ex ) {
				
				messages.error(ex.getMessage());
						
			} 
			
		} else {
			
			messages.error("Valor válido não informado");
		}
		
	}
	
	
	/**
	 * Método responsável por realizar a transferencia entre contas.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void realizarTransferencia() {
		
		if( valorTransferencia != null && valorTransferencia > 0 ) {
			
			try {
				
				Conta contaDestinoTransferencia = this.contaService.buscarContaPorNumero(this.numeroContaDeposito);
				
				this.contaCliente = this.contaService.buscarContaPorNumero(this.contaCliente.getNumero());
				
				this.contaService.transferir(contaCliente, valorTransferencia, contaDestinoTransferencia);
				
				this.inicializarOperacoesConta();
				
				messages.info("TransferÊncia realizada com sucesso!");
				
			} catch (ContaNaoExisteException e) {
				
				messages.error(e.getMessage());
				
			} catch (SaldoInsuficienteException ex) {
				
				messages.error(ex.getMessage());
			}
			
		} else {
			
			messages.error("Valor válido não informado!");
		}
		
	}
	
	
	/**
	 * Método responsável por iniciar as operações bancárias.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	private void inicializarOperacoesConta() {
		
		this.contaCliente = this.contaService.buscarContaPorCliente(clienteSelecionado);
		
		this.valorSaque = 0D;
		
		this.valorDeposito = 0D;
		
		
	}

	
	/**
	 * Método responsável por popular o objeto Cliente.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 */
	public void inicializaDadosCliente() {
		
		this.contaCliente = this.contaService.buscarContaPorCliente(clienteSelecionado);
		
		System.out.println(this.contaCliente);
	}
	
	
	public Conta getContaCliente() {
		return contaCliente;
	}

	public void setContaCliente(Conta contaCliente) {
		this.contaCliente = contaCliente;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public ContaService getContaService() {
		return contaService;
	}

	public void setContaService(ContaService contaService) {
		this.contaService = contaService;
	}

	public Double getValorSaque() {
		return valorSaque;
	}

	public void setValorSaque(Double valorSaque) {
		this.valorSaque = valorSaque;
	}


	public Double getValorDeposito() {
		return valorDeposito;
	}


	public void setValorDeposito(Double valorDeposito) {
		this.valorDeposito = valorDeposito;
	}



	public int getNumeroContaDeposito() {
		return numeroContaDeposito;
	}



	public void setNumeroContaDeposito(int numeroContaDeposito) {
		this.numeroContaDeposito = numeroContaDeposito;
	}



	public Double getValorTransferencia() {
		return valorTransferencia;
	}



	public void setValorTransferencia(Double valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}
	
	
	
}
