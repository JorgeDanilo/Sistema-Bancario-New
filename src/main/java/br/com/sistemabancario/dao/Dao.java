package br.com.sistemabancario.dao;

import java.util.Collection;

import br.com.sistemabancario.modelo.Entidade;


/**
 * Interface Dao Generica
 * 
 * @author Jorge Danilo Gomes da Silva
 *
 */
public interface Dao<E extends Entidade> {
	
	E obter(Long identificador);
	
	E salvar(E entidade);
	
	void remover(E entidade);
	
	Collection<E> listar();
	
}
