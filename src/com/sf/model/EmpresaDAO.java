package com.sf.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;

public class EmpresaDAO {

	private String sql, men;
	private BD bd;

	public EmpresaDAO() {
		bd = new BD();
	}
	
	/**
	 * Realiza a busca de todas as empresas presentes no banco de dados
	 * @return - as empresas em forma de lista
	 */
	public List<Empresa> listar() {
	    List<Empresa> lista = new ArrayList<>();
	    sql = "SELECT * FROM Empresa;";

	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.rs = bd.st.executeQuery();

	        while (bd.rs.next()) {
	        	Empresa e = new Empresa();
	            e.setIdEmpresa(bd.rs.getInt(1));
	            e.setEndereco_Emp(bd.rs.getString(2));
	            e.setCnpj_Emp(bd.rs.getString(3));
	            e.setNome_Emp(bd.rs.getString(4));
	            lista.add(e);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return lista;
	}
	
	/**
	 * Realiza a gravação de uma empresa no banco de dados
	 * @param c -  a empresa a ser salvo
	 * @return - uma mensagem informando o ocorrido
	 */
	public String salvar(Empresa e) {
		sql = "insert into Empresa values (?,?,?,?)";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, e.getEmpresa());
			bd.st.setString(2, e.getEndereco_Emp());
			bd.st.setString(3, e.getCnpj_Emp());
			bd.st.setString(4, e.getNome_Emp());
			bd.st.executeUpdate();
			men = "Empresa "+e.getNome_Emp() + " inserido com sucesso!";

		}
		catch(SQLException erro) {
			sql = "update Empresa set Endereco_Emp=?,CNPJ_Emp=?,Nome_Emp=? where Id_Empresa = ?";
			try {
				bd.st = bd.con.prepareStatement(sql);
				bd.st.setInt(4, e.getEmpresa());
				bd.st.setString(1, e.getEndereco_Emp());
				bd.st.setString(2, e.getCnpj_Emp());
				bd.st.setString(3, e.getNome_Emp());
				bd.st.executeUpdate();
				men = "Empresa "+e.getNome_Emp() + " alterado com sucesso!";

			}
			catch(SQLException erro2) {
				men = "Falha: "+ erro.toString();
			}
		}
		finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Exclui uma empresa a partir de seu código
	 * @param codigo - o código da empresa a ser excluido
	 * @return - uma mensagem informando o ocorrido
	 */
	public String excluir(int codigo) {
		sql = "delete from Empresa where Id_Empresa = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1,codigo);
			if (bd.st.executeUpdate()==1) {
				men = "Empresa excluida com sucesso!";
			}
			else {
				men = "Empresa n�o encontrado!";
			}
		}
		catch(SQLException erro) {
			men = "Falha: "+ erro.toString();
		}
		finally {
			bd.close();
		}
		return men;
	}
	/**
	 * Localiza uma empresa a partir de seu código
	 * @param codigo - o código do produto a ser localizado
	 * @return - a empresa na forma de um objeto ou null caso não encontrado
	 */
	public Empresa localizar(int codigo) {
		Empresa e = new Empresa();
		sql = "select * from Empresa where Id_Empresa = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1,codigo);
			bd.rs = bd.st.executeQuery();
			if(bd.rs.next()) {
				e.setIdEmpresa(bd.rs.getInt(1));
				e.setEndereco_Emp(bd.rs.getString(2));
	            e.setCnpj_Emp(bd.rs.getString(3));
	            e.setNome_Emp(bd.rs.getString(4));
			}
			else {
				e = null;
			}
		}
		catch(SQLException erro) {
			e = null;
		}
		finally {
			bd.close();
		}
		return e;
	}	
	
}
