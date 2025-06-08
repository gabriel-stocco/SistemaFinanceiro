package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.TabelaModular;
import com.sf.dao.ClassificacaoDAO;
import com.sf.model.Classificacao;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelListarClassificacao extends JPanel {
	private TabelaModular<Classificacao> tabela;
	private JLabel jlTitulo;
	private JButton jbAdicionar;
	private ClassificacaoDAO dao = new ClassificacaoDAO();
	List<Classificacao> classificacao = new ArrayList<Classificacao>();

	private TelaPrincipal telaPrincipal;

	public PainelListarClassificacao(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	/**
	 * Método onde inicializa os componentes do painel
	 */
	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("LISTAR CLASSIFICAÇÕES");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		jbAdicionar = new JButton("ADICIONAR");
		jbAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		jbAdicionar.setBackground(new Color(13, 33, 79));
		jbAdicionar.setForeground(Color.WHITE);
		jbAdicionar.setFocusPainted(false);
		jbAdicionar.setBorderPainted(false);
		jbAdicionar.setContentAreaFilled(false);
		jbAdicionar.setOpaque(true);
		jbAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Colunas da tabela
		LinkedHashMap<String, String> camposParaColunas = new LinkedHashMap<>();
		camposParaColunas.put("nomClassificacao", "Nome");

		// DEFININDO AS AÇÕES DE EDITAR E EXCLUIR AQUI
		Consumer<Classificacao> minhaAcaoEditar = (classificacao) -> {
			PainelCadastroClassificacao painelCadastro = new PainelCadastroClassificacao(telaPrincipal, classificacao);
			telaPrincipal.trocarPainel(painelCadastro);
		};

		Consumer<Classificacao> minhaAcaoExcluir = (classificacao) -> {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Tem certeza que deseja excluir: " + classificacao.getNomClassificacao() + "?",
					"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				String res = dao.excluir(classificacao.getIdClassificacao());
				JOptionPane.showMessageDialog(this, res);

				if (res.contains("sucesso")) {
					carregarDadosTabela();
				}
			}
		};

		tabela = new TabelaModular<>(new ArrayList<>(), camposParaColunas, minhaAcaoEditar, minhaAcaoExcluir, null,
				null, true, true);

		// Adicionando ao Painel
		add(jlTitulo);
		add(jbAdicionar);
		add(tabela);

		// Posicionamento
		jlTitulo.setBounds(30, 25, 500, 30);
		jbAdicionar.setBounds(780, 20, 200, 50);
		tabela.setBounds(30, 90, 950, 570);

		carregarDadosTabela();
	}

	/**
	 * Método responsável por carregar os dados da tabela
	 */
	private void carregarDadosTabela() {
		List<Classificacao> classificacoesAtualizadas = dao.listar();
		tabela.atualizarDados(classificacoesAtualizadas);
	}

	/**
	 * Método onde estão os eventos presentes no painel
	 */
	private void criarEventos() {
		jbAdicionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroClassificacao painelCadastro = new PainelCadastroClassificacao(telaPrincipal);
				telaPrincipal.trocarPainel(painelCadastro);
			}
		});

	}
}
