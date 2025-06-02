package com.sf.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sf.bd.BD;

public class BancoDAO {

	private String sql;
	private BD bd;

	public BancoDAO() {
		bd = new BD();
	}

	/**
	 * Realiza a busca de todas os bancos presentes no banco de dados
	 * 
	 * @return - as classificações em forma de lista
	 */
	public List<Banco> listar() {
		List<Banco> lista = new ArrayList<>();
		sql = "SELECT * FROM Banco";

		try {
			bd.getConnection();
			bd.st = bd.con.prepareStatement(sql);
			bd.rs = bd.st.executeQuery();

			while (bd.rs.next()) {
				Banco b = new Banco();
				b.setIdBanco(bd.rs.getInt(1));
				b.setNomeBanco(bd.rs.getString(2));
				lista.add(b);
			}
		} catch (SQLException erro) {
			erro.printStackTrace();
		} finally {
			bd.close();
		}

		return lista;
	}
}
