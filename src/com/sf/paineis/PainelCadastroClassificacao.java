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
import com.sf.menu.Classificacao;

@SuppressWarnings("serial")
public class PainelCadastroClassificacao extends JPanel {
	private static final Color COR_CONTEUDO = new Color(200, 200, 200);
	private JLabel jlTitulo;
	private FloatingLabelField fieldCodigo, fieldNome;
	private JButton jbCadastrar;
	private Classificacao classificacao;

	public PainelCadastroClassificacao() {
		super();
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("CADASTRO CLASSIFICAÇÕES");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldCodigo = new FloatingLabelField("Código da Classificação", 430);
		fieldNome = new FloatingLabelField("Nome da Classificação", 430);

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
		add(fieldCodigo);
		add(fieldNome);
		add(jbCadastrar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldCodigo.setBounds(30, 70, 430, 80);
		fieldNome.setBounds(520, 70, 430, 80);
		jbCadastrar.setBounds(800, 200, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String codigo, nome;
				if (!fieldCodigo.getText().isEmpty() && !fieldNome.getText().isEmpty()) {
					codigo = fieldCodigo.getText();
					nome = fieldNome.getText();

					classificacao = new Classificacao(nome);
				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Sistema Financeiro",
							JOptionPane.ERROR_MESSAGE);
				}

				fieldCodigo.setText("");
				fieldNome.setText("");
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
