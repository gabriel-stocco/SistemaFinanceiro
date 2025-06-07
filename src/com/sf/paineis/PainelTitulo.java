package com.sf.paineis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sf.dao.ClassificacaoDAO;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.importacao.OFXImport;
import com.sf.model.MovimentacaoBancaria;
import com.sf.telas.TelaPrincipal;


@SuppressWarnings("serial")
public class PainelTitulo extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private JButton jbImportar, jbTransferir, jbGerenciar, jbAdicionar;

	private TelaPrincipal telaPrincipal;

	public PainelTitulo(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("TÍTULOS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Botoes da tela
		jbImportar = criarBotao("IMPORTAR\nMOVIMENTAÇÕES");
		jbTransferir = criarBotao("TRANSFERIR MOVIMENTOS\nEM TÍTULOS");
		jbGerenciar = criarBotao("GERENCIAR TÍTULOS");
		jbAdicionar = criarBotao("ADICIONAR PREVISÕES");

		// Adicionando ao Painel
		add(jlTitulo);
		add(jbImportar);
		add(jbTransferir);
		add(jbGerenciar);
		add(jbAdicionar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		jbImportar.setBounds(80, 150, 400, 120);
		jbTransferir.setBounds(530, 150, 400, 120);
		jbGerenciar.setBounds(80, 350, 400, 120);
		jbAdicionar.setBounds(530, 350, 400, 120);
	}

	private void criarEventos() {
		jbTransferir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelClassificar classificar = new PainelClassificar(telaPrincipal);
				telaPrincipal.trocarPainel(classificar);
			}
		});

		jbGerenciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarTitulos titulos = new PainelListarTitulos(telaPrincipal);
				telaPrincipal.trocarPainel(titulos);
			}
		});

		jbAdicionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroMovimentacao cadastro = new PainelCadastroMovimentacao(telaPrincipal);
				telaPrincipal.trocarPainel(cadastro);
			}
		});
		
		jbImportar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
				JFileChooser fileChooser = new JFileChooser();

		        fileChooser.setDialogTitle("Selecionar arquivo OFX");

		        // Filtro para arquivos OFX
		        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos OFX", "ofx");
		        fileChooser.setFileFilter(filtro);

		        int resultado = fileChooser.showOpenDialog(null);

		        if (resultado == JFileChooser.APPROVE_OPTION) {
		        	File arquivo = fileChooser.getSelectedFile();	        	
					OFXImport leitor = new OFXImport();
					
					
					List<MovimentacaoBancaria> lista = leitor.importarOFX(arquivo);
					
					//teste
					
		            for (MovimentacaoBancaria mov : lista) {           	
		            	JOptionPane.showMessageDialog(null, dao.salvar(mov));

		            }
		            
		        }
			}
		});

	}

	private JButton criarBotao(String titulo) {
		JButton card = new JButton();
		card.setBackground(new Color(0, 100, 0));
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
