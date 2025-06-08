package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.TabelaModular;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.model.MovimentacaoBancaria;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelListarTitulos extends JPanel {
	private JLabel jlTitulo;
	private JButton jbVoltar;
	private TabelaModular<MovimentacaoBancaria> tabela;
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	List<MovimentacaoBancaria> empresas = new ArrayList<MovimentacaoBancaria>();

	private TelaPrincipal telaPrincipal;

	public PainelListarTitulos(TelaPrincipal telaPrincipal) {
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
		jlTitulo = new JLabel("LISTAR TÍTULOS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Botão de voltar
		jbVoltar = new JButton("VOLTAR");
		jbVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		jbVoltar.setBackground(new Color(13, 33, 79));
		jbVoltar.setForeground(Color.WHITE);
		jbVoltar.setFocusPainted(false);
		jbVoltar.setBorderPainted(false);
		jbVoltar.setContentAreaFilled(false);
		jbVoltar.setOpaque(true);
		jbVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbVoltar.setSize(200, 50);

		// Colunas da tabela
		LinkedHashMap<String, String> camposParaColunas = new LinkedHashMap<>();
		camposParaColunas.put("descMov", "Descrição");
		camposParaColunas.put("valorMov", "Valor");
		camposParaColunas.put("dataMov", "Data");

		// Ações de editar e excluir
		Consumer<MovimentacaoBancaria> minhaAcaoEditar = (movimentacao) -> {
			PainelCadastroMovimentacao painelEditar = new PainelCadastroMovimentacao(telaPrincipal, movimentacao);
			telaPrincipal.trocarPainel(painelEditar);
		};

		Consumer<MovimentacaoBancaria> minhaAcaoExcluir = (movimentacao) -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esse título?",
					"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				String res = dao.excluir(movimentacao.getIdMov());
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
		add(jbVoltar);
		add(tabela);

		// Posicionamento
		jlTitulo.setBounds(30, 25, 500, 30);
		jbVoltar.setBounds(760, 20, 200, 50);
		tabela.setBounds(30, 80, 950, 570);

		carregarDadosTabela();
	}

	/**
	 * Método responsável por carregar os dados da tabela
	 */
	private void carregarDadosTabela() {
		List<MovimentacaoBancaria> movimentacaoAtualizada = dao.listarComClassificacao();
		tabela.atualizarDados(movimentacaoAtualizada);
	}
	
	/**
	 * Método onde estão os eventos presentes no painel
	 */
	private void criarEventos() {
		jbVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelTitulo painelTitulo = new PainelTitulo(telaPrincipal);
				telaPrincipal.trocarPainel(painelTitulo);
			}
		});

		jbVoltar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jbVoltar.setBorderPainted(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jbVoltar.setBorderPainted(true);
				jbVoltar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
