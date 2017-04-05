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
import br.com.sistemabancario.util.Transacional;

public class TransacaoService implements Serializable {

	private static final long serialVersionUID = 8831867423603577817L;
	
	@Inject
	private EntityManager manager;
	
	@Transacional
	public void salvar(Transacao transacao) {
		this.manager.merge(transacao);
	}

	/**
	 * Método responsavel por listar as transações
	 * @param identificador
	 * @return
	 */
	public Collection<Transacao> listarTransacoesPorConta(Long identificador) {
		
		Session session = (Session) this.manager.getDelegate();
		
		Criteria criteria = session.createCriteria(Transacao.class);
		
		criteria.createAlias("contaDebito", "contaDebito", JoinType.LEFT_OUTER_JOIN);
		
		criteria.createAlias("contaCredito", "contaCredito", JoinType.LEFT_OUTER_JOIN);
		
		criteria.add(Restrictions.or(Restrictions.eq("contaCredito.identificador", identificador), Restrictions.eq("contaDebito.identificador", identificador)));
		
		return criteria.list();
	}

}
