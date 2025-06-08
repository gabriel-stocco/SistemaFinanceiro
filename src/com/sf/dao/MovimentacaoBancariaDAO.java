package com.sf.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;
import com.sf.model.DadosGrafico;
import com.sf.model.MovimentacaoBancaria;

public class MovimentacaoBancariaDAO {

	private String sql, men;
	private BD bd;

	public MovimentacaoBancariaDAO() {
		bd = new BD();
	}

	/**
	 * Realiza a busca de todas as movimentações com classificacao presentes no
	 * banco de dados
	 * 
	 * @return - as movimentações em forma de lista
	 */
	public List<MovimentacaoBancaria> listarComClassificacao() {
		List<MovimentacaoBancaria> lista = new ArrayList<>();
		sql = "SELECT * FROM MovimentacaoBancaria WHERE Id_Classificacao IS NOT NULL";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				MovimentacaoBancaria m = new MovimentacaoBancaria();
				m.setIdMov(bd.rs.getInt(1));
				m.setDescMov(bd.rs.getString(2));
				m.setValorMov(bd.rs.getFloat(3));
				m.setTipoMov(bd.rs.getString(4));
				m.setDataMov(bd.rs.getDate(5));
				m.setIdClassificacao(bd.rs.getInt(6));
				m.setIdConta(bd.rs.getInt(7));
				m.setIdFornecedor(bd.rs.getInt(8));
				lista.add(m);
			}
		} catch (SQLException erro) {
			erro.printStackTrace();
		} finally {
			bd.close();
		}

		return lista;
	}

	/**
	 * Realiza a busca de todas as movimentações sem classficacao presentes no banco
	 * de dados
	 * 
	 * @return - as movimentações em forma de lista
	 */
	public List<MovimentacaoBancaria> listarSemClassificacao() {
		List<MovimentacaoBancaria> lista = new ArrayList<>();
		sql = "SELECT * FROM MovimentacaoBancaria WHERE Id_Classificacao IS NULL";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				MovimentacaoBancaria m = new MovimentacaoBancaria();
				m.setIdMov(bd.rs.getInt(1));
				m.setDescMov(bd.rs.getString(2));
				m.setValorMov(bd.rs.getFloat(3));
				m.setTipoMov(bd.rs.getString(4));
				m.setDataMov(bd.rs.getDate(5));
				m.setIdClassificacao(bd.rs.getInt(6));
				m.setIdConta(bd.rs.getInt(7));
				m.setIdFornecedor(bd.rs.getInt(8));
				lista.add(m);
			}
		} catch (SQLException erro) {
			erro.printStackTrace();
		} finally {
			bd.close();
		}

		return lista;
	}

	/**
	 * Realiza a gravação de uma movimentação no banco de dados
	 * 
	 * @param f - a movimentação a ser salva
	 * @return - uma mensagem informando o ocorrido
	 */
	public String salvar(MovimentacaoBancaria m) {
		sql = "insert into MovimentacaoBancaria values (?,?,?,?,?,?,?)";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, m.getDescMov());
			bd.st.setFloat(2, m.getValorMov());
			bd.st.setString(3, m.getTipoMov());
			bd.st.setDate(4, m.getDataMov());
			bd.st.setObject(5, m.getIdClassificacao());
			bd.st.setObject(6, m.getIdConta());
			bd.st.setObject(7, m.getIdFornecedor());
			bd.st.executeUpdate();
			men = "Previsão inserida com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Realiza a atualização de uma movimentação no banco de dados
	 * 
	 * @param f - a movimentação a ser salva
	 * @return - uma mensagem informando o ocorrido
	 */
	public String atualizar(MovimentacaoBancaria m) {
		sql = "update MovimentacaoBancaria set Desc_Mov=?,Valor_Mov=?,Tipo_Mov=?,Data_Mov=?,Id_Classificacao=?,"
				+ "Id_Conta=?,Id_Forn=? where Id_Mov = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(8, m.getIdMov());
			bd.st.setString(1, m.getDescMov());
			bd.st.setFloat(2, m.getValorMov());
			bd.st.setString(3, m.getTipoMov());
			bd.st.setDate(4, m.getDataMov());
			bd.st.setObject(5, m.getIdClassificacao());
			bd.st.setObject(6, m.getIdConta());
			bd.st.setObject(7, m.getIdFornecedor());
			bd.st.executeUpdate();
			men = "Previsão alterado com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Realiza a classificacao de uma movimentação em um titulo no banco de dados
	 * 
	 * @param f - a movimentação a ser classificada
	 * @return - uma mensagem informando o ocorrido
	 */
	public String classificar(MovimentacaoBancaria m) {
		sql = "update MovimentacaoBancaria set Desc_Mov=?,Id_Classificacao=? where Id_Mov = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(3, m.getIdMov());
			bd.st.setString(1, m.getDescMov());
			bd.st.setObject(2, m.getIdClassificacao());
			bd.st.executeUpdate();
			men = "Classificação feita com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Exclui uma movimentação a partir de seu código
	 * 
	 * @param codigo - o código da movimentação a ser excluido
	 * @return - uma mensagem informando o ocorrido
	 */
	public String excluir(int codigo) {
		sql = "delete from MovimentacaoBancaria where Id_Mov = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			if (bd.st.executeUpdate() == 1) {
				men = "Previsão excluida com sucesso!";
			} else {
				men = "Previsão não encontrada!";
			}
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Localiza uma movimentação a partir de seu código
	 * 
	 * @param codigo - o código da movimentação a ser localizado
	 * @return - a movimentação na forma de um objeto ou null caso não encontrado
	 */
	public MovimentacaoBancaria localizar(int codigo) {
		MovimentacaoBancaria m = new MovimentacaoBancaria();
		sql = "select * from MovimentacaoBancaria where Id_Mov = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			bd.rs = bd.st.executeQuery();
			if (bd.rs.next()) {
				m.setIdMov(bd.rs.getInt(1));
				m.setDescMov(bd.rs.getString(2));
				m.setValorMov(bd.rs.getFloat(3));
				m.setTipoMov(bd.rs.getString(4));
				m.setDataMov(bd.rs.getDate(5));
				m.setIdClassificacao(bd.rs.getInt(6));
				m.setIdConta(bd.rs.getInt(7));
				m.setIdFornecedor(bd.rs.getInt(8));
			} else {
				m = null;
			}
		} catch (SQLException erro) {
			m = null;
		} finally {
			bd.close();
		}
		return m;
	}
	
	/**
	 * Soma os valores de movimentações do mês anterior de um tipo específico
	 *
	 * @param tipoMov - tipo da movimentação ('C' ou 'D', por exemplo)
	 * @return soma dos valores encontrados ou 0 caso nenhum registro seja encontrado
	 */
	public float buscarValores(String tipoMov) {
	    float total = 0;
	    sql = "SELECT SUM(Valor_Mov) FROM MovimentacaoBancaria " +
	            "WHERE Tipo_Mov = ? " +
	            "AND MONTH(Data_Mov) = MONTH(DATEADD(MONTH, -1, GETDATE())) " +
	            "AND YEAR(Data_Mov) = YEAR(DATEADD(MONTH, -1, GETDATE()))";

	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.st.setString(1, tipoMov);
	        bd.rs = bd.st.executeQuery();

	        if (bd.rs.next()) {
	            total = bd.rs.getFloat(1);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return total;
	}
	
	/**
	 * Soma os valores de movimentações do até o dia atual para pegar o saldo atual
	 * 
	 * @return soma dos valores encontrados ou 0 caso nenhum registro seja encontrado
	 */
	public float buscarSaldo() {
	    float total = 0;
	    sql = "SELECT SUM(Valor_Mov) FROM MovimentacaoBancaria WHERE Data_Mov <= GETDATE()";

	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.rs = bd.st.executeQuery();

	        if (bd.rs.next()) {
	            total = bd.rs.getFloat(1);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return total;
	}
	
	/**
	 * Soma os valores das previsões do próximo mês
	 * 
	 * @return soma dos valores encontrados ou 0 caso nenhum registro seja encontrado
	 */
	public float buscarPrevisoes() {
	    float total = 0;
	    sql = "SELECT SUM(Valor_Mov) FROM MovimentacaoBancaria WHERE MONTH(Data_Mov) = MONTH(GETDATE())+1 AND YEAR(Data_Mov) = YEAR(GETDATE())";

	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.rs = bd.st.executeQuery();

	        if (bd.rs.next()) {
	            total = bd.rs.getFloat(1);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return total;
	}
	
	public List<DadosGrafico> buscarDadosGrafico() {
	    List<DadosGrafico> dados = new ArrayList<>();
	    String sql = "SELECT SUM(Valor_Mov), cl.Nom_Classificacao FROM MovimentacaoBancaria mov, Classificacao cl "
	    		+ "WHERE mov.Id_Classificacao = cl.Id_Classificacao GROUP BY cl.Nom_Classificacao";
	    
	    try {
	        bd.getConnection();
	        bd.st = bd.con.prepareStatement(sql);
	        bd.rs = bd.st.executeQuery();

	        while (bd.rs.next()) {
	        	DadosGrafico dado =  new DadosGrafico();
	            dado.setValor(bd.rs.getFloat(1));
	            dado.setCategoria(bd.rs.getString(2));
	            dados.add(dado);
	        }
	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        bd.close();
	    }

	    return dados;
	}
}
