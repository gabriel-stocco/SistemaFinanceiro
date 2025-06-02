package com.sf.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;

public class ContaDAO {

	private String sql, men;
	private BD bd;

	public ContaDAO() {
		bd = new BD();
	}

	/**
	 * Realiza a busca de todas as contas presentes no banco de dados
	 * 
	 * @return - as contas em forma de lista
	 */
	public List<ContaBancaria> listar() {
		List<ContaBancaria> lista = new ArrayList<>();
		sql = "SELECT * FROM ContaBancaria";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				ContaBancaria c = new ContaBancaria();
				c.setIdConta(bd.rs.getInt(1));
				c.setAgencia(bd.rs.getString(2));
				c.setSaldo(bd.rs.getFloat(3));
				c.setNumeroConta(bd.rs.getString(4));
				c.setIdBanco(bd.rs.getInt(5));
				c.setIdEmpresa(bd.rs.getInt(6));
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
	 * Realiza a busca de todas as contas presentes no banco de dados com o nome das
	 * empresas
	 * 
	 * @return - as contas em forma de lista
	 */
	public List<ContaBancaria> listarComEmpresa() {
		List<ContaBancaria> lista = new ArrayList<>();
		sql = "SELECT c.*, e.Nome_Emp FROM ContaBancaria c, Empresa e WHERE c.Id_Empresa = e.Id_Empresa;";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				ContaBancaria c = new ContaBancaria();
				c.setIdConta(bd.rs.getInt(1));
				c.setAgencia(bd.rs.getString(2));
				c.setSaldo(bd.rs.getFloat(3));
				c.setNumeroConta(bd.rs.getString(4));
				c.setIdBanco(bd.rs.getInt(5));
				c.setIdEmpresa(bd.rs.getInt(6));
				c.setNomeEmpresa(bd.rs.getString(7));
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
	 * Realiza a gravação de uma conta no banco de dados
	 * 
	 * @param c - a conta a ser salva
	 * @return - uma mensagem informando o ocorrido
	 */
	public String salvar(ContaBancaria c) {
		sql = "insert into ContaBancaria values (?,?,?,?,?)";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setString(1, c.getAgencia());
			bd.st.setFloat(2, c.getSaldo());
			bd.st.setString(3, c.getNumeroConta());
			bd.st.setInt(4, c.getIdBanco());
			bd.st.setInt(5, c.getIdEmpresa());
			bd.st.executeUpdate();
			men = "Conta Bancaria inserida com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Realiza a atualização de uma conta no banco de dados
	 * 
	 * @param c - a conta a ser salva
	 * @return - uma mensagem informando o ocorrido
	 */
	public String atualizar(ContaBancaria c) {
		sql = "update ContaBancaria set Agencia=?, Saldo=?, Numero_Conta=?, Id_Banco = ?, Id_Empresa = ? where Id_Conta = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(6, c.getIdConta());
			bd.st.setString(1, c.getAgencia());
			bd.st.setFloat(2, c.getSaldo());
			bd.st.setString(3, c.getNumeroConta());
			bd.st.setInt(4, c.getIdBanco());
			bd.st.setInt(5, c.getIdEmpresa());
			bd.st.executeUpdate();
			men = "Conta Bancaria alterada com sucesso!";
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Exclui uma conta a partir de seu código
	 * 
	 * @param codigo - o código da conta a ser excluido
	 * @return - uma mensagem informando o ocorrido
	 */
	public String excluir(int codigo) {
		sql = "update MovimentacaoBancaria set Id_Conta = NULL WHERE Id_Conta = ?";
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
		sql = "delete from ContaBancaria where Id_Conta = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			if (bd.st.executeUpdate() == 1) {
				men = "Conta Bancaria excluida com sucesso!";
			} else {
				men = "Conta Bancaria não encontrada!";
			}
		} catch (SQLException erro) {
			men = "Falha: " + erro.toString();
		} finally {
			bd.close();
		}
		return men;
	}

	/**
	 * Localiza uma conta a partir de seu código
	 * 
	 * @param codigo - o código da conta a ser localizado
	 * @return - a conta na forma de um objeto ou null caso não encontrado
	 */
	public ContaBancaria localizar(int codigo) {
		ContaBancaria c = new ContaBancaria();
		sql = "select * from ContaBancaria where Id_Conta = ?";
		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.st.setInt(1, codigo);
			bd.rs = bd.st.executeQuery();
			if (bd.rs.next()) {
				c.setIdConta(bd.rs.getInt(1));
				c.setAgencia(bd.rs.getString(2));
				c.setSaldo(bd.rs.getFloat(3));
				c.setNumeroConta(bd.rs.getString(4));
				c.setIdBanco(bd.rs.getInt(5));
				c.setIdEmpresa(bd.rs.getInt(6));
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
