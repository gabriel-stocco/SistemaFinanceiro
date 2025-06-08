package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.FloatingLabelComboBox;
import com.sf.classes.FloatingLabelField;
import com.sf.classes.TipoMovimentacao;
import com.sf.dao.ClassificacaoDAO;
import com.sf.dao.ContaDAO;
import com.sf.dao.FornecedorDAO;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.model.Classificacao;
import com.sf.model.ContaBancaria;
import com.sf.model.Fornecedor;
import com.sf.model.MovimentacaoBancaria;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroMovimentacao extends JPanel {
	private JLabel jlTitulo;
	private FloatingLabelField fieldDesc, fieldValor, fieldData;
	private FloatingLabelComboBox<TipoMovimentacao> comboTipo;
	private FloatingLabelComboBox<Classificacao> comboClassificacao;
	private FloatingLabelComboBox<ContaBancaria> comboConta;
	private FloatingLabelComboBox<Fornecedor> comboFornecedor;
	private SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy");
	private JButton jbCadastrar, jbCancelar;
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	private MovimentacaoBancaria movimentacao;
	private List<Classificacao> classificacoes = new ArrayList<Classificacao>();
	private ClassificacaoDAO classDAO = new ClassificacaoDAO();
	private List<ContaBancaria> contas = new ArrayList<ContaBancaria>();
	private ContaDAO contaDAO = new ContaDAO();
	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	private FornecedorDAO fornDAO = new FornecedorDAO();

	private TelaPrincipal telaPrincipal;

	public PainelCadastroMovimentacao(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	public PainelCadastroMovimentacao(TelaPrincipal telaPrincipal, MovimentacaoBancaria movimentacao) {
		super();
		this.telaPrincipal = telaPrincipal;
		this.movimentacao = movimentacao;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();

		if (movimentacao != null) {
			preencherCampos(movimentacao);
			jlTitulo.setText("EDITAR EMPRESA");
			jbCadastrar.setText("ATUALIZAR");
		}
	}

	/**
	 * Preenche os campos caso esteja editando alguma movimentacao
	 * @param movimentacao - movimentacao a ser editada
	 */
	private void preencherCampos(MovimentacaoBancaria movimentacao) {
		fieldDesc.setText(movimentacao.getDescMov());
		fieldValor.setText(Float.toString(movimentacao.getValorMov()));
		fieldData.setText(formatoEntrada.format(movimentacao.getDataMov()));

		for (TipoMovimentacao tipo : TipoMovimentacao.values()) {
			if (tipo.getId().equalsIgnoreCase(movimentacao.getTipoMov())) {
				comboTipo.setSelectedItem(tipo);
				break;
			}
		}

		for (Classificacao classificacao : classificacoes) {
			if (classificacao.getIdClassificacao() == Integer.parseInt(movimentacao.getIdClassificacao().toString())) {
				comboClassificacao.setSelectedItem(classificacao);
				break;
			}
		}

		for (ContaBancaria conta : contas) {
			if (conta.getIdConta() == Integer.parseInt(movimentacao.getIdConta().toString())) {
				comboConta.setSelectedItem(conta);
				break;
			}
		}

		for (Fornecedor fornecedor : fornecedores) {
			if (fornecedor.getIdFornecedor() == Integer.parseInt(movimentacao.getIdFornecedor().toString())) {
				comboFornecedor.setSelectedItem(fornecedor);
				break;
			}
		}
	}

	/**
	 * Método onde inicializa os componentes do painel
	 */
	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("ADICIONAR PREVISÃO");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldDesc = new FloatingLabelField("Descrição", 600, null, true);
		fieldValor = new FloatingLabelField("Valor", 260, null, true);
		comboTipo = new FloatingLabelComboBox<>("Tipo", 300, true);
		comboTipo.setOptions(List.of(TipoMovimentacao.values()), TipoMovimentacao::getNome, TipoMovimentacao::getId);
		fieldData = new FloatingLabelField("Data da Movimentação", 350, "##-##-####", true);

		// Select de classificacao
		classificacoes = classDAO.listar();
		comboClassificacao = new FloatingLabelComboBox<>("Classificação", 430, true);
		comboClassificacao.setOptions(classificacoes, Classificacao::getNomClassificacao,
				Classificacao::getIdClassificacao);

		// Select de conta bancaria
		contas = contaDAO.listarComEmpresa();
		comboConta = new FloatingLabelComboBox<>("Conta Bancaria", 430, true);
		comboConta.setOptions(contas, c -> c.getNomeEmpresa() + " - " + c.getNumeroConta(), ContaBancaria::getIdConta);

		// Select de fornecedores
		fornecedores = fornDAO.listar();
		comboFornecedor = new FloatingLabelComboBox<>("Fornecedor", 430, false);
		comboFornecedor.setOptions(fornecedores, Fornecedor::getEmailForn, Fornecedor::getIdFornecedor);

		// Botão do formulario
		jbCadastrar = new JButton("SALVAR");
		jbCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		jbCadastrar.setBackground(new Color(13, 33, 79));
		jbCadastrar.setForeground(Color.WHITE);
		jbCadastrar.setFocusPainted(false);
		jbCadastrar.setBorderPainted(false);
		jbCadastrar.setContentAreaFilled(false);
		jbCadastrar.setOpaque(true);
		jbCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbCadastrar.setSize(150, 60);

		jbCancelar = new JButton("CANCELAR");
		jbCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		jbCancelar.setBackground(Color.WHITE);
		jbCancelar.setForeground(new Color(13, 33, 79));
		jbCancelar.setFocusPainted(false);
		jbCancelar.setBorderPainted(false);
		jbCancelar.setContentAreaFilled(false);
		jbCancelar.setOpaque(true);
		jbCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbCancelar.setSize(150, 60);

		// Adicionando ao Painel
		add(jlTitulo);
		add(fieldDesc);
		add(fieldValor);
		add(comboTipo);
		add(fieldData);
		add(comboClassificacao);
		add(comboConta);
		add(comboFornecedor);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldDesc.setBounds(30, 70, 600, 80);
		fieldValor.setBounds(690, 70, 260, 80);
		comboTipo.setBounds(30, 170, 130, 80);
		fieldData.setBounds(220, 170, 300, 80);
		comboClassificacao.setBounds(580, 170, 370, 80);
		comboConta.setBounds(30, 270, 430, 80);
		comboFornecedor.setBounds(520, 270, 430, 80);
		jbCadastrar.setBounds(800, 420, 150, 50);
		jbCancelar.setBounds(620, 420, 150, 50);
	}

	/**
	 * Método onde estão os eventos presentes no painel
	 */
	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String desc, tipo, dataTexto;
				float valor;
				int idClassificacao, idConta;
				Integer idFornecedor = null;
				if (!fieldDesc.getText().isEmpty() && !comboTipo.isEmpty() && !fieldValor.getText().isEmpty()
					&& !fieldData.getText().isEmpty() && !comboClassificacao.isEmpty() && !comboConta.isEmpty()) {
					desc = fieldDesc.getText();
					tipo = comboTipo.getSelectedValue().toString();
					valor = Float.parseFloat(fieldValor.getText());
					dataTexto = fieldData.getText();
					idClassificacao = (int) comboClassificacao.getSelectedValue();
					idConta = (int) comboConta.getSelectedValue();
					if (!comboFornecedor.isEmpty()) {
						idFornecedor = (Integer) comboFornecedor.getSelectedValue();
					}

					try {
						java.util.Date dataUtil = formatoEntrada.parse(dataTexto);

						java.sql.Date dataSQL = new java.sql.Date(dataUtil.getTime());

						if (movimentacao == null) {
							movimentacao = new MovimentacaoBancaria(desc, valor, tipo, dataSQL, idClassificacao,
									idConta, idFornecedor);
							String res = dao.salvar(movimentacao, true);
							JOptionPane.showMessageDialog(null, res, "Sistema Financeiro",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							movimentacao.setDescMov(desc);
							movimentacao.setValorMov(valor);
							movimentacao.setTipoMov(tipo);
							movimentacao.setDataMov(dataSQL);
							movimentacao.setIdClassificacao(idClassificacao);
							movimentacao.setIdConta(idConta);
							movimentacao.setIdFornecedor(idFornecedor);

							String res = dao.atualizar(movimentacao);
							JOptionPane.showMessageDialog(null, res, "Sistema Financeiro",
									JOptionPane.INFORMATION_MESSAGE);
						}

						if (jlTitulo.getText().equals("ADICIONAR PREVISÃO")) {
							PainelTitulo painelTitulo = new PainelTitulo(telaPrincipal);
							telaPrincipal.trocarPainel(painelTitulo);
						} else {
							PainelListarTitulos painelListar = new PainelListarTitulos(telaPrincipal);
							telaPrincipal.trocarPainel(painelListar);
						}
					} catch (ParseException ex) {
						JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd-MM-yyyy.", "Erro de Data",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Sistema Financeiro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		jbCadastrar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jbCadastrar.setBorderPainted(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jbCadastrar.setBorderPainted(true);
				jbCadastrar.setBorder(BorderFactory.createLineBorder(TelaPrincipal.COR_HOVER, 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		jbCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jlTitulo.getText().equals("ADICIONAR PREVISÃO")) {
					PainelTitulo painelTitulo = new PainelTitulo(telaPrincipal);
					telaPrincipal.trocarPainel(painelTitulo);
				} else {
					PainelListarTitulos painelListar = new PainelListarTitulos(telaPrincipal);
					telaPrincipal.trocarPainel(painelListar);
				}
			}
		});

		jbCancelar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jbCancelar.setBorderPainted(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jbCancelar.setBorderPainted(true);
				jbCancelar.setBorder(BorderFactory.createLineBorder(new Color(13, 33, 79), 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
