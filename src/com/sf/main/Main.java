package com.sf.main;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import com.sf.menu.Menu;

/*
 * Classe principal, que faz as instâncias e cria a janela e relações
 */
public class Main extends JFrame{
	
	/**
	 * Código de serialização gerado automaticamente
	 */
	private static final long serialVersionUID = -1982822269813614355L;
	
	/*
	 * Função principal que instancia a classe e abre a janela
	 */
	public static void main(String args[]) {
		Main main = new Main();
		main.start();
	}
	
	/*
	 * Função Start que inicia os parâmetros da tela do sistema
	 */
	private void start() {
		setTitle("Sistema Financeiro");
		setBounds(0,0,500,500);
		setLayout(new FlowLayout());
		
		Menu menu = new Menu();
		add(menu.cadastro);
		add(menu.titulos);
		add(menu.conciliacao);
		add(menu.relatorios);
		

		setVisible(true);
	}

}
