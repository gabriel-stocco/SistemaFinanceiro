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
import com.sf.model.Fornecedor;
import com.sf.model.FornecedorDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelListarFornecedor extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private TabelaModular<Fornecedor> tabela;
	private JLabel jlTitulo;
	private JButton jbAdicionar;
	private FornecedorDAO dao = new FornecedorDAO();
	List<Fornecedor> fornecedor = new ArrayList<Fornecedor>();
	
	private TelaPrincipal telaPrincipal;
	
	public PainelListarFornecedor(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
		setLayout(null);
		setBackground(COR_CONTEUDO);
		iniciarComponentes();
		criarEventos();
	}

	private void iniciarComponentes() {
		// Título do Painel
		jlTitulo = new JLabel("LISTAGEM CLASSIFICAÇÕES");
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
		camposParaColunas.put("idFornecedor", "Código");
		camposParaColunas.put("cnjpForn", "CNPJ");
		camposParaColunas.put("enderecoForn", "Endereço");
		
		// DEFININDO AS AÇÕES DE EDITAR E EXCLUIR AQUI
        Consumer<Fornecedor> minhaAcaoEditar = (fornecedor) -> {
            JOptionPane.showMessageDialog(this, "Ação de EDITAR para: " + fornecedor.getCnjpForn() + " (ID: " + fornecedor.getIdFornecedor() + ")");
        };

        Consumer<Fornecedor> minhaAcaoExcluir = (fornecedor) -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir: " + fornecedor.getCnjpForn() + "?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            	String res =  dao.excluir(fornecedor.getIdFornecedor());
                JOptionPane.showMessageDialog(this, res); 
                
                if (res.contains("sucesso")) {
                    carregarDadosTabela();	
                }
            }
        };
		
		tabela = new TabelaModular<>(new ArrayList<>(), camposParaColunas, minhaAcaoEditar, minhaAcaoExcluir);
		
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
		 List<Fornecedor> fornecedoresAtualizados = dao.listar();
	     tabela.atualizarDados(fornecedoresAtualizados);
	}

	private void criarEventos() {
		jbAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCadastroFornecedor painelCadastro = new PainelCadastroFornecedor(telaPrincipal);
				if (telaPrincipal != null) {
					telaPrincipal.trocarPainel(painelCadastro);
				} else {
					JOptionPane.showMessageDialog(PainelListarFornecedor.this, "Erro: Tela principal não referenciada.");
				}
			}
		});
		
	}
}
