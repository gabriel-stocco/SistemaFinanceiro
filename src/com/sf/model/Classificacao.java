package com.sf.model;
/*
 * Classe responsável por criar o objeto Classificação
 */
public class Classificacao {
	private int idClassificacao;
	private String nomClassificacao;

	public Classificacao() {
		// TODO Auto-generated constructor stub
	}

	public Classificacao(int idClassificacao, String nomClassificacao) {
		this.idClassificacao = idClassificacao;
		this.nomClassificacao = nomClassificacao;
	}

	public int getIdClassificacao() {
		return idClassificacao;
	}

	public String getNomClassificacao() {
		return nomClassificacao;
	}
	
	public void setIdClassificacao(int idClassificacao) {
		this.idClassificacao = idClassificacao;
	}

	public void setNomClassificacao(String nomClassificacao) {
		this.nomClassificacao = nomClassificacao;
	}
	
}
