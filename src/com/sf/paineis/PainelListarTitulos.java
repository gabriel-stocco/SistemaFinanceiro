package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.TabelaModular;
import com.sf.model.MovimentacaoBancaria;
import com.sf.model.MovimentacaoBancariaDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelListarTitulos extends JPanel {
	private final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private TabelaModular<MovimentacaoBancaria> tabela;
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	List<MovimentacaoBancaria> empresas = new ArrayList<MovimentacaoBancaria>();

	private TelaPrincipal telaPrincipal;

	public PainelListarTitulos(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("LISTAR TÍTULOS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Colunas da tabela
		LinkedHashMap<String, String> camposParaColunas = new LinkedHashMap<>();
		camposParaColunas.put("descMov", "Descrição");
		camposParaColunas.put("tipoMov", "Tipo");
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
		add(tabela);

		// Posicionamento
		jlTitulo.setBounds(30, 25, 500, 30);
		tabela.setBounds(30, 80, 950, 570);

		carregarDadosTabela();
	}

	private void carregarDadosTabela() {
		List<MovimentacaoBancaria> movimentacaoAtualizada = dao.listarComClassificacao();
		tabela.atualizarDados(movimentacaoAtualizada);
	}
}
