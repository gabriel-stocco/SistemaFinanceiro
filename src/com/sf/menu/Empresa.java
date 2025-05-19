package com.sf.menu;

/*
 * Classe responsável por criar o objeto empresa
 */
public class Empresa {
	private int idEmpresa;
	private String enderecoEmp;
	private String cnpjEmp;
	private String nomeEmp;
	
	
	public Empresa(String endereco_Emp, String cnpj_Emp, String nome_Emp) {
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
	public String getCnpj_Emp() {
		return cnpjEmp;
	}
	public void setCnpj_Emp(String cnpj_Emp) {
		this.cnpjEmp = cnpj_Emp;
	}
	public String getNome_Emp() {
		return nomeEmp;
	}
	public void setNome_Emp(String nome_Emp) {
		this.nomeEmp = nome_Emp;
	}
	
	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", enderecoEmp=" + enderecoEmp + ", cnpjEmp=" + cnpjEmp
				+ ", nomeEmp=" + nomeEmp + "]";
	}
	
}
