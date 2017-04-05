package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.sistemabancario.excecoes.SaldoInsuficienteException;
import br.com.sistemabancario.modelo.Cliente;
import br.com.sistemabancario.modelo.Conta;
import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.modelo.enums.EnumTipoTransacao;
import br.com.sistemabancario.util.Transacional;
import br.com.sistemabancario.util.UtilData;

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
	
	protected void historicoTransacao(Conta contaDebito, Conta contaCredito, double valor, String descr, EnumTipoTransacao tipoTransacao) {

		Transacao transacao = new Transacao(UtilData.data(), contaDebito, contaCredito, valor, descr, tipoTransacao);

		if (contaDebito != null) {

			contaDebito.getTransacoes().add(transacao);

		}

		contaCredito.getTransacoes().add(transacao);

		transacaoService.salvar(transacao);
	}

}
