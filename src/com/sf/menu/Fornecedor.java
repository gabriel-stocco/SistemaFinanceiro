package com.sf.menu;
/*
 * Classe responsável por criar o objeto Fornecedor
 */
public class Fornecedor {
	private int idFornecedor;
	private int cnjpForn;
	String enderecoForn;
	String emailForn;
	
	public Fornecedor(int cnjpForn, String enderecoForn, String emailForn) {
		this.cnjpForn = cnjpForn;
		this.enderecoForn = enderecoForn;
		this.emailForn = emailForn;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public int getCnjpForn() {
		return cnjpForn;
	}

	public void setCnjpForn(int cnjpForn) {
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
