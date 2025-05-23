package com.sf.menu;
/*
 * Classe responsável por criar o objeto Classificação
 */
public class Classificacao {
	private int idClassificacao;
	private String nomClassificacao;


	public Classificacao(String nomClassificacao) {
		this.nomClassificacao = nomClassificacao;
	}

	public int getIdClassificacao() {
		return idClassificacao;
	}

	public String getNomClassificacao() {
		return nomClassificacao;
	}

	public void setNomClassificacao(String nomClassificacao) {
		this.nomClassificacao = nomClassificacao;
	}
	
}
