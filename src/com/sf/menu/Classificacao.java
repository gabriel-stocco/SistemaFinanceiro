package com.sf.main;
/*
 * Classe responsável por criar o objeto Classificação
 */
public class Classificacao {
	private int idClassificacao;
	private String nomClassificacao;


	public Classificacao(String nomClassificacao) {

		this.idClassificacao = (Integer) null;
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
