package app;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.*;
import java.util.Date;
import java.sql.*;
import java.text.ParseException;

public class FVenda extends JFrame {
    private static final long serialVersionUID = 1L;
    private Venda v;

    private JTextField tfCodCli;
    private JTextField tfNomeCliente;
    private JFormattedTextField tfData;
    private JTextField tfCodProd;
    private JButton btPesquisarProduto;
    private JTextField tfNomeProduto;
    private JTextField tfPreco;
    private JTextField tfQuant;
    private JButton btAdicionarItem;
    private JScrollPane scrollPane;
    private JTable table;
    private JLabel lbTotal;
    private JButton btFinalizarVenda;

    // Declarar model como membro da classe
    private DefaultTableModel model;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FVenda frame = new FVenda();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FVenda() {
        // Configurações do JFrame
        setTitle("Venda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(null);

        // Inicializar model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] {"id", "Cod Prod", "Produto", "Quant", "Preço Subtotal"});

        // Campos de texto e botões
        JLabel lblCodCli = new JLabel("Código Cliente:");
        lblCodCli.setBounds(20, 20, 100, 25);
        getContentPane().add(lblCodCli);

        tfCodCli = new JTextField();
        tfCodCli.setBounds(120, 20, 150, 25);
        getContentPane().add(tfCodCli);

        JLabel lblNomeCliente = new JLabel("Nome Cliente:");
        lblNomeCliente.setBounds(20, 60, 100, 25);
        getContentPane().add(lblNomeCliente);

        tfNomeCliente = new JTextField();
        tfNomeCliente.setBounds(120, 60, 150, 25);
        tfNomeCliente.setEditable(false);
        getContentPane().add(tfNomeCliente);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(20, 100, 100, 25);
        getContentPane().add(lblData);

        tfData = new JFormattedTextField();
        tfData.setBounds(120, 100, 96, 20);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            tfData.setFormatterFactory(new DefaultFormatterFactory(dateMask));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getContentPane().add(tfData);

        JLabel lblCodProd = new JLabel("Código Produto:");
        lblCodProd.setBounds(20, 140, 100, 25);
        getContentPane().add(lblCodProd);

        tfCodProd = new JTextField();
        tfCodProd.setBounds(120, 140, 150, 25);
        getContentPane().add(tfCodProd);

        btPesquisarProduto = new JButton("Pesquisar Produto");
        btPesquisarProduto.setBounds(280, 140, 150, 25);
        getContentPane().add(btPesquisarProduto);

        JLabel lblNomeProduto = new JLabel("Produto:");
        lblNomeProduto.setBounds(20, 180, 100, 25);
        getContentPane().add(lblNomeProduto);

        tfNomeProduto = new JTextField();
        tfNomeProduto.setBounds(120, 180, 300, 25);
        tfNomeProduto.setEditable(false);
        getContentPane().add(tfNomeProduto);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 220, 100, 25);
        getContentPane().add(lblPreco);

        tfPreco = new JTextField();
        tfPreco.setBounds(120, 220, 150, 25);
        getContentPane().add(tfPreco);

        JLabel lblQuant = new JLabel("Quantidade:");
        lblQuant.setBounds(20, 260, 100, 25);
        getContentPane().add(lblQuant);

        tfQuant = new JTextField();
        tfQuant.setBounds(120, 260, 150, 25);
        getContentPane().add(tfQuant);

        btAdicionarItem = new JButton("Adicionar Item");
        btAdicionarItem.setBounds(20, 300, 150, 25);
        getContentPane().add(btAdicionarItem);

        lbTotal = new JLabel("Total: 0.00");
        lbTotal.setBounds(600, 400, 100, 25);
        getContentPane().add(lbTotal);

        btFinalizarVenda = new JButton("Finalizar Venda");
        btFinalizarVenda.setBounds(600, 440, 150, 25);
        getContentPane().add(btFinalizarVenda);

        // Tabela para exibir itens da venda
        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 340, 760, 50);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.setModel(model); // Usar model já declarado como campo
        scrollPane.setViewportView(table);

        // Eventos...

