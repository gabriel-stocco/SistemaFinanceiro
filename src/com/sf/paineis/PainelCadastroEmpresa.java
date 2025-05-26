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
import com.sf.model.Empresa;
import com.sf.model.EmpresaDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroEmpresa extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private FloatingLabelField fieldNome, fieldCnpj, fieldCep, fieldLogradouro, fieldBairro, fieldNumero, fieldCidade;
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

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("CADASTRO EMPRESAS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldNome = new FloatingLabelField("Nome da Empresa", 430, null);
		fieldCnpj = new FloatingLabelField("CNPJ da Empresa", 430, "##.###.###/####-##");
		fieldCep = new FloatingLabelField("CEP", 300, "#####-##");
		fieldLogradouro = new FloatingLabelField("Logradouro", 350, null);
		fieldNumero = new FloatingLabelField("Número", 130, null);
		fieldBairro = new FloatingLabelField("Bairro", 430, null);
		fieldCidade = new FloatingLabelField("Cidade", 430, null);

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
		add(fieldCep);
		add(fieldLogradouro);
		add(fieldNumero);
		add(fieldBairro);
		add(fieldCidade);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldNome.setBounds(30, 70, 430, 80);
		fieldCnpj.setBounds(520, 70, 430, 80);
		fieldCep.setBounds(30, 170, 300, 80);
		fieldLogradouro.setBounds(400, 170, 350, 80);
		fieldNumero.setBounds(820, 170, 130, 80);
		fieldBairro.setBounds(30, 270, 430, 80);
		fieldCidade.setBounds(520, 270, 430, 80);
		jbCadastrar.setBounds(800, 420, 150, 50);
		jbCancelar.setBounds(620, 420, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id, nome, cnpj, endereco;
				if (!fieldNome.getText().isEmpty() && !fieldCnpj.isEmpty() && !fieldCep.isEmpty()
						&& !fieldLogradouro.getText().isEmpty() && !fieldBairro.getText().isEmpty()
						&& !fieldNumero.getText().isEmpty() && !fieldCidade.getText().isEmpty()) {
					id = fieldNumero.getText();
					nome = fieldNome.getText();
					cnpj = fieldCnpj.getText().replaceAll("[^\\d]", "");
					endereco = fieldLogradouro.getText() + " " + fieldNumero.getText() + " " + fieldBairro.getText()
							+ " " + fieldCidade.getText();

					empresa = new Empresa(Integer.parseInt(id), endereco, cnpj, nome);
					
					String res = dao.salvar(empresa);
					
					JOptionPane.showMessageDialog(null, res, "Sistema Financeiro",
							JOptionPane.INFORMATION_MESSAGE);
					
					PainelListarEmpresa painelListar = new PainelListarEmpresa(telaPrincipal);
					if (telaPrincipal != null) {
						telaPrincipal.trocarPainel(painelListar);
					} else {
						JOptionPane.showMessageDialog(PainelCadastroEmpresa.this, "Erro: Tela principal não referenciada.");
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
				if (telaPrincipal != null) {
					telaPrincipal.trocarPainel(painelListar);
				} else {
					JOptionPane.showMessageDialog(PainelCadastroEmpresa.this, "Erro: Tela principal não referenciada.");
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
