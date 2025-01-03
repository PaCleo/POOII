package app;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FCliente extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField tfNome;
    private JFormattedTextField tfCpf;
    private JTextField tffone;
    private JButton btSalvar;
    private JButton btLimpar;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FCliente frame = new FCliente();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FCliente() {
        // Configurações do JFrame
        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);

        // Campos de texto e botões
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 100, 25);
        getContentPane().add(lblNome);

        tfNome = new JTextField();
        tfNome.setBounds(120, 20, 250, 25);
        getContentPane().add(tfNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(20, 60, 100, 25);
        getContentPane().add(lblCpf);

        tfCpf = new JFormattedTextField();
        tfCpf.setBounds(120, 60, 150, 25);
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            tfCpf.setFormatterFactory(new DefaultFormatterFactory(cpfMask));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getContentPane().add(tfCpf);

        JLabel lblfone = new JLabel("fone:");
        lblfone.setBounds(20, 100, 100, 25);
        getContentPane().add(lblfone);

        tffone = new JTextField();
        tffone.setBounds(120, 100, 150, 25);
        getContentPane().add(tffone);

        btSalvar = new JButton("Salvar");
        btSalvar.setBounds(120, 150, 100, 30);
        getContentPane().add(btSalvar);

        btLimpar = new JButton("Limpar");
        btLimpar.setBounds(230, 150, 100, 30);
        getContentPane().add(btLimpar);

        // Eventos...

        // Ao clicar no botão Salvar
        btSalvar.addActionListener(e -> {
            String nome = tfNome.getText();
            String cpf = tfCpf.getText();
            String fone = tffone.getText();

            // Chamar o método para salvar o cliente
            salvarCliente(nome, cpf, fone);
        });

        // Ao clicar no botão Limpar
        btLimpar.addActionListener(e -> {
            tfNome.setText("");
            tfCpf.setText("");
            tffone.setText("");
        });
    }

    private void salvarCliente(String nome, String cpf, String fone) {
        String cpfSomenteNumeros = cpf.replaceAll("[^0-9]", "");

        if (cpfSomenteNumeros.length() != 11) {
            JOptionPane.showMessageDialog(this, "CPF deve conter 11 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO cliente (nome, cpf, fone) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpfSomenteNumeros);
            stmt.setString(3, fone);

            int resultado = stmt.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
