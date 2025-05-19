package com.sf.classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FloatingLabelField extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JTextField field;
    private JLabel label;

    public FloatingLabelField(String texto, int width) {
        setLayout(null);
        setBackground(COR_CONTEUDO);
        setPreferredSize(new Dimension(300, 60));

        label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        label.setForeground(Color.WHITE);
        label.setBounds(0, 25, width, 30);
        label.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        field.setForeground(Color.WHITE);
        field.setBackground(COR_CONTEUDO);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        field.setBounds(0, 30, width, 40);
        field.setOpaque(false);
        
        add(field);
        add(label);
        
        setComponentZOrder(label, 0);

        // Foco e animação básica
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
            	animateLabelUp(width);
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                	 animateLabelDown(width);
                }
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
            }
        });
    }
    
    private void animateLabelUp(int width) {
        Timer timer = new Timer(5, null);
        final int endY = 0;
        
        timer.addActionListener(e -> {
            int currentY = label.getY();
            if (currentY <= endY) {
                label.setBounds(0, endY, width, 30);
                timer.stop();
            } else {
                label.setBounds(0, currentY - 2, width, 30);
                repaint();
            }
        });
        timer.start();
    }

    private void animateLabelDown(int width) {
        Timer timer = new Timer(5, null);
        final int endY = 25;
        
        timer.addActionListener(e -> {
            int currentY = label.getY();
            if (currentY >= endY) {
                label.setBounds(0, endY, width, 30);
                timer.stop();
            } else {
                label.setBounds(0, currentY + 2, width, 30);
                repaint();
            }
        });
        timer.start();
    }

    public String getText() {
        return field.getText();
    }
    
    public void setText(String texto) {
    	field.setText(texto);
    }
}
