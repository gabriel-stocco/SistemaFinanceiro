package com.sf.model;

/*
 * Classe responsável por criar o objeto Conta Bancaria
 */
public class ContaBancaria {
	private int idConta;
	private int idEmpresa;
	private int idBanco;
	private String agencia;
	private float saldo;
	private String numeroConta;
	private String nomeEmpresa;

	public ContaBancaria() {
		// TODO Auto-generated constructor stub
	}

	public ContaBancaria(String agencia, float saldo, String numeroConta, int idBanco, int idEmpresa) {
		this.agencia = agencia;
		this.saldo = saldo;
		this.numeroConta = numeroConta;
		this.idBanco = idBanco;
		this.idEmpresa = idEmpresa;
	}

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(int idBanco) {
		this.idBanco = idBanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

}
