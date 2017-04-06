package br.com.sistemabancario.services;

/**
 * Enumerador das tarifas cobras.
 * 
 * @author Jorge Danilo Gomes da Silva
 *
 */
public enum EnumTarifario {

	TRANSFERENCIA( 1.75D ),
	SAQUE(3.90D);
	
	private double preco;

	/**
	 * Construtor responsável por novas intancias desse enumerador.
	 * @param preco
	 */
	EnumTarifario(double preco) {
		this.preco = preco;
	}


	public double getPreco() {
		return preco;
	}
	
	
}
