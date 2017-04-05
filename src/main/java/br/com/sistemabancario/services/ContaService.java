package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.excecoes.ContaNaoExisteException;
import br.com.sistemabancario.excecoes.SaldoInsuficienteException;
import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.modelo.enums.EnumTipoTransacao;
import br.com.sistemabancario.util.Transacional;
import br.com.sistemabancario.util.UtilData;

/**
 * Classe de Servico responsavel por implementação das regras de negocio.
 * @author Jorge Danilo Gomes da Silva
 */
public class ContaService implements Serializable {

	private static final long serialVersionUID = -3052031878124202391L;
	
	@Inject
	private EntityManager manager;
	
	@Inject
	private TransacaoService transacaoService;
	
	@Transacional
	public void salvar(Conta conta) {
		
		this.manager.merge(conta);
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

	
	@Transacional
	public void sacar(Conta contaSaque, double valor) throws SaldoInsuficienteException {

		if (contaSaque.getSaldo() - valor >= 0) {

			contaSaque.setSaldo(contaSaque.getSaldo() - valor);
			
			this.manager.merge(contaSaque);

			this.historicoTransacao(null, contaSaque, valor, "saque na conta " + contaSaque.getNumero(), EnumTipoTransacao.SAQUE);

		} else {

			throw new SaldoInsuficienteException();
		}
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
		
		contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
		
		contaDestino.setSaldo(contaDestino.getSaldo() + valor);

		this.manager.merge(contaOrigem);
		
		this.manager.merge(contaDestino);
		
		this.historicoTransacao(null, contaDestino, valor, "deposito na conta" + contaDestino.getNumero(), EnumTipoTransacao.DEPOSITO);
		
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
	 * Método responsável por buscar conta por numero. Caso não exista lança exceção ContaNãoExisteException
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

}
