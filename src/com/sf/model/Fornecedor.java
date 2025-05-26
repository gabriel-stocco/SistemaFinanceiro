package com.sf.model;
/*
 * Classe responsável por criar o objeto Fornecedor
 */
public class Fornecedor {
	private int idFornecedor;
	private String cnjpForn;
	private String enderecoForn;
	private String emailForn;
	private int idMov;
	
	public Fornecedor() {
		// TODO Auto-generated constructor stub
	}
	
	public Fornecedor(int idForn, String cnjpForn, String enderecoForn, int idMov) {
		this.idFornecedor = idForn;
		this.cnjpForn = cnjpForn;
		this.enderecoForn = enderecoForn;
		this.idMov = idMov;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}
	
	public void setIdForn(int idForn) {
		this.idFornecedor = idForn;
	}

	public String getCnjpForn() {
		return cnjpForn;
	}

	public void setCnjpForn(String cnjpForn) {
		this.cnjpForn = cnjpForn;
	}

	public String getEnderecoForn() {
		return enderecoForn;
	}

	public void setEnderecoForn(String enderecoForn) {
		this.enderecoForn = enderecoForn;
	}

	public String getEmailForn() {
		return emailForn;
	}

	public void setEmailForn(String emailForn) {
		this.emailForn = emailForn;
	}

	public int getIdMov() {
		return idMov;
	}

	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}
	
}
