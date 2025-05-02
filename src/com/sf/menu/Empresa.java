package com.sf.menu;

/*
 * Classe responsável por criar o objeto empresa
 */
public class Empresa {
	private int idEmpresa;
	private String enderecoEmp;
	private int cnpjEmp;
	private String nomeEmp;
	
	
	public Empresa(String endereco_Emp, int cnpj_Emp, String nome_Emp) {
		this.idEmpresa = (Integer) null;
		this.enderecoEmp = endereco_Emp;
		this.cnpjEmp = cnpj_Emp;
		this.nomeEmp = nome_Emp;
	}
	public int getEmpresa() {
		return idEmpresa;
	}

	public String getEndereco_Emp() {
		return enderecoEmp;
	}
	public void setEndereco_Emp(String endereco_Emp) {
		this.enderecoEmp = endereco_Emp;
	}
	public int getCnpj_Emp() {
		return cnpjEmp;
	}
	public void setCnpj_Emp(int cnpj_Emp) {
		this.cnpjEmp = cnpj_Emp;
	}
	public String getNome_Emp() {
		return nomeEmp;
	}
	public void setNome_Emp(String nome_Emp) {
		this.nomeEmp = nome_Emp;
	}
	

	
	
}
