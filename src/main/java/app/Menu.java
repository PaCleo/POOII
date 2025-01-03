package app;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Menu extends JFrame {

    public static Connection conn; // Conexão estática acessível em toda a aplicação

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Menu frame = new Menu();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar a janela ao iniciar
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Menu() {
        // Configuração inicial do JFrame
        setTitle("Sistema de Gerenciamento de Vendas");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 600); // Define um tamanho inicial

        // Configuração da conexão com o banco de dados
        try {
            conn = DatabaseConnection.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Configuração do layout
        getContentPane().setLayout(null);

        // Criando a barra de menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu Arquivo
        JMenu mnArquivo = new JMenu("Arquivo");
        menuBar.add(mnArquivo);

        JMenuItem mntmSair = new JMenuItem("Sair");
        mnArquivo.add(mntmSair);

        // Evento do botão "Sair"
        mntmSair.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Deseja sair do programa?", "Confirmação",
                    JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        // Menu Cadastros
        JMenu mnCadastros = new JMenu("Cadastros");
        menuBar.add(mnCadastros);

        JMenuItem mntmCliente = new JMenuItem("Cliente");
        mntmCliente.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
                FCliente cadastroCliente = new FCliente();
                cadastroCliente.setVisible(true);
            }
        });
        mnCadastros.add(mntmCliente);

        JMenuItem mntmProduto = new JMenuItem("Produto");
        mntmProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FProduto fProd = new FProduto();
                fProd.setVisible(true);
            }
        });
        mnCadastros.add(mntmProduto);

        // Menu Operações
        JMenu mnOperacoes = new JMenu("Operações");
        menuBar.add(mnOperacoes);

        JMenuItem mntmVenda = new JMenuItem("Venda");
        mntmVenda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FVenda fv = new FVenda();
                fv.setVisible(true);
            }
        });
        mnOperacoes.add(mntmVenda);

        // Evento para o botão "X" de fechar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(Menu.this, "Deseja sair do programa?", "Confirmação",
                        JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
