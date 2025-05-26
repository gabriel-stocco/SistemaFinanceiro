package com.sf.classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;


@SuppressWarnings("serial")
public class FloatingLabelField extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private JFormattedTextField field;
    private JLabel label;

    /**
     * @param texto Texto do label
     * @param width Largura do campo
     * @param fieldType Tipo de campo (TEXT, NUMERIC, MASK)
     * @param mask Caso o tipo seja MASK, define a máscara (ex: "###.###.###-##")
     */
    public FloatingLabelField(String texto, int width, String mask) {
        setLayout(null);
        setBackground(COR_CONTEUDO);
        setPreferredSize(new Dimension(300, 60));

        label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        label.setForeground(Color.WHITE);
        label.setBounds(0, 25, width, 30);
        label.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        field = createField(mask);
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
            	int currentY = label.getY();
            	label.setBounds(0, currentY - 20, width, 30);
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                	int currentY = label.getY();
                	label.setBounds(0, currentY + 20, width, 30);
                }
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
                repaint();
            }
        });
    }
    
    private JFormattedTextField createField(String mask) {
        try {
        	if (mask != null && !mask.isEmpty()) {
        		MaskFormatter maskFormatter = new MaskFormatter(mask);
                maskFormatter.setPlaceholderCharacter('_');
                return new JFormattedTextField(maskFormatter);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JFormattedTextField();
    }

    public String getText() {
        return field.getText();
    }
    
    public void setText(String texto) {
    	field.setText(texto);
    }
    
    public boolean isEmpty() {
        return getText().replaceAll("[^\\d]", "").isEmpty();
    }
}
