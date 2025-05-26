package com.sf.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;

public class FornecedorDAO {
	
	private String sql, men;
	private BD bd;

	public FornecedorDAO() {
		bd = new BD();
	}
	
	/**
	 * Realiza a busca de todas os fornecedores presentes no banco de dados
	 * @return - os fornecedores em forma de lista
	 */
	public List<Fornecedor> listar() {
	    List<Fornecedor> lista = new ArrayList<>();
	    sql = "SELECT * FROM Fornecedor;";

	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.rs = bd.st.executeQuery();

	        while (bd.rs.next()) {
	        	Fornecedor f = new Fornecedor();
	            f.setIdForn(bd.rs.getInt(1));
	            f.setCnjpForn(bd.rs.getString(2));
	            f.setEnderecoForn(bd.rs.getString(3));
	            f.setIdMov(bd.rs.getInt(4));
	            lista.add(f);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return lista;
	}
	
	/**
	 * Realiza a gravação de um fornecedor no banco de dados
	 * @param f -  o fornecedor a ser salvo
	 * @return - uma mensagem informando o ocorrido
	 */
	public String salvar(Fornecedor f) {
		sql = "insert into Fornecedor values (?,?,?,?)";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, f.getIdFornecedor());
			bd.st.setString(2, f.getCnjpForn());
			bd.st.setString(3, f.getEnderecoForn());
			bd.st.setInt(4, f.getIdMov());
			bd.st.executeUpdate();
			men = "Fornecedor inserido com sucesso!";

		}
		catch(SQLException erro) {
			sql = "update Fornecedor set CNPJ_Emp=?,Endereco_Emp=?,Id_Mov=? where ID_Forn = ?";
			try {
				bd.st = bd.con.prepareStatement(sql);
				bd.st.setInt(4, f.getIdFornecedor());
				bd.st.setString(1, f.getCnjpForn());
				bd.st.setString(2, f.getEnderecoForn());
				bd.st.setInt(3, f.getIdMov());
				bd.st.executeUpdate();
				men = "Fornecedor alterado com sucesso!";

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
	 * Exclui um fornecedor a partir de seu código
	 * @param codigo - o código do fornecedor a ser excluido
	 * @return - uma mensagem informando o ocorrido
	 */
	public String excluir(int codigo) {
		sql = "delete from Fornecedor where ID_Forn = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1,codigo);
			if (bd.st.executeUpdate()==1) {
				men = "Fornecedor excluido com sucesso!";
			}
			else {
				men = "Fornecedor não encontrado!";
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
	 * Localiza um fornecedor a partir de seu código
	 * @param codigo - o código do fornecedor a ser localizado
	 * @return - o fornecedor na forma de um objeto ou null caso não encontrado
	 */
	public Fornecedor localizar(int codigo) {
		Fornecedor f = new Fornecedor();
		sql = "select * from Fornecedor where ID_Forn = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1,codigo);
			bd.rs = bd.st.executeQuery();
			if(bd.rs.next()) {
				f.setIdForn(bd.rs.getInt(1));
	            f.setCnjpForn(bd.rs.getString(2));
	            f.setEnderecoForn(bd.rs.getString(3));
	            f.setIdMov(bd.rs.getInt(4));
			}
			else {
				f = null;
			}
		}
		catch(SQLException erro) {
			f = null;
		}
		finally {
			bd.close();
		}
		return f;
	}	
}
