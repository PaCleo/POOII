package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class FConsultaCliente extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTable table;
    private int codigoSelecionado = 0;

    public FConsultaCliente() {
        setTitle("Consulta Cliente");
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

        // Carregar clientes ao abrir a janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                List<Cliente> clientes = Cliente.getTodos(); // Supondo que a classe Cliente tenha esse método
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);  // Limpar a tabela
                for (Cliente c : clientes) {
                    Object[] row = { c.getidcliente(), c.getNome() };
                    model.addRow(row);
                }
            }
        });

        // Evento de Pesquisar
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Cliente> clientes = Cliente.buscarPorNome(textField.getText()); // Supondo que a classe Cliente tenha esse método
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                for (Cliente c : clientes) {
                    Object[] row = { c.getidcliente(), c.getNome() };
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
                    dispose(); // Fecha a janela
                } else {
                    JOptionPane.showMessageDialog(FConsultaCliente.this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Evento de Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigoSelecionado = 0;
                dispose(); // Fecha a janela
            }
        });
    }

    public int getCodigoSelecionado() {
        return codigoSelecionado;
    }
}
