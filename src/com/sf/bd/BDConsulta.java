package com.sf.bd;

import java.sql.SQLException;

public class BDConsulta {
	BD bd = new BD();
	public void consulta(String query) {
		if(bd.getConnection()) {

			try {
				bd.st = bd.con.prepareStatement(query);
				bd.rs = bd.st.executeQuery();
				bd.rs.next();//Ponteiro se move para o próximo registro
				System.out.println(bd.rs.getString("ra"));
			}catch(SQLException e) {
				System.out.println(e);
			}
		}else System.out.println("Falha na conexão ao BD :(");
	}
}
