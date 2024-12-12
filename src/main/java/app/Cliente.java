package app;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int idcliente;
    private String nome;
    private String cpf;
    private String fone;

    // Construtor
    public Cliente(int idcliente, String nome, String cpf, String fone) {
        this.idcliente = idcliente;
        this.nome = nome;
        this.cpf = cpf;
        this.fone = fone;
    }

    public static List<Cliente> buscarPorNome(String nome) {
        String sql = "SELECT idcliente, nome, cpf, fone FROM cliente WHERE nome LIKE ?";
        List<Cliente> clientes = new ArrayList<>();

        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");  // O "%" no início e no final permite a busca parcial
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(rs.getInt("idcliente"), rs.getString("nome"),
                            rs.getString("cpf"), rs.getString("fone"));
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes por nome: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }

    public static Cliente getByID(int valor) {
        String sql = "SELECT idcliente, nome, cpf, fone FROM cliente WHERE idcliente = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setInt(1, valor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(rs.getInt("idcliente"), rs.getString("nome"), rs.getString("cpf"),
                            rs.getString("fone"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente por ID: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // Getters e Setters
    public int getidcliente() {
        return idcliente;
    }

    public void setidcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getfone() {
        return fone;
    }

    public void setfone(String fone) {
        this.fone = fone;
    }

    public static List<Cliente> getTodos() {
        String sql = "SELECT idcliente, nome, cpf, fone FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("idcliente"), rs.getString("nome"),
                        rs.getString("cpf"), rs.getString("fone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }

}
