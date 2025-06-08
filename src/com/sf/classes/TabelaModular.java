package com.sf.classes;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class TabelaModular<T> extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private final JLabel labelSemDados = new JLabel("Nenhum dado disponível.", SwingConstants.CENTER);
	private final List<T> objetos;
	private final LinkedHashMap<String, String> camposParaColunas;
	private final Consumer<T> acaoEditar;
	private final Consumer<T> acaoExcluir;
	private final String nomeBotaoEditar;
	private final String nomeBotaoExcluir;
	private final boolean mostrarBotaoEditar;
	private final boolean mostrarBotaoExcluir;
	private JScrollPane scrollPane;
	private CardLayout cardLayout;
	private JPanel painelCentral;

	public TabelaModular(List<T> objetos, LinkedHashMap<String, String> camposParaColunas, Consumer<T> acaoEditar,
			Consumer<T> acaoExcluir, String nomeBotaoEditar, String nomeBotaoExcluir, boolean mostrarBotaoEditar,
			boolean mostrarBotaoExcluir) {
		this.objetos = (objetos != null) ? new ArrayList<>(objetos) : new ArrayList<>();
		this.camposParaColunas = camposParaColunas;
		this.acaoEditar = acaoEditar;
		this.acaoExcluir = acaoExcluir;

		this.nomeBotaoEditar = (nomeBotaoEditar == null || nomeBotaoEditar.trim().isEmpty()) ? "Editar"
				: nomeBotaoEditar;
		this.nomeBotaoExcluir = (nomeBotaoExcluir == null || nomeBotaoExcluir.trim().isEmpty()) ? "Excluir"
				: nomeBotaoExcluir;

		this.mostrarBotaoEditar = mostrarBotaoEditar;
		this.mostrarBotaoExcluir = mostrarBotaoExcluir;

		setLayout(new BorderLayout());
		setBackground(COR_CONTEUDO);

		String[] colunas;
		if (camposParaColunas == null || camposParaColunas.isEmpty()) {
			colunas = new String[] { "Ações" };
		} else {
			colunas = new String[camposParaColunas.size() + 1];
			int idx = 0;
			for (String nome : camposParaColunas.values()) {
				colunas[idx++] = nome;
			}
			colunas[idx] = "Ações";
		}

		DefaultTableModel model = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == getColumnCount() - 1;
			}
		};

		preencherModeloComObjetos(model, this.objetos);

		JTable tabela = new JTable(model);
		tabela.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		tabela.setRowHeight(40);
		tabela.setFocusable(false);

		tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (column == table.getColumn("Ações").getModelIndex()) {
					JPanel panel = criarPainelBotoesApenasVisual();
					panel.setBackground(Color.WHITE);
					return panel;
				} else {
					JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setBackground(Color.WHITE);
					return label;
				}
			}
		});

		if (tabela.getColumnCount() > 0 && tabela.getColumn("Ações") != null) {
			tabela.getColumn("Ações").setCellEditor(new AcoesCellEditor());
		}

		JTableHeader header = tabela.getTableHeader();
		header.setBackground(new Color(13, 33, 79));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		header.setPreferredSize(new Dimension(header.getWidth(), 35));

		this.scrollPane = new JScrollPane(tabela);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.scrollPane.setBackground(COR_CONTEUDO);
		this.scrollPane.getViewport().setBackground(COR_CONTEUDO);
		
		// Criar painel com CardLayout
		CardLayout cardLayout = new CardLayout();
		JPanel painelCentral = new JPanel(cardLayout);

		// Placeholder
		labelSemDados.setFont(new Font("Segoe UI", Font.ITALIC, 24));
		labelSemDados.setForeground(Color.WHITE);
		labelSemDados.setHorizontalAlignment(SwingConstants.CENTER);
		labelSemDados.setVerticalAlignment(SwingConstants.CENTER);

		// Painel com label
		JPanel painelPlaceholder = new JPanel(new BorderLayout());
		painelPlaceholder.add(labelSemDados, BorderLayout.CENTER);
		painelPlaceholder.setBackground(COR_CONTEUDO);

		// Adicionar os dois "cards"
		painelCentral.add(this.scrollPane, "tabela");
		painelCentral.add(painelPlaceholder, "vazio");

		add(painelCentral, BorderLayout.CENTER);

		// Mostrar o card certo
		if (objetos.isEmpty()) {
			cardLayout.show(painelCentral, "vazio");
		} else {
			cardLayout.show(painelCentral, "tabela");
		}

		// Guardar o layout e painel central em campos (para uso posterior)
		this.cardLayout = cardLayout;
		this.painelCentral = painelCentral;
	}

	/**
	 * Preenche o modelo de tabela com a lista de objetos que eu quiser
	 * @param model - modelo da tabela a ser adicionado os dados
	 * @param objetosParaPreencher - lista com os dados para preencher a tabela
	 */
	private void preencherModeloComObjetos(DefaultTableModel model, List<T> objetosParaPreencher) {
		if (objetosParaPreencher == null || objetosParaPreencher.isEmpty()) {
			return;
		}

		for (T obj : objetosParaPreencher) {
			Object[] linha = new Object[this.camposParaColunas.size() + 1];
			int i = 0;

			for (String nomeCampo : this.camposParaColunas.keySet()) {
				String nomeColuna = this.camposParaColunas.get(nomeCampo);

				try {
					Field campo = obj.getClass().getDeclaredField(nomeCampo);
					campo.setAccessible(true);
					Object valor = campo.get(obj);

					if (valor != null) {
						// Aplica máscara se necessário
						if (nomeColuna.toLowerCase().contains("cnpj")) {
							linha[i] = formatarCNPJ(valor.toString());
						} else if (nomeColuna.toLowerCase().contains("data")) {
							linha[i] = formatarData(valor);
						} else {
							linha[i] = valor;
						}
					} else {
						linha[i] = "";
					}

				} catch (NoSuchFieldException | IllegalAccessException e) {
					linha[i] = "Erro";
					System.err.println("Erro ao acessar campo " + nomeCampo + " para objeto "
							+ obj.getClass().getSimpleName() + ": " + e.getMessage());
				}
				i++;
			}

			linha[i] = "Ações";
			model.addRow(linha);
		}
	}


	/**
	 * Atualiza os dados da tabela quando houver necessidade
	 * @param novosObjetos - lista dos novos objetos que vão estar na tabela
	 */
	public void atualizarDados(List<T> novosObjetos) {
		this.objetos.clear();
		if (novosObjetos != null) {
			this.objetos.addAll(novosObjetos);
		}

		if (this.scrollPane != null && this.scrollPane.getViewport() != null
				&& this.scrollPane.getViewport().getView() instanceof JTable) {
			JTable table = (JTable) this.scrollPane.getViewport().getView();
			DefaultTableModel model = (DefaultTableModel) table.getModel();

			if (table.getCellEditor() != null) {
				table.getCellEditor().stopCellEditing();
			}

			model.setRowCount(0);

			preencherModeloComObjetos(model, this.objetos);

			model.fireTableDataChanged();
			
			if (this.objetos.isEmpty()) {
				cardLayout.show(painelCentral, "vazio");
			} else {
				cardLayout.show(painelCentral, "tabela");
			}

		} else {
			System.err.println(
					"Erro: JScrollPane ou JTable não inicializados corretamente em TabelaModular para atualizarDados.");
		}
	}

	/**
	 * Cria a parte visual dos botões de editar e excluir da tabela
	 * @return - painel com os dois botões
	 */
	private JPanel criarPainelBotoesApenasVisual() {
		JPanel painel = new JPanel(new GridBagLayout());
		painel.setOpaque(true);

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		botoes.setOpaque(false);

		if (mostrarBotaoEditar) {
			JButton jbEditar = new JButton(nomeBotaoEditar);
			jbEditar.setFocusPainted(false);
			jbEditar.setBackground(new Color(14, 122, 13));
			jbEditar.setForeground(Color.WHITE);
			jbEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
			botoes.add(jbEditar);
		}

		if (mostrarBotaoExcluir) {
			JButton jbExcluir = new JButton(nomeBotaoExcluir);
			jbExcluir.setFocusPainted(false);
			jbExcluir.setBackground(new Color(153, 0, 0));
			jbExcluir.setForeground(Color.WHITE);
			jbExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
			botoes.add(jbExcluir);
		}

		painel.add(botoes);
		return painel;
	}

	/**
	 * Classe interna com as ações dos botões de editar e excluir
	 */
	class AcoesCellEditor extends AbstractCellEditor implements TableCellEditor {
		private JPanel editorComponent;
		private int currentRow;

		public AcoesCellEditor() {
			editorComponent = new JPanel(new GridBagLayout());
			editorComponent.setOpaque(true);

			JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
			botoes.setOpaque(false);

			if (mostrarBotaoEditar) {
				JButton jbEditar = new JButton(nomeBotaoEditar);
				jbEditar.setFocusPainted(false);
				jbEditar.setBackground(new Color(14, 122, 13));
				jbEditar.setForeground(Color.WHITE);
				jbEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
				jbEditar.addActionListener((ActionEvent e) -> {
					T objeto = TabelaModular.this.objetos.get(currentRow);
					acaoEditar.accept(objeto);
					fireEditingStopped();
				});
				botoes.add(jbEditar);
			}

			if (mostrarBotaoExcluir) {
				JButton jbExcluir = new JButton(nomeBotaoExcluir);
				jbExcluir.setFocusPainted(false);
				jbExcluir.setBackground(new Color(153, 0, 0));
				jbExcluir.setForeground(Color.WHITE);
				jbExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
				jbExcluir.addActionListener((ActionEvent e) -> {
					T objeto = TabelaModular.this.objetos.get(currentRow);
					acaoExcluir.accept(objeto);
					fireEditingStopped();
				});
				botoes.add(jbExcluir);
			}

			editorComponent.add(botoes);
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.currentRow = row;
			editorComponent.setBackground(Color.WHITE);
			return editorComponent;
		}
	}
	
	/**
	 * Método para formatar cnpj e mostra-lo melhor na tabela
	 * @param cnpj - cnpj a ser adicionado a máscara
	 * @return - cnpj com a máscara
	 */
	private String formatarCNPJ(String cnpj) {
		cnpj = cnpj.replaceAll("[^\\d]", "");
		if (cnpj.length() != 14) return cnpj;
		return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	}

	/**
	 * Método para formatar uma data e mostra-lá melhor na tabela
	 * @param valor - data a ser adicionada a máscara
	 * @return - data com a máscara
	 */
	private String formatarData(Object valor) {
		if (valor instanceof java.time.LocalDate) {
			return ((java.time.LocalDate) valor).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} else if (valor instanceof java.util.Date) {
			return new java.text.SimpleDateFormat("dd/MM/yyyy").format((java.util.Date) valor);
		} else if (valor instanceof String) {
			try {
				// Tenta converter string ISO-8601
				java.time.LocalDate data = java.time.LocalDate.parse((String) valor);
				return data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			} catch (Exception e) {
				return valor.toString(); // Retorna original se falhar
			}
		}
		return valor.toString();
	}

}