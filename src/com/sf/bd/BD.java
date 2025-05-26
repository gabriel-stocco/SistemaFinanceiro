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
	public boolean getConnection(){
		try{
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,LOGIN,SENHA);
			System.out.println("Conectou");
			return true;
		}
		catch(ClassNotFoundException erro){
			System.out.println(erro.toString());
			return false;
		}
		catch(SQLException erro){
				System.out.println(erro.toString());	
			return false;
		}
	}
	
	/*
	 * Método para encerrrar a conexão com o BD
	 */
	public void close(){
		try{
           if(rs!=null)
              rs.close();
		}
		catch(SQLException erro){}
		try{
           if(st!=null)
			  st.close();
		}
		catch(SQLException erro){} 
		try{
			con.close();
			System.out.println("Desconectou");
		}
		catch(SQLException erro){
			System.out.println(erro.toString());
		} 
	}  
	
}
