package com.sf.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sf.paineis.PainelCadastroClassificacao;
import com.sf.paineis.PainelCadastroEmpresa;
import com.sf.paineis.PainelCadastroFornecedor;
import com.sf.paineis.PainelRelatorio;
import com.sf.paineis.PainelTitulo;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	private JPanel painelConteudo, painelMenu, submenuCadastros, painelDashboard;
	private JLabel menuLabel = new JLabel("Menu");
	private JButton jbDashboard, jbCadastro, jbClassificacao, jbEmpresa, jbFornecedor, jbTitulo, jbConciliacao, jbRelatorio;
	private Container contentPane;
	private static final Color COR_MENU = new Color(140, 140, 140);
	private static final Color COR_HOVER = new Color(180, 180, 180);
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	
	public TelaPrincipal() {
		super();
	    setTitle("Sistema Financeiro");
		setSize(1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		iniciarComponentes();
		criarEventos();
	}
	 
	private void iniciarComponentes() {
		contentPane = getContentPane();
	    painelMenu = new JPanel();
	    painelMenu.setBackground(COR_MENU);
	    painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
	    painelMenu.setPreferredSize(new Dimension(256, getHeight()));
	    
	    menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
	    menuLabel.setForeground(Color.WHITE);
	    menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    menuLabel.setBorder(new EmptyBorder(20, 20, 0, 10));
	    painelMenu.add(menuLabel);
	    painelMenu.add(Box.createVerticalStrut(20));
	    
	    jbDashboard = criarBotaoMenu("Dashboard");
	    painelMenu.add(jbDashboard);
	    painelMenu.add(Box.createVerticalStrut(10));
	    jbCadastro = criarBotaoMenu("Cadastros ▼");
	    painelMenu.add(jbCadastro);

	    // Submenu de Cadastros
	    submenuCadastros = new JPanel();
	    submenuCadastros.setLayout(new BoxLayout(submenuCadastros, BoxLayout.Y_AXIS));
	    submenuCadastros.setVisible(false);
	    
	    // Adicionando itens ao submenu
	    jbClassificacao = criarBotaoSubmenu("Classificação");
	    jbEmpresa = criarBotaoSubmenu("Empresas");
	    jbFornecedor = criarBotaoSubmenu("Fornecedor");
	    submenuCadastros.add(jbClassificacao);
	    submenuCadastros.add(jbEmpresa);
	    submenuCadastros.add(jbFornecedor);
	    painelMenu.add(submenuCadastros);
	    painelMenu.add(Box.createVerticalStrut(10));
	    
	    // Outros botões
	    jbTitulo = criarBotaoMenu("Títulos");
	    painelMenu.add(jbTitulo);
	    painelMenu.add(Box.createVerticalStrut(10));
	    jbConciliacao = criarBotaoMenu("Conciliação");
	    painelMenu.add(jbConciliacao);
	    jbRelatorio = criarBotaoMenu("Relatórios");
	    painelMenu.add(Box.createVerticalStrut(10));
	    painelMenu.add(jbRelatorio);

	    add(painelMenu, BorderLayout.WEST);

	    // Painel de conteúdo principal
	    painelConteudo = new JPanel();
        painelConteudo.setBackground(COR_CONTEUDO);
        painelConteudo.setLayout(new BorderLayout());
        contentPane.add(painelConteudo, BorderLayout.CENTER);
        
        //Painel de Dashboard
        painelDashboard = new JPanel();
        painelDashboard.setBackground(COR_CONTEUDO);
        painelDashboard.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        painelDashboard.setBorder(new EmptyBorder(50, 00, 0, 0));
        
        painelDashboard.add(criarCard("Saídas totais", "R$ 0,00"));
        painelDashboard.add(criarCard("Entradas totais", "R$ 0,00"));
        painelDashboard.add(criarCard("Previsão semanal", "R$ 0,00"));
        painelDashboard.add(criarCard("Saldo total", "R$ 0,00"));
        
        painelConteudo.add(painelDashboard);
	}
	 
	private JButton criarBotaoMenu(String nome) {
	    JButton botao = new JButton(nome);
	  
	    botao.setAlignmentX(Component.LEFT_ALIGNMENT);
	    botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
	    botao.setFont(new Font("Segoe UI", Font.PLAIN, 22));
	    botao.setHorizontalAlignment(SwingConstants.LEFT);
	    botao.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
	    botao.setFocusPainted(false);
	    botao.setContentAreaFilled(false);
	    botao.setOpaque(true);
	    botao.setBackground(COR_MENU);
	    botao.setForeground(Color.WHITE);
	    botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
	    botao.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				botao.setBackground(COR_MENU);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				botao.setBackground(COR_HOVER);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	    
	    return botao;
	}

	private JButton criarBotaoSubmenu(String nome) {
	    JButton botao = new JButton(nome);
	    botao.setAlignmentX(Component.LEFT_ALIGNMENT);
	    botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
	    botao.setFont(new Font("Segoe UI", Font.PLAIN, 20));
	    botao.setHorizontalAlignment(SwingConstants.LEFT);
	    botao.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 5));
	    botao.setFocusPainted(false);
	    botao.setContentAreaFilled(false);
	    botao.setOpaque(true);
	    botao.setBackground(COR_MENU);
	    botao.setForeground(Color.WHITE);
	    botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
	    botao.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				botao.setBackground(COR_MENU);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				botao.setBackground(COR_HOVER);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    return botao;
	}
	
	private void criarEventos() {
		jbDashboard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				trocarPainel(painelDashboard);
			}
		});
		
		jbCadastro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = submenuCadastros.isVisible();
				submenuCadastros.setVisible(!isVisible);
				jbCadastro.setText(isVisible ? "Cadastros ▼" : "Cadastros ▲");
			}
		});
		
		jbClassificacao.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroClassificacao painel = new PainelCadastroClassificacao();
				trocarPainel(painel);
			}
		});
		
		jbEmpresa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroEmpresa painel = new PainelCadastroEmpresa();
				trocarPainel(painel);
			}
		});
		
		jbFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroFornecedor painel = new PainelCadastroFornecedor();
				trocarPainel(painel);
			}
		});
		
		jbTitulo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelTitulo painel = new PainelTitulo();
				trocarPainel(painel);
			}
		});
		
		jbRelatorio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelRelatorio painel = new PainelRelatorio();
				trocarPainel(painel);
			}
		});
	}
	
	private void trocarPainel(JPanel painelNovo) {
		painelConteudo.removeAll();
		painelConteudo.add(painelNovo);
		painelConteudo.revalidate();
        painelConteudo.repaint();
	}

	private JPanel criarCard(String titulo, String valor) {
	    JPanel card = new JPanel();
	    card.setBackground(new Color(13, 33, 79));
	    card.setLayout(new BorderLayout());
	    card.setPreferredSize(new Dimension(450, 120));
	    card.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

	    JLabel labelTitulo = new JLabel(titulo);
	    labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
	    labelTitulo.setForeground(Color.WHITE);

	    JLabel labelValor = new JLabel(valor);
	    labelValor.setFont(new Font("Segoe UI", Font.PLAIN, 28));
	    labelValor.setForeground(Color.WHITE);

	    JPanel painelTexto = new JPanel();
	    painelTexto.setLayout(new BoxLayout(painelTexto, BoxLayout.Y_AXIS));
	    painelTexto.setBackground(null);
	    painelTexto.add(labelTitulo, BorderLayout.CENTER);
	    painelTexto.add(labelValor, BorderLayout.CENTER);

	    card.add(painelTexto, BorderLayout.CENTER);
	    return card;
	}
	  
}
