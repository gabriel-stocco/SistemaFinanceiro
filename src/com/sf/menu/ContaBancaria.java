package com.sf.menu;
/*
 * Classe responsável por criar o objeto Conta Bancaria
 */
public class ContaBancaria {
	private int idConta;
	private int idEmpresa;
	private String agencia;
	private int saldo;
	private int numeroConta;
	private String banco;
	
	public ContaBancaria(int idEmpresa, String agencia, int saldo, int numeroConta, String banco) {
		this.idEmpresa = idEmpresa;
		this.agencia = agencia;
		this.saldo = saldo;
		this.numeroConta = numeroConta;
		this.banco = banco;
	}

	public int getIdConta() {
		return idConta;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}
}
