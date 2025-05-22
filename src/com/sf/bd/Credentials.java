package com.sf.bd;

public interface Credentials {
	public final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/*Arrumar o nome do bd*/
	public final String DATABASE = "";
	public final String URL = "jdbc:sqlserver//localhost:1433;databasemame="+DATABASE;
	public final String LOGIN = "";
	public final String SENHA = "";
}
