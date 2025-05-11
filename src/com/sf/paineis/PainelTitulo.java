package com.sf.paineis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PainelTitulo extends JPanel {
	private static final Color COR_CONTEUDO = new Color(200, 200, 200);
	private JLabel jlTitulo;
	private JButton jbImportar, jbTransferir, jbGerenciar, jbAdicionar;
	
	public PainelTitulo() {
		super();
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		//Título do Painel
		jlTitulo = new JLabel("TÍTULOS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Botoes da tela
		jbImportar = criarBotao("IMPORTAR\nMOVIMENTAÇÕES");
		jbTransferir = criarBotao("TRANSFERIR MOVIMENTOS\nEM TÍTULOS");
		jbGerenciar = criarBotao("GERENCIAR TÍTULOS");
		jbAdicionar = criarBotao("ADICIONAR PREVISÕES");
		
		//Adicionando ao Painel
		add(jlTitulo);
		add(jbImportar);
		add(jbTransferir);
		add(jbGerenciar);
		add(jbAdicionar);
		
		//Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		jbImportar.setBounds(80, 150, 400, 120);
		jbTransferir.setBounds(530, 150, 400, 120);
		jbGerenciar.setBounds(80, 350, 400, 120);
		jbAdicionar.setBounds(530, 350, 400, 120);
	}

	private void criarEventos() {
		// TODO Auto-generated method stub
		
	}
	
	private JButton criarBotao(String titulo) {
	    JButton card = new JButton();
	    card.setBackground(new Color(144, 230, 144));
	    card.setLayout(new BorderLayout());
	    card.setPreferredSize(new Dimension(420, 120));
	    card.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
	    card.setFocusPainted(false);
	    card.setBorderPainted(false);
	    card.setContentAreaFilled(false);
	    card.setOpaque(true);
	    card.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    JPanel painelTexto = new JPanel();
	    painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
	    painelTexto.setBackground(null);
	    
	    for (String linha : titulo.split("\n")) {
	        JLabel label = new JLabel(linha);
	        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
	        label.setForeground(Color.WHITE);
	        label.setAlignmentX(Component.LEFT_ALIGNMENT);
	        painelTexto.add(label);
	    }
	    
	    card.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				card.setBorderPainted(false);
				card.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				card.setBorderPainted(true);
				Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 3);
			    Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
			    card.setBorder(BorderFactory.createCompoundBorder(whiteBorder, padding));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

	    card.add(painelTexto, BorderLayout.CENTER);
	    return card;
	}
}
