package com.sf.classes;

public enum TipoMovimentacao {
	CREDITO("C", "CRÉDITO"), DEBITO("D", "DÉBITO");

	private final String id;
	private final String nome;

	TipoMovimentacao(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return nome;
	}
}
