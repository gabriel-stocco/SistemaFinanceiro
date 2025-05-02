package com.sf.bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Classe que faz a conexão com o banco de dados
 */
public class BD implements Credentials{

	public Connection con;
	public PreparedStatement st;
	public ResultSet rs;
	
	/*
	 * Classe main que instância a própria classe para iniciar o BD
	 */
	public static void main(String args[]) {
		BD bd = new BD();
		bd.getConnection();
		bd.close();
	}
	
	/*
	 * Método get connection que retorna o resultado da conexão
	 */
	public boolean getConnection() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, DRIVER, DATABASE);
			System.out.println("Driver carregado ;)");
		}catch(ClassNotFoundException e) {
			System.out.println("Driver não encontrado");
		}catch(SQLException sql) {
			System.out.println(sql);
		}
		return false;
	}
	
	/*
	 * Método para encerrrar a conexão com o BD
	 */
	public void close() {
		try {
			if(st!=null) st.close();
		}catch(SQLException sql) {
			
		}
		
		try {
			if(rs!=null) rs.close();
		}catch(SQLException sql) {
			
		}
		
		try {
			if(con!=null) con.close();
		}catch(SQLException sql) {
			
		}
	}
	
}
