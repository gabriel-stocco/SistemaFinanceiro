package com.sf.classes;

import javax.swing.JPanel;
import com.sf.model.DadosGrafico;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GraficoBarras extends JPanel {

    private List<DadosGrafico> dados = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#,##0.00");

    private final Color[] cores = {
        new Color(255, 99, 132),
        new Color(54, 162, 235),
        new Color(255, 206, 86),
        new Color(75, 192, 192),
        new Color(153, 102, 255),
        new Color(255, 159, 64),
        new Color(100, 200, 100),
        new Color(200, 100, 200)
    };

    public GraficoBarras() {
        setBackground(new Color(210, 210, 210));
    }

    public void setDados(List<DadosGrafico> dados) {
        this.dados = dados;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (dados == null || dados.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double maxValor = dados.stream().mapToDouble(DadosGrafico::getValor).max().orElse(1);

        int largura = getWidth();
        int altura = getHeight();
        int margem = 60;
        int larguraGrafico = largura - 2 * margem;
        int alturaGrafico = altura - 2 * margem;
        int larguraBarra = larguraGrafico / dados.size();
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));

        for (int i = 0; i < dados.size(); i++) {
            DadosGrafico dado = dados.get(i);
            double valor = dado.getValor();
            int alturaBarra = (int) ((valor / maxValor) * alturaGrafico);
            int x = margem + i * larguraBarra;
            int y = altura - margem - alturaBarra;

            // Desenha barra
            g2d.setColor(cores[i % cores.length]);
            g2d.fillRect(x, y, larguraBarra - 10, alturaBarra);

            // Desenha valor acima da barra
            g2d.setColor(Color.BLACK);
            String valorFormatado = "R$ " + df.format(valor);
            int larguraTexto = g2d.getFontMetrics().stringWidth(valorFormatado);
            g2d.drawString(valorFormatado, x + (larguraBarra - 10 - larguraTexto) / 2, y - 5);

            // Desenha rótulo abaixo da barra
            String categoria = dado.getCategoria();
            int larguraCat = g2d.getFontMetrics().stringWidth(categoria);
            g2d.drawString(categoria, x + (larguraBarra - 10 - larguraCat) / 2, altura - margem + 15);
        }

        // Eixo Y
        g2d.setColor(Color.GRAY);
        g2d.drawLine(margem, altura - margem, largura - margem, altura - margem);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 450);
    }

}
