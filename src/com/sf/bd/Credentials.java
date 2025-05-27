package com.sf.bd;

public interface Credentials {
	public final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/*Arrumar o nome do bd*/
	public final String DATABASE = "teste_PIC";
	public final String URL = "jdbc:sqlserver://localhost:1433;databaseName="+DATABASE;
	public final String LOGIN = "sa";
	public final String SENHA = "";
}
