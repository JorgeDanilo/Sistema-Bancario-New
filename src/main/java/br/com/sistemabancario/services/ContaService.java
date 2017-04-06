package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import br.com.sistemabancario.dao.ContaDao;
import br.com.sistemabancario.excecoes.ContaNaoExisteException;
import br.com.sistemabancario.excecoes.SaldoInsuficienteException;
import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.modelo.enums.EnumTipoTransacao;
import br.com.sistemabancario.util.Transacional;
import br.com.sistemabancario.util.UtilData;

/**
 * Classe de Servico responsável por implementação das regras de negócio. 
 * 
 * @author Jorge Danilo Gomes da Silva
 */
public class ContaService implements Serializable {

	private static final long serialVersionUID = -3052031878124202391L;
	
	@Inject
	private ContaDao contaDao;
	
	@Inject
	private TransacaoService transacaoService;
	
	@Transacional
	public void salvar(Conta conta) {
		
		this.contaDao.salvar(conta);
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
		
		return this.getContaDao().listar();
	}

	
	@Transacional
	public void sacar(Conta contaSaque, double valor) throws SaldoInsuficienteException {

		if (contaSaque.getSaldo() - valor >= 0) {
			
			if ( this.isPermiteCobrarSaque(contaSaque.getIdentificador()) ) {
				
				contaSaque.setSaldo( contaSaque.getSaldo() - valor - EnumTarifario.SAQUE.getPreco());
				
			} else {
				
				contaSaque.setSaldo( contaSaque.getSaldo() - valor );
			}

			this.getContaDao().salvar(contaSaque);

			this.historicoTransacao(null, contaSaque, valor, "saque na conta " + contaSaque.getNumero(), EnumTipoTransacao.SAQUE);

		} else {

			throw new SaldoInsuficienteException();
		}
	}
	
	
	/**
	 * Método responsavel por verificar se é permitido cobrar por um saque.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * @return boolean
	 */
	public boolean isPermiteCobrarSaque(Long identificador) {
		
		return this.transacaoService.buscarTransacaoPorSaque(identificador);		
		
	}
	
	/**
	 * Método responsável por realizar o depósito.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * @param contaOrigem
	 * @param contaDestino
	 * @param valor
	 */
	@Transacional
	public void depositar(Conta contaOrigem, Conta contaDestino, Double valor) {
		
		contaDestino.setSaldo(contaDestino.getSaldo() + valor);
		
		this.getContaDao().salvar(contaDestino);
		
		this.historicoTransacao(contaOrigem, contaDestino, valor, "deposito na conta " + contaDestino.getNumero(), EnumTipoTransacao.DEPOSITO);
		
	}
	
	
	/**
	 * Método responsável por gravas as informações dos históricos de transações
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param contaDebito
	 * @param contaCredito
	 * @param valor
	 * @param descr
	 * @param tipoTransacao
	 */
	protected void historicoTransacao(Conta contaDebito, Conta contaCredito, double valor, String descr, EnumTipoTransacao tipoTransacao) {

		Transacao transacao = new Transacao(UtilData.data(), contaDebito, contaCredito, valor, descr, tipoTransacao);

		if (contaDebito != null) {

			contaDebito.getTransacoes().add(transacao);

		}

		contaCredito.getTransacoes().add(transacao);

		transacaoService.salvar(transacao);
	}


	/**
	 * Método responsável por buscar conta poupdater numero. Caso não exista lança exceção ContaNãoExisteException
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param numeroConta
	 * @return
	 * @throws ContaNaoExisteException
	 */
	public Conta buscarContaPorNumero(int numeroConta) throws ContaNaoExisteException {
		
		for (Conta conta : this.listar()) {
			
			if ( conta.getNumero() == numeroConta ) {
				
				return conta;
			}
		}
		
		throw new ContaNaoExisteException();
		
	}


	/**
	 * Responsavel por retornar a instancia da classe ContaDao.
	 * @return
	 */
	public ContaDao getContaDao() {
		return contaDao;
	}


	/**
	 * Método responsável por realizar transferencia entre contas caso não tenha saldo suficiente e lançado uma exceção
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param contaSaque
	 * @param valor
	 * @param contaDestino
	 * @return
	 * @throws SaldoInsuficienteException
	 */
	@Transacional
	public boolean transferir(Conta contaSaque, Double valor, Conta contaDestino) throws SaldoInsuficienteException {
		
		return transferir(contaSaque, valor, contaDestino, "transferencia para conta " + contaDestino.getNumero());
		
	}

	
	/**
	 * Método responsável por realizar transferencia entre contas caso não tenha saldo suficiente e lançado uma exceção
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param contaSaque
	 * @param valor
	 * @param contaDestino
	 * @param descr
	 * @return
	 * @throws SaldoInsuficienteException
	 */
	@Transacional
	private boolean transferir(Conta contaSaque, Double valor, Conta contaDestino, String descr) throws SaldoInsuficienteException {
		
		if( contaSaque.getSaldo() - valor >= 0 ) {
			
			contaDestino.setSaldo(contaDestino.getSaldo() + valor );
			
			contaSaque.setSaldo(contaSaque.getSaldo() - valor - EnumTarifario.TRANSFERENCIA.getPreco() );
			
			this.getContaDao().salvar(contaSaque);
			
			this.getContaDao().salvar(contaDestino);
			
			
			this.historicoTransacao(contaSaque, contaDestino, valor, descr, EnumTipoTransacao.TRANSFERENCIA);
			
			return true;
			
		} else {
			
			throw new SaldoInsuficienteException();
		}
		
	}
	
	
	/**
	 * Método responsável por realizar debitos na conta
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * @param contaOperacao
	 * @param valor
	 */
	@Transacional
	private void debito(Conta contaOperacao, Double valor) {
		
		contaOperacao.setSaldo(contaOperacao.getSaldo() - valor);
		
		this.getContaDao().salvar(contaOperacao);
		
	}


	/**
	 * Método responsável por realizar operações de credito na conta
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * @param contaOperacao
	 * @param valor
	 */
	@Transacional
	protected void credito(Conta contaOperacao, double valor ) {
		
		contaOperacao.setSaldo(contaOperacao.getSaldo() + valor);
		
		this.getContaDao().salvar(contaOperacao);
	}
	
	

}
