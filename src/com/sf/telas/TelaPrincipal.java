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
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sf.bd.BD;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.paineis.PainelListarClassificacao;
import com.sf.paineis.PainelListarContas;
import com.sf.paineis.PainelListarEmpresa;
import com.sf.paineis.PainelListarFornecedor;
import com.sf.paineis.PainelRelatorio;
import com.sf.paineis.PainelTitulo;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	private JPanel painelConteudo, painelMenu, submenuCadastros, painelDashboard;
	private JLabel menuLabel = new JLabel("MENU"), jlTitulo;
	private JButton jbDashboard, jbCadastro, jbClassificacao, jbConta, jbEmpresa, jbFornecedor, jbTitulo,
			jbRelatorio;
	private Container contentPane;
	private static final Color COR_MENU = new Color(140, 140, 140);
	public static final Color COR_HOVER = new Color(180, 180, 180);
	public static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	public float entradas, saidas, saldo, previsoes;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private BD bd = new BD();

	public TelaPrincipal() {
		super();
		
		if (!bd.getConnection()) {
			JOptionPane.showMessageDialog(null, 
				"Não foi possível conectar ao banco de dados.\nVerifique sua conexão e tente novamente.",
		        "Erro de Conexão",
		         JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		bd.close();
		
		setTitle("Sistema Financeiro");
		setSize(1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		iniciarComponentes();
		criarEventos();
	}

	/**
	 * Método onde inicializa os componentes do painel
	 */
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

		jbDashboard = criarBotaoMenu("DASHBOARD");
		painelMenu.add(jbDashboard);
		painelMenu.add(Box.createVerticalStrut(10));
		jbCadastro = criarBotaoMenu("CADASTROS ▼");
		painelMenu.add(jbCadastro);

		// Submenu de Cadastros
		submenuCadastros = new JPanel();
		submenuCadastros.setLayout(new BoxLayout(submenuCadastros, BoxLayout.Y_AXIS));
		submenuCadastros.setVisible(false);

		// Adicionando itens ao submenu
		jbClassificacao = criarBotaoSubmenu("CLASSIFICAÇÃO");
		jbConta = criarBotaoSubmenu("CONTA BANCÁRIA");
		jbEmpresa = criarBotaoSubmenu("EMPRESAS");
		jbFornecedor = criarBotaoSubmenu("FORNECEDOR");
		submenuCadastros.add(jbClassificacao);
		submenuCadastros.add(jbConta);
		submenuCadastros.add(jbEmpresa);
		submenuCadastros.add(jbFornecedor);
		painelMenu.add(submenuCadastros);
		painelMenu.add(Box.createVerticalStrut(10));

		// Outros botões
		jbTitulo = criarBotaoMenu("TÍTULOS");
		painelMenu.add(jbTitulo);
		painelMenu.add(Box.createVerticalStrut(10));
		jbRelatorio = criarBotaoMenu("RELATÓRIOS");
		painelMenu.add(jbRelatorio);

		add(painelMenu, BorderLayout.WEST);

		// Painel de conteúdo principal
		painelConteudo = new JPanel();
		painelConteudo.setBackground(COR_CONTEUDO);
		painelConteudo.setLayout(new BorderLayout());
		contentPane.add(painelConteudo, BorderLayout.CENTER);

		// Painel de Dashboard
		jlTitulo = new JLabel("DASHBOARD");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		painelDashboard = new JPanel();
		painelDashboard.setBackground(COR_CONTEUDO);
		painelDashboard.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		painelDashboard.setBorder(new EmptyBorder(70, 0, 0, 0));
		
		//Buscando Valores do DASHBOARD
		entradas = dao.buscarValores("C");
		saidas = dao.buscarValores("D");
		previsoes = dao.buscarPrevisoes();
		saldo = dao.buscarSaldo();

		painelDashboard.add(criarCard("SAÍDAS TOTAIS", "R$ " + df.format(saidas)));
		painelDashboard.add(criarCard("ENTRADAS TOTAIS", "R$ " + df.format(entradas)));
		painelDashboard.add(criarCard("PREVISÃO MENSAL", "R$ " + df.format(previsoes)));
		painelDashboard.add(criarCard("SALDO TOTAL", "R$ " + df.format(saldo)));

		// Painel que junta título + dashboard
		JPanel painelCompletoDashboard = new JPanel();
		painelCompletoDashboard.setLayout(new BorderLayout());
		painelCompletoDashboard.setBackground(COR_CONTEUDO);

		// Painel para o título
		JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTitulo.setBackground(COR_CONTEUDO);
		painelTitulo.setBorder(new EmptyBorder(20, 30, 0, 0));
		jlTitulo = new JLabel("DASHBOARD");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		painelTitulo.add(jlTitulo);

		painelCompletoDashboard.add(painelTitulo, BorderLayout.NORTH);
		painelCompletoDashboard.add(painelDashboard, BorderLayout.CENTER);

		// Salva como painel principal do Dashboard
		painelDashboard = painelCompletoDashboard;

		painelConteudo.add(painelDashboard, BorderLayout.CENTER);
	}

	/**
	 * Método que cria um botão do menu lateral da tela
	 * @param nome - o texto presente no botão
	 * @return - o botao a ser adicionado no menu
	 */
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

	/**
	 * Cria um botão a ser adicionado a um submenu do menu
	 * @param nome - o texto presente no botao
	 * @return - o botao a ser adicionado no submenu
	 */
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

	/**
	 * Método onde estão os eventos presentes no painel
	 */
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
				jbCadastro.setText(isVisible ? "CADASTROS ▼" : "CADASTROS ▲");
			}
		});

		jbClassificacao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarClassificacao painel = new PainelListarClassificacao(TelaPrincipal.this);
				trocarPainel(painel);
			}
		});

		jbConta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarContas painel = new PainelListarContas(TelaPrincipal.this);
				trocarPainel(painel);
			}
		});

		jbEmpresa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarEmpresa painel = new PainelListarEmpresa(TelaPrincipal.this);
				trocarPainel(painel);
			}
		});

		jbFornecedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarFornecedor painel = new PainelListarFornecedor(TelaPrincipal.this);
				trocarPainel(painel);
			}
		});

		jbTitulo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelTitulo painel = new PainelTitulo(TelaPrincipal.this);
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

	/**
	 * Método padrão para trocar entre os paineis da aplicacao
	 * @param painelNovo - painel a ser colocado
	 */
	public void trocarPainel(JPanel painelNovo) {
		painelConteudo.removeAll();
		painelConteudo.add(painelNovo);
		painelConteudo.revalidate();
		painelConteudo.repaint();
	}

	/**
	 * Método que cria os cards presentes no dashboard
	 * @param titulo - texto que fica em cima no card
	 * @param valor - o valor que fica em baixo no card
	 * @return - o card a ser adicionado no dashboard
	 */
	private JPanel criarCard(String titulo, String valor) {
		JPanel card = new JPanel();
		card.setBackground(new Color(13, 33, 79));
		card.setLayout(new BorderLayout());
		card.setPreferredSize(new Dimension(450, 130));
		card.setBorder(BorderFactory.createEmptyBorder(25, 20, 10, 10));

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
