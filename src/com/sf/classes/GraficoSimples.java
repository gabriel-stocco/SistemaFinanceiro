package com.sf.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraficoSimples extends JPanel {
	private int[] valores = {10, 20, 30, 40, 50};
    private String[] rotulos = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

    public GraficoSimples() {
        setBackground(new Color(200, 200, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int alturaMax = 50;
        int padding = 40;
        int baseY = getHeight() - padding;
        int esquerda = padding;
        int larguraTotal = getWidth() - 2 * padding;
        int alturaUtil = getHeight() - 2 * padding;

        int larguraBarra = larguraTotal / valores.length / 2;
        int espacamento = larguraTotal / valores.length;

        // Linhas horizontais (grade)
        g2d.setColor(new Color(220, 220, 220));
        for (int i = 0; i <= alturaMax; i += 10) {
            int y = baseY - (i * alturaUtil / alturaMax);
            g2d.drawLine(esquerda, y, esquerda + larguraTotal, y);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString(String.valueOf(i), esquerda - 25, y + 5);
            g2d.setColor(new Color(220, 220, 220));
        }
        
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Barras
        for (int i = 0; i < valores.length; i++) {
            int altura = valores[i] * alturaUtil / alturaMax;
            int x = esquerda + i * espacamento + espacamento / 4;
            int y = baseY - altura;

            g2d.setColor(new Color(102, 204, 255));
            g2d.fillRect(x, y, larguraBarra, altura);

            g2d.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int textoLargura = fm.stringWidth(rotulos[i]);
            g2d.drawString(rotulos[i], x + (larguraBarra - textoLargura) / 2, baseY + 15);
        }
    }
}
