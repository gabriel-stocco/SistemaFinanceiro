package com.sf.menu;
/*
 * Classe responsável por estruturar o recebimento da Movimentação bancária via banco de dados
 */
public class MovimentacaoBancaria {
	private int idMov;
	private int idClassificacao;
	private int idConta;
	private int idFornecedor;
	private String descMov;
	private int valorMov;
	private String tipoMov;
	private String dataMov;
	
	public MovimentacaoBancaria(String descMov,
			int valorMov, String tipoMov, String dataMov) {

		this.idMov = (Integer) null;
		this.idClassificacao = (Integer) null;
		this.idConta = (Integer) null;
		this.idFornecedor = (Integer) null;
		this.descMov = descMov;
		this.valorMov = valorMov;
		this.tipoMov = tipoMov;
		this.dataMov = dataMov;
	}
	public int getIdMov() {
		return idMov;
	}

	public int getIdClassificacao() {
		return idClassificacao;
	}

	public int getIdConta() {
		return idConta;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public String getDescMov() {
		return descMov;
	}
	public void setDescMov(String descMov) {
		this.descMov = descMov;
	}
	public int getValorMov() {
		return valorMov;
	}
	public void setValorMov(int valorMov) {
		this.valorMov = valorMov;
	}
	public String getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}
	public String getDataMov() {
		return dataMov;
	}
	public void setDataMov(String dataMov) {
		this.dataMov = dataMov;
	}
}
