package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class FConsulta extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTable table;
    private int codigoSelecionado = 0;

    public FConsulta(Cadastro c) {
        setTitle("Consulta");
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        textField = new JTextField();
        textField.setBounds(10, 10, 300, 30);
        contentPane.add(textField);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(320, 10, 100, 30);
        contentPane.add(btnPesquisar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 560, 200);
        contentPane.add(scrollPane);

        table = new JTable(new DefaultTableModel(new Object[][] {}, new String[] { "Código", "Nome" }));
        scrollPane.setViewportView(table);

        JButton btnOK = new JButton("OK");
        btnOK.setBounds(380, 270, 80, 30);
        contentPane.add(btnOK);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(470, 270, 100, 30);
        contentPane.add(btnCancelar);

        // Evento de Window Activated
        addWindowListener(new WindowListener() {
            @Override
            public void windowActivated(WindowEvent e) {
                List<Busca> b = c.buscar("");
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                for (Busca item : b) {
                    Object[] row = { item.getCodigo(), item.getNome() };
                    model.addRow(row);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowOpened(WindowEvent e) {}
        });

        // Evento de Pesquisar
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Busca> b = c.buscar(textField.getText());
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                for (Busca item : b) {
                    Object[] row = { item.getCodigo(), item.getNome() };
                    model.addRow(row);
                }
            }
        });

        // Evento de OK
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    codigoSelecionado = (int) table.getValueAt(selectedRow, 0);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(FConsulta.this, "Selecione um item da tabela!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Evento de Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigoSelecionado = 0;
                dispose();
            }
        });
    }

    public int getCodigoSelecionado() {
        return codigoSelecionado;
    }
}
