package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sf.classes.GraficoSimples;

@SuppressWarnings("serial")
public class PainelRelatorio extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private GraficoSimples grafico;
	
	public PainelRelatorio() {
		super();
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("RELATÓRIOS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		grafico = new GraficoSimples();
        grafico.setBounds(30, 70, 500, 300);
        add(grafico);
		
		// Adicionando ao Painel
		add(jlTitulo);
		add(grafico);
		
		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		grafico.setBounds(30, 70, 480, 300);
	}

	private void criarEventos() {
		// TODO Auto-generated method stub
		
	}
}
