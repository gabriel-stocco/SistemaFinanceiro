package com.sf.paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sf.classes.TabelaModular;
import com.sf.dao.EmpresaDAO;
import com.sf.model.Empresa;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelListarEmpresa extends JPanel {
	private TabelaModular<Empresa> tabela;
	private JLabel jlTitulo;
	private JButton jbAdicionar;
	private EmpresaDAO dao = new EmpresaDAO();
	List<Empresa> empresas = new ArrayList<Empresa>();

	private TelaPrincipal telaPrincipal;

	public PainelListarEmpresa(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(TelaPrincipal.COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("LISTAR EMPRESAS");
		jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		jlTitulo.setForeground(Color.WHITE);
		jlTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		jbAdicionar = new JButton("ADICIONAR");
		jbAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 20));
		jbAdicionar.setBackground(new Color(13, 33, 79));
		jbAdicionar.setForeground(Color.WHITE);
		jbAdicionar.setFocusPainted(false);
		jbAdicionar.setBorderPainted(false);
		jbAdicionar.setContentAreaFilled(false);
		jbAdicionar.setOpaque(true);
		jbAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Colunas da tabela
		LinkedHashMap<String, String> camposParaColunas = new LinkedHashMap<>();
		camposParaColunas.put("nomeEmp", "Nome");
		camposParaColunas.put("cnpjEmp", "CNPJ");

		// DEFININDO AS AÇÕES DE EDITAR E EXCLUIR AQUI
		Consumer<Empresa> minhaAcaoEditar = (empresa) -> {
			PainelCadastroEmpresa painelCadastro = new PainelCadastroEmpresa(telaPrincipal, empresa);
			telaPrincipal.trocarPainel(painelCadastro);
		};

		Consumer<Empresa> minhaAcaoExcluir = (empresa) -> {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Tem certeza que deseja excluir: " + empresa.getNome_Emp() + "?", "Confirmação de Exclusão",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				String res = dao.excluir(empresa.getEmpresa());
				JOptionPane.showMessageDialog(this, res);

				if (res.contains("sucesso")) {
					carregarDadosTabela();
				}
			}
		};

		tabela = new TabelaModular<>(new ArrayList<>(), camposParaColunas, minhaAcaoEditar, minhaAcaoExcluir, null,
				null, true, true);

		// Adicionando ao Painel
		add(jlTitulo);
		add(jbAdicionar);
		add(tabela);

		// Posicionamento
		jlTitulo.setBounds(30, 25, 500, 30);
		jbAdicionar.setBounds(780, 20, 200, 50);
		tabela.setBounds(30, 90, 950, 570);

		carregarDadosTabela();
	}

	private void carregarDadosTabela() {
		List<Empresa> empresasAtualizadas = dao.listar();
		tabela.atualizarDados(empresasAtualizadas);
	}

	private void criarEventos() {
		jbAdicionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroEmpresa painelCadastro = new PainelCadastroEmpresa(telaPrincipal);
				if (telaPrincipal != null) {
					telaPrincipal.trocarPainel(painelCadastro);
				} else {
					JOptionPane.showMessageDialog(PainelListarEmpresa.this, "Erro: Tela principal não referenciada.");
				}
			}
		});

	}
}
