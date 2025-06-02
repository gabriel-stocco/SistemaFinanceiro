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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.FloatingLabelComboBox;
import com.sf.classes.FloatingLabelField;
import com.sf.model.Classificacao;
import com.sf.model.ClassificacaoDAO;
import com.sf.model.MovimentacaoBancaria;
import com.sf.model.MovimentacaoBancariaDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroClassificar extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private FloatingLabelField fieldDesc;
	private JButton jbCadastrar, jbCancelar;
	private FloatingLabelComboBox<Classificacao> comboClassificacao;
	private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();
	private MovimentacaoBancaria movimentacao;
	private ClassificacaoDAO classiDAO = new ClassificacaoDAO();
	private List<Classificacao> classificacoes = new ArrayList<Classificacao>();

	private TelaPrincipal telaPrincipal;

	public PainelCadastroClassificar(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	public PainelCadastroClassificar(TelaPrincipal telaPrincipal, MovimentacaoBancaria movimentacao) {
		super();
		this.telaPrincipal = telaPrincipal;
		this.movimentacao = movimentacao;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();

		if (movimentacao != null) {
			preencherCampos(movimentacao);
			jlTitulo.setText("CLASSIFICAR MOVIMENTAÇÃO");
			jbCadastrar.setText("CLASSIFICAR");
		}
	}

	private void preencherCampos(MovimentacaoBancaria movimentacao) {
		fieldDesc.setText(movimentacao.getDescMov());
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel();
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldDesc = new FloatingLabelField("Descrição", 540, null);

		// Select de classificacao
		classificacoes = classiDAO.listar();
		comboClassificacao = new FloatingLabelComboBox<>("Classificação", 330);
		comboClassificacao.setOptions(classificacoes, Classificacao::getNomClassificacao,
				Classificacao::getIdClassificacao);

		// Botão do formulario
		jbCadastrar = new JButton();
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
		add(comboClassificacao);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldDesc.setBounds(30, 70, 540, 80);
		comboClassificacao.setBounds(620, 70, 330, 80);
		jbCadastrar.setBounds(770, 200, 180, 50);
		jbCancelar.setBounds(590, 200, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {
			String desc;
			int idClassificacao;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!fieldDesc.getText().isEmpty()) {
					desc = fieldDesc.getText();
					idClassificacao = (int) comboClassificacao.getSelectedValue();

					movimentacao.setDescMov(desc);
					movimentacao.setIdClassificacao(idClassificacao);
					String res = dao.classificar(movimentacao);
					JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);

					PainelClassificar painelClassificar = new PainelClassificar(telaPrincipal);
					telaPrincipal.trocarPainel(painelClassificar);

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
				jbCadastrar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		jbCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelClassificar painelListagem = new PainelClassificar(telaPrincipal);
				telaPrincipal.trocarPainel(painelListagem);
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
