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
	
	public void abrirConta() {
		
		this.contaService.salvar(contaCliente);
		
		this.inicializarOperacoesConta();
		
	}

	private void inicializarOperacoesConta() {
		// TODO Auto-generated method stub
		
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
	
	

}
