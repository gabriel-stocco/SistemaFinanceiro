package com.sf.model;

public class Banco {
	private int idBanco;
	private String nomeBanco;

	public Banco() {
		// TODO Auto-generated constructor stub
	}

	public Banco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public int getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(int idBanco) {
		this.idBanco = idBanco;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

}
