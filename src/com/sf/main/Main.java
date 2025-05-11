package com.sf.main;

import com.sf.telas.TelaPrincipal;

/*
 * Classe principal, que faz as instâncias e cria a janela
 */
public class Main {
		
	/*
	 * Função principal que instancia a classe e abre a janela
	 */
	public static void main(String args[]) {
		TelaPrincipal tela = new TelaPrincipal();
		tela.setVisible(true);
	}

}
