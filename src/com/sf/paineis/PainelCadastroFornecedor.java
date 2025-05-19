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
import com.sf.menu.Fornecedor;

@SuppressWarnings("serial")
public class PainelCadastroFornecedor extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private FloatingLabelField fieldEmail, fieldCnpj, fieldCep, fieldLogradouro, fieldBairro, fieldNumero, fieldCidade;
	private JButton jbCadastrar;
	private Fornecedor fornecedor;

	public PainelCadastroFornecedor() {
		super();
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("CADASTRO FORNECEDORES");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldEmail = new FloatingLabelField("Email do Fornecedor", 430);
		fieldCnpj = new FloatingLabelField("CNPJ do Fornecedor", 430);
		fieldCep = new FloatingLabelField("CEP", 300);
		fieldLogradouro = new FloatingLabelField("Logradouro", 350);
		fieldNumero = new FloatingLabelField("Número", 130);
		fieldBairro = new FloatingLabelField("Bairro", 430);
		fieldCidade = new FloatingLabelField("Cidade", 430);

		// Botão do formulario
		jbCadastrar = new JButton("Salvar");
		jbCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		jbCadastrar.setBackground(new Color(100, 100, 100));
		jbCadastrar.setForeground(Color.WHITE);
		jbCadastrar.setFocusPainted(false);
		jbCadastrar.setBorderPainted(false);
		jbCadastrar.setContentAreaFilled(false);
		jbCadastrar.setOpaque(true);
		jbCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jbCadastrar.setSize(150, 60);

		// Adicionando ao Painel
		add(jlTitulo);
		add(fieldEmail);
		add(fieldCnpj);
		add(fieldCep);
		add(fieldLogradouro);
		add(fieldNumero);
		add(fieldBairro);
		add(fieldCidade);
		add(jbCadastrar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldEmail.setBounds(30, 70, 430, 80);
		fieldCnpj.setBounds(520, 70, 430, 80);
		fieldCep.setBounds(30, 170, 300, 80);
		fieldLogradouro.setBounds(400, 170, 350, 80);
		fieldNumero.setBounds(820, 170, 130, 80);
		fieldBairro.setBounds(30, 270, 430, 80);
		fieldCidade.setBounds(520, 270, 430, 80);
		jbCadastrar.setBounds(800, 420, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email, cnpj, endereco;
				if (!fieldEmail.getText().isEmpty() && !fieldCnpj.getText().isEmpty() && !fieldCep.getText().isEmpty()
						&& !fieldLogradouro.getText().isEmpty() && !fieldBairro.getText().isEmpty()
						&& !fieldNumero.getText().isEmpty() && !fieldCidade.getText().isEmpty()) {
					email = fieldEmail.getText();
					cnpj = fieldCnpj.getText();
					endereco = fieldLogradouro.getText() + " " + fieldNumero.getText() + " " + fieldBairro.getText()
							+ " " + fieldCidade.getText();

					fornecedor = new Fornecedor(Integer.parseInt(cnpj), endereco, email);
				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Sistema Financeiro",
							JOptionPane.ERROR_MESSAGE);
				}

				fieldEmail.setText("");
				fieldCnpj.setText("");
				fieldCep.setText("");
				fieldLogradouro.setText("");
				fieldBairro.setText("");
				fieldNumero.setText("");
				fieldCidade.setText("");
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
				jbCadastrar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
