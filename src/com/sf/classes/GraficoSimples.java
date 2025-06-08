package com.sf.classes;

import javax.swing.JPanel;

import com.sf.model.DadosGrafico;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GraficoSimples extends JPanel {

    private List<DadosGrafico> dados = new ArrayList<>();
    private List<Color> coresAleatorias = new ArrayList<>();
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

    public GraficoSimples() {
        setBackground(new Color(240, 240, 240));
    }

    public void setDados(List<DadosGrafico> dados) {
        this.dados = dados;
        gerarCoresAleatorias();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (dados == null || dados.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double total = dados.stream().mapToDouble(DadosGrafico::getValor).sum();

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 100;
        int x = 40;
        int y = 40;

        double anguloInicio = 0;

        for (int i = 0; i < dados.size(); i++) {
            DadosGrafico dado = dados.get(i);
            double angulo = dado.getValor() * 360 / total;

            g2d.setColor(cores[i % cores.length]);
            g2d.fillArc(x, y, diameter, diameter, (int) anguloInicio, (int) Math.round(angulo));
            anguloInicio += angulo;
        }

        // Legenda
        int espacoLegenda = 35;
        int totalLegendaAltura = dados.size() * espacoLegenda;
        int legendaY = y + (diameter - totalLegendaAltura) / 2;
        int legendaX = x + diameter + 40;

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < dados.size(); i++) {
            DadosGrafico dado = dados.get(i);
            g2d.setColor(cores[i % cores.length]);
            g2d.fillRect(legendaX, legendaY + i * espacoLegenda, 15, 15);
            g2d.setColor(Color.BLACK);
            String label = dado.getCategoria() + " (R$ " + df.format(dado.getValor()) + ")";
            g2d.drawString(label, legendaX + 20, legendaY + 12 + i * espacoLegenda);
        }
    }
    
    private void gerarCoresAleatorias() {
        coresAleatorias.clear();
        for (int i = 0; i < dados.size(); i++) {
            Color cor = new Color(
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
            );
            coresAleatorias.add(cor);
        }
    }
}