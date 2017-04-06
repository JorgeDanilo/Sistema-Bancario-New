package br.com.sistemabancario.services;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.sistemabancario.modelo.Transacao;
import br.com.sistemabancario.modelo.enums.EnumTipoTransacao;
import br.com.sistemabancario.util.Transacional;
import br.com.sistemabancario.util.UtilData;

public class TransacaoService implements Serializable {

	private static final long serialVersionUID = 8831867423603577817L;
	
	@Inject
	private EntityManager manager;
	
	
	/**
	 * Método responsavel por salvar os dados da entidade Transacao.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * @param transacao
	 */
	@Transacional
	public void salvar(Transacao transacao) {
		this.manager.merge(transacao);
	}

	/**
	 * Método responsavel por listar as transações
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param identificador
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<Transacao> listarTransacoesPorConta(Long identificador) {
		
		Session session = (Session) this.manager.getDelegate();
		
		Criteria criteria = session.createCriteria(Transacao.class);
		
		criteria.createAlias("contaDebito", "contaDebito", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("contaCredito", "contaCredito", JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.or(Restrictions.eq("contaCredito.identificador", identificador), Restrictions.eq("contaDebito.identificador", identificador)));
		
		return criteria.list();
	}

	/**
	 * Método responsavel por verificar se já existe mais de quatro saque por mes.
	 * 
	 * @author Jorge Danilo Gomes da Silva
	 * 
	 * @param identificador
	 * @return
	 */
	public boolean buscarTransacaoPorSaque(Long identificador) {
		
		Session session = (Session) this.manager.getDelegate();
		
		Criteria criteria = session.createCriteria(Transacao.class);
		
		criteria.createAlias("contaCredito", "contaCredito", JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.eqOrIsNull("contaCredito.identificador", identificador));
		
		criteria.add(Restrictions.eq("tipoTransacao", EnumTipoTransacao.SAQUE));
		
		criteria.add(Restrictions.eq("data", UtilData.data()));
		
		return criteria.list().size() > 4;
	}

}
