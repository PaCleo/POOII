package app;

import javax.swing.*;
import java.awt.event.*;

public class FProduto extends JFrame {

    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JButton btnExcluir;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FProduto frame = new FProduto();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FProduto() {
        // Configurações do JFrame
        setTitle("Cadastro de Produto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(null);

        // Campos de texto e botões
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 25);
        getContentPane().add(lblCodigo);

        textField = new JTextField();
        textField.setBounds(100, 20, 150, 25);
        getContentPane().add(textField);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 80, 25);
        getContentPane().add(lblNome);

        textField_1 = new JTextField();
        textField_1.setBounds(100, 60, 300, 25);
        getContentPane().add(textField_1);

        JLabel lblPrecoVenda = new JLabel("Preço Venda:");
        lblPrecoVenda.setBounds(20, 100, 80, 25);
        getContentPane().add(lblPrecoVenda);

        textField_2 = new JTextField();
        textField_2.setBounds(100, 100, 150, 25);
        getContentPane().add(textField_2);

        JLabel lblPrecoCusto = new JLabel("Preço Custo:");
        lblPrecoCusto.setBounds(20, 140, 80, 25);
        getContentPane().add(lblPrecoCusto);

        textField_3 = new JTextField();
        textField_3.setBounds(100, 140, 150, 25);
        getContentPane().add(textField_3);

        JLabel lblEstoque = new JLabel("Estoque:");
        lblEstoque.setBounds(20, 180, 80, 25);
        getContentPane().add(lblEstoque);

        textField_4 = new JTextField();
        textField_4.setBounds(100, 180, 150, 25);
        getContentPane().add(textField_4);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(100, 220, 80, 25);
        getContentPane().add(btnOk);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(190, 220, 100, 25);
        getContentPane().add(btnCancelar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(300, 220, 100, 25);
        btnExcluir.setEnabled(false);
        getContentPane().add(btnExcluir);

        // Eventos

        // Ao entrar no campo de código
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.selectAll();
                textField_1.setText("");
                textField_2.setText("");
                textField_3.setText("");
                textField_4.setText("");
                btnExcluir.setEnabled(false);
            }
        });

        // Ao sair do campo de código
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                int valor;
                try {
                    valor = Integer.parseInt(textField.getText());
                } catch (NumberFormatException err) {
                    valor = 0;
                }
                if (valor > 0) {
                    Produto p = Produto.getByID(valor);
                    if (p != null) {
                        textField_1.setText(p.getNome());
                        textField_2.setText(String.format("%.2f", p.getPrecoVenda()));
                        textField_3.setText(String.format("%.2f", p.getPrecoCusto()));
                        textField_4.setText(String.valueOf(p.getEstoque()));
                        btnExcluir.setEnabled(true);
                    } else {
                        textField.setText("");
                    }
                } else {
                    textField.setText("");
                }
            }
        });

        // Botão OK
        btnOk.addActionListener(e -> {
            int idProduto;
            double precoCusto;
            double precoVenda;
            int estoque;
            try {
                idProduto = Integer.parseInt(textField.getText());
            } catch (NumberFormatException err) {
                idProduto = 0;
            }
            try {
                precoVenda = Double.parseDouble(textField_2.getText());
            } catch (NumberFormatException err) {
                precoVenda = 0.0;
            }
            try {
                precoCusto = Double.parseDouble(textField_3.getText());
            } catch (NumberFormatException err) {
                precoCusto = 0.0;
            }
            try {
                estoque = Integer.parseInt(textField_4.getText());
            } catch (NumberFormatException err) {
                estoque = 0;
            }
            Produto p = null;
            if (idProduto > 0) {
                p = Produto.getByID(idProduto);
                if (p != null) {
                    p.setNome(textField_1.getText());
                    p.setPrecoVenda(precoVenda);
                    p.setPrecoCusto(precoCusto);
                    p.setEstoque(estoque);
                    p.atualizar(); // Atualiza
                    textField.grabFocus();
                }
            }
            if (textField_1.getText().length() > 0 && (idProduto == 0 || p == null)) {
                p = new Produto();
                p.setNome(textField_1.getText());
                p.setPrecoVenda(precoVenda);
                p.setPrecoCusto(precoCusto);
                p.setEstoque(estoque);
                p.adicionar(); // Adiciona
                textField.grabFocus();
            }
        });

        // Botão Cancelar
        btnCancelar.addActionListener(e -> {
            textField.setText("");
            textField.grabFocus();
        });

        // Botão Excluir
        btnExcluir.addActionListener(e -> {
            int idProduto;
            try {
                idProduto = Integer.parseInt(textField.getText());
            } catch (NumberFormatException err) {
                idProduto = 0;
            }
            if (idProduto > 0) {
                Produto p = Produto.getByID(idProduto);
                if (p != null) {
                    if (JOptionPane.showConfirmDialog(this, "Excluir Registro?", "Confirmação",
                            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        p.excluir();
                        textField.setText("");
                        textField.grabFocus();
                    }
                }
            }
        });

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(300, 20, 100, 25);
        getContentPane().add(btnPesquisar);

        // Eventos dos botões
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Criar a janela de consulta
                FConsulta frame = new FConsulta(new Produto());
                frame.setVisible(true); // Interrompe a execução e mostra o FConsulta

                // Após fechar a janela, pega o código selecionado
                int codigoSelecionado = frame.getCodigoSelecionado();

                // Exibir o código na textField
                textField.setText(String.valueOf(codigoSelecionado));

                // Se o código for válido, buscar o produto correspondente
                if (codigoSelecionado > 0) {
                    Produto p = Produto.getByID(codigoSelecionado);
                    if (p != null) {
                        // Preenche os campos com as informações do produto
                        textField_1.setText(p.getNome());
                        textField_2.setText(String.format("%.2f", p.getPrecoVenda()));
                        textField_3.setText(String.format("%.2f", p.getPrecoCusto()));
                        textField_4.setText(String.valueOf(p.getEstoque()));
                        btnExcluir.setEnabled(true);
                        textField_1.grabFocus();
                    } else {
                        textField.setText("");
                    }
                } else {
                    textField.setText("");
                }
            }
        });
    }
}