        // Ao entrar no campo de código do cliente
        tfCodCli.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                v = null;
                tfCodCli.setText("");
                tfNomeCliente.setText("");
                tfData.setText("");
                tfCodProd.setText("");
                tfNomeProduto.setText("");
                tfQuant.setText("");
                tfPreco.setText("");
                model.setRowCount(0); // Limpar a tabela
                lbTotal.setText("0.00");
            }
        });

        // Ao sair do campo de código do cliente
        tfCodCli.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                int valor = 0;
                try {
                    valor = Integer.parseInt(tfCodCli.getText());
                } catch (NumberFormatException err) {
                    valor = 0;
                }
                if (valor > 0) {
                    Cliente c = Cliente.getByID(valor);
                    if (c != null) {
                        tfNomeCliente.setText(c.getNome());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String dataAtual = sdf.format(new Date());
                        tfData.setText(dataAtual);
                        tfData.grabFocus();
                        tfData.selectAll();
                    } else {
                        tfCodCli.setText("");
                        tfNomeCliente.setText("");
                    }
                }
            }
        });

        JButton btPesquisarCliente;

        btPesquisarCliente = new JButton("Pesquisar Cliente");
        btPesquisarCliente.setBounds(280, 20, 150, 25);
        getContentPane().add(btPesquisarCliente);

        btPesquisarCliente.addActionListener(e -> {
            FConsultaCliente frame = new FConsultaCliente();  // Cria a janela de consulta de cliente
            frame.setVisible(true);  // Exibe a janela de consulta de cliente

            // Espera até que o cliente tenha sido selecionado e a janela seja fechada
            while (frame.isVisible()) {
                try {
                    Thread.sleep(100); // Pausa para evitar loop infinito
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            // Agora obtém o cliente selecionado
            int idClienteSelecionado = frame.getCodigoSelecionado();
            if (idClienteSelecionado > 0) {
                Cliente clienteSelecionado = Cliente.getByID(idClienteSelecionado); // Supondo que exista um método getByID
                if (clienteSelecionado != null) {
                    tfCodCli.setText(String.valueOf(clienteSelecionado.getidcliente()));
                    tfNomeCliente.setText(clienteSelecionado.getNome());
                }
            }
        });

        // Ao clicar no botão pesquisar produto
        btPesquisarProduto.addActionListener(e -> {
            FConsulta frame = new FConsulta(new Produto());
            frame.setVisible(true);
            tfCodProd.setText(String.valueOf(frame.getCodigoSelecionado()));
            int valor = 0;
            try {
                valor = Integer.parseInt(tfCodProd.getText());
            } catch (NumberFormatException err) {
                valor = 0;
            }
            if (valor > 0) {
                Produto p = Produto.getByID(valor);
                if (p != null) {
                    tfNomeProduto.setText(p.getNome());
                    tfPreco.setText(String.format("%.2f", p.getPrecoVenda()));
                    tfQuant.grabFocus();
                } else {
                    tfCodProd.setText("");
                    tfPreco.setText("");
                    tfNomeProduto.setText("");
                    tfQuant.setText("");
                }
            }
        });

        // Ao clicar no botão Adicionar Item
        btAdicionarItem.addActionListener(e -> {
            try {
                int CodProd = Integer.parseInt(tfCodProd.getText());
                int Quant = Integer.parseInt(tfQuant.getText());
                double Preco = Double.parseDouble(tfPreco.getText().replace(",", "."));

                // Se a venda ainda não foi iniciada, inicialize com o idCliente e a data atual
                if (v == null) {
                    int idCliente = Integer.parseInt(tfCodCli.getText()); // ou de onde você pegar o idCliente
                    Date data = new Date(); // ou outra forma de obter a data
                    java.sql.Date dataSql = new java.sql.Date(data.getTime());
                    v = new Venda(dataSql, idCliente);
                }

                // Agora, adiciona o item à venda
                int idDetalhe = v.adicionarItem(CodProd, Quant, Preco);

                // Adicionar a linha à tabela
                Object[] row = { String.valueOf(idDetalhe), String.valueOf(CodProd), tfNomeProduto.getText(),
                        String.valueOf(Quant), String.format("%.2f", Preco), String.format("%.2f", Quant * Preco) };
                model.addRow(row); // Atualiza a tabela

                // Atualizar o total da venda
                lbTotal.setText(String.format("%.2f", v.calcularTotal()));

                // Limpar campos e dar foco para o próximo código do produto
                tfCodProd.setText("");
                tfNomeProduto.setText("");
                tfQuant.setText("");
                tfPreco.setText("");
                tfCodProd.grabFocus();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira valores válidos para o código do produto, quantidade e preço.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar item: " + e1.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Ao clicar no botão Finalizar Venda
        btFinalizarVenda.addActionListener(e -> {
            tfCodCli.grabFocus();
        });
    }
}
