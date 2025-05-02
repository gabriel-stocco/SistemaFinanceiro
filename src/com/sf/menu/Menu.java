package com.sf.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 *Classe menu que cria o menu principal
 */
public class Menu {
	
	public JButton cadastro,
					titulos,
					conciliacao,
					relatorios;
	/*
	 * Inicializando os botões padrões do menu
	 */
	public Menu() {
		cadastro = new JButton("Cadastro");
		titulos = new JButton("Titulos");
		conciliacao = new JButton("Conciliação");
		relatorios = new JButton("Relatorios");
		
		cadastro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cadastro,"Cadastro");
			}
		});
		titulos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cadastro,"Cadastro");
			}
		});
		conciliacao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cadastro,"Cadastro");
			}
		});
		relatorios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cadastro,"Cadastro");
			}
		});
	}
	
}
