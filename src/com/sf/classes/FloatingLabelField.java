package com.sf.classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
	final int LABEL_Y_NORMAL = 25;
	final int LABEL_Y_FLOAT = 5;
	private boolean required;

	/**
	 * @param texto Texto do label
	 * @param width Largura do campo
	 * @param mask  Caso o tipo seja MASK, define a máscara (ex:"###.###.###-##")
	 * @param required Determina se o campo é obrigatório ou não
	 */
	public FloatingLabelField(String texto, int width, String mask, boolean required) {
		this.required = required;
		
		setLayout(null);
		setBackground(COR_CONTEUDO);
		setPreferredSize(new Dimension(300, 60));

		label = new JLabel(formatarTextoLabel(texto));
		label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		label.setForeground(Color.WHITE);
		label.setBounds(0, 5, width, 30);
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
	}

	/**
	 * Método responsavél por criar um campo com mascara se necessário
	 * @param mask - mascára a ser inserida no campo
	 * @return - o campo com a mascára adicionada
	 */
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
	
	/**
	 * Adiciona um asteristico vermelho se o campo for obrigatório
	 * @param texto - texto a ser adicionado o asteristico
	 * @return - texto com o asteristico
	 */
	private String formatarTextoLabel(String texto) {
		if (required) {
			return "<html>" + texto + " <font color='#cc0000'>*</font></html>";
		}
		return texto;
	}

	/**
	 * Método para pegar o valor do campo
	 * @return - o valor do campo em String
	 */
	public String getText() {
		return field.getText();
	}

	/**
	 * Método que coloca algum valor dentro do campo
	 * @param texto - texto a ser colocado no campo
	 */
	public void setText(String texto) {
		field.setText(texto);
	}

	/**
	 * Verifica se o campo está vazio
	 * @return - true se estiver vazio e false se não estiver
	 */
	public boolean isEmpty() {
		return getText().replaceAll("[^\\d]", "").isEmpty();
	}
}
