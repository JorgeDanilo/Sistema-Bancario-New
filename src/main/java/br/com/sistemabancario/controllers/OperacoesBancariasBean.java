package br.com.sistemabancario.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.services.ContaService;

@Named
@SessionScoped
public class OperacoesBancariasBean implements Serializable {

	private static final long serialVersionUID = -8707024590728060564L;
	
	private Conta contaCliente = new Conta();
	
	private Cliente clienteSelecionado;
	
	@Inject
	private ContaService contaService;
	
	private Double valorSaque;
	 
	/**
	 *
	 * Método responsavel por abrir uma conta para o usuario.
	 */
	public void abrirConta() {
		
		this.contaCliente.setTitular(clienteSelecionado);
		
		this.contaService.salvar(contaCliente);
		
//		TODO: implementar a mensagem de sucesso e atualizar as modais...
		
		this.inicializarOperacoesConta();
		
	}

	private void inicializarOperacoesConta() {
		
		this.contaCliente = this.contaService.buscarContaPorCliente(clienteSelecionado);
		
		System.out.println(clienteSelecionado);
	}
	
	public void efetuarSaque() {
		

		if (valorSaque != null && valorSaque > 0) {

			try {
				
				this.contaCliente = this.contaService.buscarContaPorCliente(clienteSelecionado);

				this.contaService.sacar(contaCliente, valorSaque);

				this.inicializarOperacoesConta();


			} catch (Exception e) {
				
				System.out.println(e.getMessage());
			}

		} else {

//			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro.", "Valor válido não informado!"));
		}
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
	

}
