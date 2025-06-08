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
import com.sf.dao.BancoDAO;
import com.sf.dao.ContaDAO;
import com.sf.dao.EmpresaDAO;
import com.sf.model.Banco;
import com.sf.model.ContaBancaria;
import com.sf.model.Empresa;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroConta extends JPanel {
	private JLabel jlTitulo;
	private FloatingLabelField fieldAgencia, fieldNumeroConta, fieldSaldo;
	private FloatingLabelComboBox<Banco> comboBanco;
	private FloatingLabelComboBox<Empresa> comboEmpresa;
	private JButton jbCadastrar, jbCancelar;
	private ContaBancaria conta;
	private ContaDAO dao = new ContaDAO();
	private List<Banco> bancos = new ArrayList<Banco>();
	private BancoDAO bancoDAO = new BancoDAO();
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private EmpresaDAO empresaDAO = new EmpresaDAO();

	private TelaPrincipal telaPrincipal;

	public PainelCadastroConta(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	public PainelCadastroConta(TelaPrincipal telaPrincipal, ContaBancaria conta) {
		super();
		this.telaPrincipal = telaPrincipal;
		this.conta = conta;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();

		if (conta != null) {
			preencherCampos(conta);
			jlTitulo.setText("EDITAR CONTA BANCÁRIA");
			jbCadastrar.setText("ATUALIZAR");
		}
	}

	private void preencherCampos(ContaBancaria conta) {
		fieldAgencia.setText(conta.getAgencia());
		fieldNumeroConta.setText(conta.getNumeroConta());
		fieldSaldo.setText(Float.toString(conta.getSaldo()));

		for (Banco banco : bancos) {
			if (banco.getIdBanco() == conta.getIdBanco()) {
				comboBanco.setSelectedItem(banco);
				break;
			}
		}

		for (Empresa empresa : empresas) {
			if (empresa.getEmpresa() == conta.getIdEmpresa()) {
				comboEmpresa.setSelectedItem(empresa);
				break;
			}
		}
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("ADICIONAR CONTA BANCÁRIA");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Campos do Formulario
		fieldAgencia = new FloatingLabelField("Agência", 263, null, true);
		fieldNumeroConta = new FloatingLabelField("Número da Conta", 263, "########-#", true);
		fieldSaldo = new FloatingLabelField("Saldo Atual", 265, null, true);

		// Select de fornecedores
		empresas = empresaDAO.listar();
		comboEmpresa = new FloatingLabelComboBox<>("Empresa", 430, true);
		comboEmpresa.setOptions(empresas, Empresa::getNome_Emp, Empresa::getEmpresa);

		// Select de fornecedores
		bancos = bancoDAO.listar();
		comboBanco = new FloatingLabelComboBox<>("Banco", 430, true);
		comboBanco.setOptions(bancos, Banco::getNomeBanco, Banco::getIdBanco);

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
		add(fieldAgencia);
		add(fieldNumeroConta);
		add(fieldSaldo);
		add(comboEmpresa);
		add(comboBanco);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldAgencia.setBounds(30, 70, 263, 80);
		fieldNumeroConta.setBounds(353, 70, 263, 80);
		fieldSaldo.setBounds(676, 70, 265, 80);
		comboEmpresa.setBounds(30, 170, 430, 80);
		comboBanco.setBounds(520, 170, 430, 80);
		jbCadastrar.setBounds(800, 320, 150, 50);
		jbCancelar.setBounds(620, 320, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String agencia, numero;
				int idEmpresa, idBanco;
				float saldo;
				if (!fieldAgencia.getText().isEmpty() && !fieldNumeroConta.isEmpty() && !fieldSaldo.getText().isEmpty()
						&& !comboEmpresa.isEmpty() && !comboBanco.isEmpty()) {
					agencia = fieldAgencia.getText();
					numero = fieldNumeroConta.getText().replaceAll("[^\\d]", "");
					saldo = Float.parseFloat(fieldSaldo.getText());
					idEmpresa = (int) comboEmpresa.getSelectedValue();
					idBanco = (int) comboBanco.getSelectedValue();

					if (conta == null) {
						conta = new ContaBancaria(agencia, saldo, numero, idBanco, idEmpresa);
						String res = dao.salvar(conta);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					} else {
						conta.setAgencia(agencia);
						conta.setNumeroConta(numero);
						conta.setSaldo(saldo);
						conta.setIdEmpresa(idEmpresa);
						conta.setIdBanco(idBanco);

						String res = dao.atualizar(conta);
						JOptionPane.showMessageDialog(null, res, "Sistema Financeiro", JOptionPane.INFORMATION_MESSAGE);
					}

					PainelListarContas painelListar = new PainelListarContas(telaPrincipal);
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
				PainelListarContas painelListar = new PainelListarContas(telaPrincipal);
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
