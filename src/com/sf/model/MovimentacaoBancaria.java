package com.sf.model;

import java.sql.Date;

/*
 * Classe responsável por estruturar o recebimento da Movimentação bancária via banco de dados
 */
public class MovimentacaoBancaria {
	private int idMov;
	private int idClassificacao;
	private Object idConta;
	private Object idFornecedor;
	private String descMov;
	private float valorMov;
	private String tipoMov;
	private Date dataMov;

	public MovimentacaoBancaria() {
		// TODO Auto-generated constructor stub
	}

	public MovimentacaoBancaria(String descMov, float valorMov, String tipoMov, Date dataMov, int idClassificacao,
			Object idConta, Object idForn) {
		this.descMov = descMov;
		this.valorMov = valorMov;
		this.tipoMov = tipoMov;
		this.dataMov = dataMov;
		this.idClassificacao = idClassificacao;
		this.idConta = idConta;
		this.idFornecedor = idForn;
	}

	public int getIdMov() {
		return idMov;
	}

	public void setIdMov(int idMov) {
		this.idMov = idMov;
	}

	public int getIdClassificacao() {
		return idClassificacao;
	}

	public void setIdClassificacao(int idClassificacao) {
		this.idClassificacao = idClassificacao;
	}

	public Object getIdConta() {
		return idConta;
	}

	public void setIdConta(Object idConta) {
		this.idConta = idConta;
	}

	public Object getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Object idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	@Override
	public String toString() {
		return "MovimentacaoBancaria [idMov=" + idMov + ", idClassificacao=" + idClassificacao + ", idConta=" + idConta
				+ ", idFornecedor=" + idFornecedor + ", descMov=" + descMov + ", valorMov=" + valorMov + ", tipoMov="
				+ tipoMov + ", dataMov=" + dataMov + "]";
	}

	public String getDescMov() {
		return descMov;
	}

	public void setDescMov(String descMov) {
		this.descMov = descMov;
	}

	public float getValorMov() {
		return valorMov;
	}

	public void setValorMov(float valorMov) {
		this.valorMov = valorMov;
	}

	public String getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}

	public Date getDataMov() {
		return dataMov;
	}

	public void setDataMov(java.util.Date date) {
		this.dataMov = (Date) date;
	}

}
