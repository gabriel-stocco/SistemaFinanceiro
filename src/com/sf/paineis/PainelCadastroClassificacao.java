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
import com.sf.model.Classificacao;
import com.sf.model.ClassificacaoDAO;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelCadastroClassificacao extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JLabel jlTitulo;
	private FloatingLabelField fieldCodigo, fieldNome;
	private JButton jbCadastrar, jbCancelar;
	private ClassificacaoDAO dao = new ClassificacaoDAO();
	private Classificacao classificacao;
	
	private TelaPrincipal telaPrincipal;
	
	public PainelCadastroClassificacao(TelaPrincipal telaPrincipal) {
		super();
		this.telaPrincipal = telaPrincipal;
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
		fieldCodigo = new FloatingLabelField("Código da Classificação", 430, null);
		fieldNome = new FloatingLabelField("Nome da Classificação", 430, null);

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
		add(fieldCodigo);
		add(fieldNome);
		add(jbCadastrar);
		add(jbCancelar);

		// Posicionamento
		jlTitulo.setBounds(30, 20, 500, 30);
		fieldCodigo.setBounds(30, 70, 430, 80);
		fieldNome.setBounds(520, 70, 430, 80);
		jbCadastrar.setBounds(800, 200, 150, 50);
		jbCancelar.setBounds(620, 200, 150, 50);
	}

	private void criarEventos() {
		jbCadastrar.addActionListener(new ActionListener() {
			String id, nome;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!fieldCodigo.getText().isEmpty() && !fieldNome.getText().isEmpty()) {
					id = fieldCodigo.getText();
					nome = fieldNome.getText();
					
					classificacao = new Classificacao(Integer.parseInt(id), nome);
					
					String res = dao.salvar(classificacao);
					
					JOptionPane.showMessageDialog(null, res, "Sistema Financeiro",
							JOptionPane.INFORMATION_MESSAGE);
					
					PainelListarClassificacao painelCadastro = new PainelListarClassificacao(telaPrincipal);
					if (telaPrincipal != null) {
						telaPrincipal.trocarPainel(painelCadastro);
					} else {
						JOptionPane.showMessageDialog(PainelCadastroClassificacao.this, "Erro: Tela principal não referenciada.");
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
				PainelListarClassificacao painelListagem = new PainelListarClassificacao(telaPrincipal);
				if (telaPrincipal != null) {
					telaPrincipal.trocarPainel(painelListagem);
				} else {
					JOptionPane.showMessageDialog(PainelCadastroClassificacao.this, "Erro: Tela principal não referenciada.");
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
