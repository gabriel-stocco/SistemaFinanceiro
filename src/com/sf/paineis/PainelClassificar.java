package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sf.classes.TabelaModular;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.model.MovimentacaoBancaria;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelClassificar extends JPanel {
	private JLabel jlTitulo;
	private TabelaModular<MovimentacaoBancaria> tabela;
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	List<MovimentacaoBancaria> empresas = new ArrayList<MovimentacaoBancaria>();

	private TelaPrincipal telaPrincipal;

	public PainelClassificar(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("TRANSFERIR MOVIMENTAÇÕES");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Colunas da tabela
		LinkedHashMap<String, String> camposParaColunas = new LinkedHashMap<>();
		camposParaColunas.put("valorMov", "Valor");
		camposParaColunas.put("tipoMov", "Tipo");
		camposParaColunas.put("dataMov", "Data");

		// Ação de editar
		Consumer<MovimentacaoBancaria> minhaAcaoEditar = (movimentacao) -> {
			PainelCadastroClassificar painelEditar = new PainelCadastroClassificar(telaPrincipal, movimentacao);
			telaPrincipal.trocarPainel(painelEditar);
		};

		tabela = new TabelaModular<>(new ArrayList<>(), camposParaColunas, minhaAcaoEditar, null, "Classificar", null,
				true, false);

		// Adicionando ao Painel
		add(jlTitulo);
		add(tabela);

		// Posicionamento
		jlTitulo.setBounds(30, 25, 500, 30);
		tabela.setBounds(30, 80, 950, 570);

		carregarDadosTabela();
	}

	private void carregarDadosTabela() {
		List<MovimentacaoBancaria> movimentacaoAtualizada = dao.listarSemClassificacao();
		tabela.atualizarDados(movimentacaoAtualizada);
	}

}
