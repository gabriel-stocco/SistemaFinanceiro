package com.sf.model;

/*
 * Classe responsável por criar o objeto Fornecedor
 */
public class Fornecedor {
	private int idFornecedor;
	private String cnjpForn;
	private String enderecoForn;
	private String emailForn;

	public Fornecedor() {
		// TODO Auto-generated constructor stub
	}

	public Fornecedor(String cnjpForn, String enderecoForn, String emailForn) {
		this.cnjpForn = cnjpForn;
		this.enderecoForn = enderecoForn;
		this.emailForn = emailForn;
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

}
