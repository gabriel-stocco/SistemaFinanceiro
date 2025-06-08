package com.sf.model;

public class DadosGrafico {
	private String categoria;
    private float valor;
    
    public DadosGrafico() {
		// TODO Auto-generated constructor stub
	}

    public DadosGrafico(String categoria, float valor) {
        this.categoria = categoria;
        this.valor = valor;
    }

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

    
}
