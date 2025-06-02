package com.sf.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;

public class ClassificacaoDAO {

	private String sql, men;
	private BD bd;

	public ClassificacaoDAO() {
		bd = new BD();
	}

	/**
	 * Realiza a busca de todas as classificações presentes no banco de dados
	 * 
	 * @return - as classificações em forma de lista
	 */
	public List<Classificacao> listar() {
		List<Classificacao> lista = new ArrayList<>();
		sql = "SELECT * FROM Classificacao;";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				Classificacao c = new Classificacao();
				c.setIdClassificacao(bd.rs.getInt(1));
				c.setNomClassificacao(bd.rs.getString(2));
				lista.add(c);
			}
		} catch (SQLException erro) {
			erro.printStackTrace();
		} finally {
			bd.close();
		}

		return lista;
	}

	/**
	 * Realiza a gravação de uma classificação no banco de dados
	 * 
	 * @param c - a classificação a ser salvo
	 * @return - uma mensagem informando o ocorrido
	 */
	public String salvar(Classificacao c) {
		sql = "insert into Classificacao values (?)";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, c.getNomClassificacao());
			bd.st.executeUpdate();
			men = "Classificação inserido com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * @param c - a classificação a ser salvo
	 * @return - uma mensagem informando o ocorrido
	 */
	public String atualizar(Classificacao c) {
		sql = "update Classificacao set Nom_Classificacao=? where Id_Classificacao = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(2, c.getIdClassificacao());
			bd.st.setString(1, c.getNomClassificacao());
			bd.st.executeUpdate();
			men = "Classificação alterado com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Exclui uma classificação a partir de seu código
	 * 
	 * @param codigo - o código da classificação a ser excluido
	 * @return - uma mensagem informando o ocorrido
	 */
	public String excluir(int codigo) {
		sql = "update MovimentacaoBancaria set Id_Classificacao = NULL WHERE Id_Classificacao = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			if (bd.st.executeUpdate() == 1) {
				men = "Classificação excluida com sucesso!";
			} else {
				men = "Classificação não encontrada!";
			}
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		sql = "delete from Classificacao where Id_Classificacao = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			if (bd.st.executeUpdate() == 1) {
				men = "Classificação excluida com sucesso!";
			} else {
				men = "Classificação não encontrada!";
			}
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Localiza uma classificação a partir de seu c�digo
	 * 
	 * @param codigo - o código da classificação a ser localizado
	 * @return - a classificação na forma de um objeto ou null caso n�o encontrado
	 */
	public Classificacao localizar(int codigo) {
		Classificacao c = new Classificacao();
		sql = "select * from Classificacao where Id_Classificacao = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			bd.rs = bd.st.executeQuery();
			if (bd.rs.next()) {
				c.setIdClassificacao(bd.rs.getInt(1));
				c.setNomClassificacao(bd.rs.getString(2));
			} else {
				c = null;
			}
		} catch (SQLException erro) {
			c = null;
		} finally {
			bd.close();
		}
		return c;
	}

}
