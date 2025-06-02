package com.sf.classes;

import java.awt.*;
import java.util.List;
import java.util.function.Function;
import javax.swing.*;

@SuppressWarnings("serial")
public class FloatingLabelComboBox<T> extends JPanel {
	private static final Color COR_CONTEUDO = new Color(180, 180, 180);
	private final JLabel label;
	private final JComboBox<ItemWrapper<T>> comboBox;

	public FloatingLabelComboBox(String texto, int width) {
		setLayout(null);
		setBackground(COR_CONTEUDO);
		setPreferredSize(null);

		label = new JLabel(texto);
		label.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		label.setForeground(Color.WHITE);
		label.setBounds(0, 5, width, 30);
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));

		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(COR_CONTEUDO);
		comboBox.setBounds(0, 30, width, 40);
		comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
		comboBox.setOpaque(false);

		comboBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());

		add(label);
		add(comboBox);
		setComponentZOrder(label, 0);
	}

	public void setOptions(List<T> options, Function<T, String> labelMapper, Function<T, ?> valueMapper) {
		comboBox.removeAllItems();

		comboBox.addItem(new ItemWrapper<>(null, "", ""));

		for (T option : options) {
			comboBox.addItem(new ItemWrapper<>(option, labelMapper.apply(option), valueMapper.apply(option)));
		}
	}

	public T getSelectedItem() {
		@SuppressWarnings("unchecked")
		ItemWrapper<T> selected = (ItemWrapper<T>) comboBox.getSelectedItem();
		return selected != null ? selected.getItem() : null;
	}

	public Object getSelectedValue() {
		@SuppressWarnings("unchecked")
		ItemWrapper<T> selected = (ItemWrapper<T>) comboBox.getSelectedItem();
		return selected != null ? selected.getValue() : null;
	}

	public boolean isEmpty() {
		return getSelectedItem() == null;
	}

	public void setSelectedItem(T item) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			ItemWrapper<T> wrapper = comboBox.getItemAt(i);
			if (wrapper.getItem() != null && wrapper.getItem().equals(item)) {
				comboBox.setSelectedItem(wrapper);
				return;
			}
		}
		comboBox.setSelectedIndex(0);
	}

	// Classe interna para cada item ter label e value
	private static class ItemWrapper<T> {
		private final T item;
		private final String label;
		private final Object value;

		public ItemWrapper(T item, String label, Object value) {
			this.item = item;
			this.label = label;
			this.value = value;
		}

		public T getItem() {
			return item;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
