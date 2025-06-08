package com.sf.paineis;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sf.classes.GraficoBarras;
import com.sf.dao.MovimentacaoBancariaDAO;
import com.sf.model.DadosGrafico;
import com.sf.telas.TelaPrincipal;

@SuppressWarnings("serial")
public class PainelRelatorio extends JPanel {
    private JLabel jlTitulo;
    private GraficoBarras grafico;
    private List<DadosGrafico> dados = new ArrayList<>();
    private MovimentacaoBancariaDAO dao = new MovimentacaoBancariaDAO();

    public PainelRelatorio() {
        super(new BorderLayout());
        setBackground(TelaPrincipal.COR_CONTEUDO);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        // ----- TÍTULO NO TOPO -----
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setOpaque(false);
        painelTitulo.setBorder(new EmptyBorder(20, 30, 0, 0));
        jlTitulo = new JLabel("RELATÓRIO - VALOR DAS CLASSIFICAÇÕES");
        jlTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        jlTitulo.setForeground(Color.WHITE);
        painelTitulo.add(jlTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        // ----- DADOS E GRÁFICO -----
        dados = dao.buscarDadosGrafico();
        grafico = new GraficoBarras();
        grafico.setDados(dados);

        // Painel central para o gráfico
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setOpaque(false);
        painelCentral.add(grafico);

        add(painelCentral, BorderLayout.CENTER);
    }
}
