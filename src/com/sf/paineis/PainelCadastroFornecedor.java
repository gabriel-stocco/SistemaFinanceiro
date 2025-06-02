package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.FloatingLabelField;
import com.sf.model.Fornecedor;
import com.sf.model.FornecedorDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroFornecedor extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private static final Color COR_HOVER = new Color(200, 200, 200);
	private JLabel jlTitulo;
	private FloatingLabelField fieldEmail, fieldCnpj, fieldLogradouro;
	private JButton jbCadastrar, jbCancelar;
	private FornecedorDAO dao = new FornecedorDAO();
	private Fornecedor fornecedor;

	private TelaPrincipal telaPrincipal;

	public PainelCadastroFornecedor(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	public PainelCadastroFornecedor(TelaPrincipal telaPrincipal, Fornecedor fornecedor) {
		super();
		this.telaPrincipal = telaPrincipal;
		this.fornecedor = fornecedor;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();

		if (fornecedor != null) {
			preencherCampos(fornecedor);
			jlTitulo.setText("EDITAR FORNECEDOR");
			jbCadastrar.setText("ATUALIZAR");
		}
	}

	private void preencherCampos(Fornecedor fornecedor) {
		fieldEmail.setText(fornecedor.getEmailForn());
		fieldCnpj.setText(fornecedor.getCnjpForn());
		fieldLogradouro.setText(fornecedor.getEnderecoForn());
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("ADICIONAR FORNECEDOR");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldEmail = new FloatingLabelField("Email do Fornecedor", 430, null);
		fieldCnpj = new FloatingLabelField("CNPJ do Fornecedor", 430, "##.###.###/####-##");
		fieldLogradouro = new FloatingLabelField("Endereço", 920, null);

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
		add(fieldEmail);
		add(fieldCnpj);
		add(fieldLogradouro);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldEmail.setBounds(30, 70, 430, 80);
		fieldCnpj.setBounds(520, 70, 430, 80);
		fieldLogradouro.setBounds(30, 170, 920, 80);
		jbCadastrar.setBounds(800, 320, 150, 50);
		jbCancelar.setBounds(620, 320, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cnpj, endereco, email;
				if (!fieldEmail.getText().isEmpty() && !fieldCnpj.isEmpty() && !fieldLogradouro.getText().isEmpty()) {
					cnpj = fieldCnpj.getText().replaceAll("[^\\d]", "");
					endereco = fieldLogradouro.getText();
					email = fieldEmail.getText();

					if (fornecedor == null) {
						fornecedor = new Fornecedor(cnpj, endereco, email);
						String res = dao.salvar(fornecedor);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					} else {
						fornecedor.setEmailForn(email);
						fornecedor.setCnjpForn(cnpj);
						fornecedor.setEnderecoForn(endereco);
						String res = dao.atualizar(fornecedor);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					}

					PainelListarFornecedor painelListagem = new PainelListarFornecedor(telaPrincipal);
					telaPrincipal.trocarPainel(painelListagem);

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
				jbCadastrar.setBorder(BorderFactory.createLineBorder(COR_HOVER, 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		jbCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelListarFornecedor painelListagem = new PainelListarFornecedor(telaPrincipal);
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
