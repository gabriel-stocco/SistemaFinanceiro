package com.sf.classes;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class TabelaModular<T> extends JPanel {
    private static final Color COR_CONTEUDO = new Color(180, 180, 180);
    private final List<T> objetos;
    private final LinkedHashMap<String, String> camposParaColunas;
    private final Consumer<T> acaoEditar;
    private final Consumer<T> acaoExcluir;
    private JScrollPane scrollPane;

    public TabelaModular(List<T> objetos, LinkedHashMap<String, String> camposParaColunas,
                         Consumer<T> acaoEditar, Consumer<T> acaoExcluir) {
        this.objetos = (objetos != null) ? new ArrayList<>(objetos) : new ArrayList<>();
        this.camposParaColunas = camposParaColunas;
        this.acaoEditar = acaoEditar;
        this.acaoExcluir = acaoExcluir;

        setLayout(new BorderLayout());
        setBackground(COR_CONTEUDO);

        String[] colunas;
        if (camposParaColunas == null || camposParaColunas.isEmpty()) {
            colunas = new String[]{"Ações"};
        } else {
            colunas = new String[camposParaColunas.size() + 1];
            int idx = 0;
            for (String nome : camposParaColunas.values()) {
                colunas[idx++] = nome;
            }
            colunas[idx] = "Ações";
        }


        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1;
            }
        };
        
        preencherModeloComObjetos(model, this.objetos);

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabela.setRowHeight(40);
        tabela.setFocusable(false);
        
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == table.getColumn("Ações").getModelIndex()) {
                    JPanel panel = criarPainelBotoesApenasVisual();
                    panel.setBackground(Color.WHITE);
                    return panel;
                } else {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(Color.WHITE);
                    return c;
                }
            }
        });

        if (tabela.getColumnCount() > 0 && tabela.getColumn("Ações") != null) {
            tabela.getColumn("Ações").setCellEditor(new AcoesCellEditor());
        }

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(new Color(13, 33, 79));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        this.scrollPane = new JScrollPane(tabela);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.scrollPane.setBackground(COR_CONTEUDO);
        this.scrollPane.getViewport().setBackground(COR_CONTEUDO);

        add(this.scrollPane, BorderLayout.CENTER);
    }

    private void preencherModeloComObjetos(DefaultTableModel model, List<T> objetosParaPreencher) {
        if (objetosParaPreencher == null || objetosParaPreencher.isEmpty()) {
            return;
        }

        for (T obj : objetosParaPreencher) {
            Object[] linha = new Object[this.camposParaColunas.size() + 1];
            int i = 0;

            for (String nomeCampo : this.camposParaColunas.keySet()) {
                try {
                    Field campo = obj.getClass().getDeclaredField(nomeCampo);
                    campo.setAccessible(true);
                    linha[i] = campo.get(obj);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    linha[i] = "Erro";
                    System.err.println("Erro ao acessar campo " + nomeCampo + " para objeto " + obj.getClass().getSimpleName() + ": " + e.getMessage());
                }
                i++;
            }
            linha[i] = "Ações";
            model.addRow(linha);
        }
    }

    public void atualizarDados(List<T> novosObjetos) {
        this.objetos.clear();
        if (novosObjetos != null) {
            this.objetos.addAll(novosObjetos);
        }

        if (this.scrollPane != null && this.scrollPane.getViewport() != null && this.scrollPane.getViewport().getView() instanceof JTable) {
            JTable table = (JTable) this.scrollPane.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            if (table.getCellEditor() != null) {
                table.getCellEditor().stopCellEditing();
            }

            model.setRowCount(0);

            preencherModeloComObjetos(model, this.objetos);

            model.fireTableDataChanged();
        } else {
            System.err.println("Erro: JScrollPane ou JTable não inicializados corretamente em TabelaModular para atualizarDados.");
        }
    }

    private JPanel criarPainelBotoesApenasVisual() {
        JButton jbEditar = new JButton("Editar");
        JButton jbExcluir = new JButton("Excluir");

        jbEditar.setFocusPainted(false);
        jbExcluir.setFocusPainted(false);

        jbEditar.setBackground(new Color(14, 122, 13));
        jbExcluir.setBackground(new Color(153, 0, 0));

        jbEditar.setForeground(Color.WHITE);
        jbExcluir.setForeground(Color.WHITE);
        
        jbEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setOpaque(true);
        
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoes.setOpaque(false);
        botoes.add(jbEditar);
        botoes.add(jbExcluir);
        painel.add(botoes);
        return painel;
    }

    class AcoesCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel editorComponent;
        private int currentRow;
        private JTable currentTable;

        public AcoesCellEditor() {
            editorComponent = new JPanel(new GridBagLayout());
            editorComponent.setOpaque(true);

            JButton jbEditar = new JButton("Editar");
            JButton jbExcluir = new JButton("Excluir");

            jbEditar.setFocusPainted(false);
            jbExcluir.setFocusPainted(false);

            jbEditar.setBackground(new Color(14, 122, 13));
            jbExcluir.setBackground(new Color(153, 0, 0));

            jbEditar.setForeground(Color.WHITE);
            jbExcluir.setForeground(Color.WHITE);
            
            jbEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jbExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));

            jbEditar.addActionListener((ActionEvent e) -> {
                T objeto = TabelaModular.this.objetos.get(currentRow);
                acaoEditar.accept(objeto);
                fireEditingStopped();
            });

            jbExcluir.addActionListener((ActionEvent e) -> {
                T objeto = TabelaModular.this.objetos.get(currentRow);
                acaoExcluir.accept(objeto);
                fireEditingStopped();
            });

            JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            botoes.setOpaque(false);
            botoes.add(jbEditar);
            botoes.add(jbExcluir);
            editorComponent.add(botoes);
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.currentRow = row;
            this.currentTable = table;
            
            editorComponent.setBackground(Color.WHITE);
            return editorComponent;
        }
    }
}