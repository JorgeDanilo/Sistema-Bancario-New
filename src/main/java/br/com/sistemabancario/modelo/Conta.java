package br.com.sistemabancario.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Range;

import br.com.sistemabancario.util.UtilData;

@Entity
public class Conta extends EntidadeBanco {

	private static final long serialVersionUID = -5801396957496860471L;
	
	@Id
	@GeneratedValue
	@Column(name = "id_conta")
	private Long identificador;
	
	private Integer numero;
	
	private Double saldo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura;
	
	@Transient
	private Collection<Transacao> transacoes;
	
	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(name = "id_titular")
	private Cliente titular;
	
	@Transient
	private Boolean isContaCriada;
	
		// construtor padrão da classe Conta que define a data de criação da conta e inicializa o array de transacao
		public Conta() {
			dataAbertura = UtilData.data();
			transacoes = new ArrayList<Transacao>();
		}

		// construtor com tres parametros
		public Conta( Cliente cliente, Integer nconta ) {

			this();
			numero = nconta;
			titular = cliente;
			saldo = 0.0; // Conta em reais e zerada
		}
	
	@Override
	public Long getIdentificador() {
		return identificador;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Collection<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(Collection<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Cliente getTitular() {
		return titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}
	
	public Boolean getIsContaCriada() {
		return isContaCriada;
	}

	public void setIsContaCriada(Boolean isContaCriada) {
		this.isContaCriada = isContaCriada;
	}

	@Override
	public String toString() {
		return getNumero() + "-" + getTitular().getNome();
	}
	
	public String getDataFormatada(){
		return UtilData.formataData(dataAbertura);
	}
}
