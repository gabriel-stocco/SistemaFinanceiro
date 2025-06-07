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
import com.sf.dao.EmpresaDAO;
import com.sf.model.Empresa;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroEmpresa extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private FloatingLabelField fieldNome, fieldCnpj, fieldLogradouro;
	private JButton jbCadastrar, jbCancelar;
	private Empresa empresa;
	private EmpresaDAO dao = new EmpresaDAO();

	private TelaPrincipal telaPrincipal;

	public PainelCadastroEmpresa(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	public PainelCadastroEmpresa(TelaPrincipal telaPrincipal, Empresa empresa) {
		super();
		this.telaPrincipal = telaPrincipal;
		this.empresa = empresa;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();

		if (empresa != null) {
			preencherCampos(empresa);
			jlTitulo.setText("EDITAR EMPRESA");
			jbCadastrar.setText("ATUALIZAR");
		}
	}

	private void preencherCampos(Empresa empresa) {
		fieldNome.setText(empresa.getNome_Emp());
		fieldCnpj.setText(empresa.getCnpj_Emp());
		fieldLogradouro.setText(empresa.getEndereco_Emp());
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("ADICIONAR EMPRESA");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldNome = new FloatingLabelField("Nome da Empresa", 430, null);
		fieldCnpj = new FloatingLabelField("CNPJ da Empresa", 430, "##.###.###/####-##");
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
		add(fieldNome);
		add(fieldCnpj);
		add(fieldLogradouro);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldNome.setBounds(30, 70, 430, 80);
		fieldCnpj.setBounds(520, 70, 430, 80);
		fieldLogradouro.setBounds(30, 170, 920, 80);
		jbCadastrar.setBounds(800, 320, 150, 50);
		jbCancelar.setBounds(620, 320, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nome, cnpj, endereco;
				if (!fieldNome.getText().isEmpty() && !fieldCnpj.isEmpty() && !fieldLogradouro.getText().isEmpty()) {
					nome = fieldNome.getText();
					cnpj = fieldCnpj.getText().replaceAll("[^\\d]", "");
					endereco = fieldLogradouro.getText();

					if (empresa == null) {
						empresa = new Empresa(endereco, cnpj, nome);
						String res = dao.salvar(empresa);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					} else {
						empresa.setNome_Emp(nome);
						empresa.setCnpj_Emp(cnpj);
						empresa.setEndereco_Emp(endereco);

						String res = dao.atualizar(empresa);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					}

					PainelListarEmpresa painelListar = new PainelListarEmpresa(telaPrincipal);
					telaPrincipal.trocarPainel(painelListar);

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
				PainelListarEmpresa painelListar = new PainelListarEmpresa(telaPrincipal);
				telaPrincipal.trocarPainel(painelListar);
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
