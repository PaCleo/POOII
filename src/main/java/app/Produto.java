package app;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Produto implements Cadastro {
    private int idProduto;
    private String nome;
    private double precoCusto;
    private double precoVenda;
    private int estoque;
    public Produto() {
        super();
        this.idProduto = 0;
        this.nome = "";
        this.precoCusto = 0;
        this.precoVenda = 0;
        this.estoque = 0;
    }
    public Produto(int idProduto, String nome, double precoCusto, double precoVenda, int estoque) {
        super();
        this.idProduto = idProduto;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.estoque = estoque;
    }
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getPrecoCusto() {
        return precoCusto;
    }
    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }
    public double getPrecoVenda() {
        return precoVenda;
    }
    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    // Método para adicionar um produto ao banco de dados
    @Override
    public void adicionar() {
        String sql = "INSERT INTO produto (nome, precocusto, precovenda, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setDouble(2, this.precoCusto);
            stmt.setDouble(3, this.precoVenda);
            stmt.setInt(4, this.estoque);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para listar todos os produtos
    public static List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(rs.getInt("idproduto"), rs.getString("nome"),
                        rs.getDouble("precocusto"),
                        rs.getDouble("precovenda"), rs.getInt("estoque"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return produtos;
    }

    // Método para atualizar um produto no banco de dados
    @Override
    public void atualizar() {
        String sql = "UPDATE produto SET nome = ?, precocusto = ?, precovenda = ?, estoque = ? WHERE idproduto = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setDouble(2, this.precoCusto);
            stmt.setDouble(3, this.precoVenda);
            stmt.setInt(4, this.estoque);
            stmt.setInt(5, this.idProduto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void excluir() {
        String sql = "DELETE FROM produto WHERE idproduto = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idProduto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o produto: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para buscar um produto pelo ID
    public static Produto getByID(int idProduto) {
        String sql = "SELECT * FROM produto WHERE idproduto = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(rs.getInt("idproduto"), rs.getString("nome"),
                            rs.getDouble("precocusto"),
                            rs.getDouble("precovenda"), rs.getInt("estoque"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto por ID: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // Método para buscar produtos pelo nome
    @Override
    public List<Busca> buscar(String nome) {
        List<Busca> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY nome";
        PreparedStatement stmt = null;
        try {
            if (nome != null && !nome.isEmpty()) {
                sql = "SELECT * FROM produto WHERE nome LIKE ?";
                stmt = Menu.conn.prepareStatement(sql);
                stmt.setString(1, "%" + nome + "%");
            } else {
                stmt = Menu.conn.prepareStatement(sql);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Busca b = new Busca(rs.getInt("idproduto"), rs.getString("nome"));
                    produtos.add(b);
                }

                if (produtos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o nome: " + nome,
                            "Informação",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar produto por nome: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return produtos;
    }
}