package app;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Venda {
    private int idVenda;
    private Date data;
    private int idCliente;
    private List<DetalheVenda> itens;
    // Construtor
    public Venda(Date data, int idCliente) {
        this.idVenda = 0;
        this.data = data;
        this.idCliente = idCliente;
        this.itens = new ArrayList<>();
    }
    // Getters e Setters
    public int getIdVenda() {
        return idVenda;
    }
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public List<DetalheVenda> getItens() {
        return itens;
    }
    // Método para salvar venda no banco de dados
    public void salvarVenda() throws SQLException {
        String sqlVenda = "INSERT INTO venda (data, idcliente) VALUES (?, ?)";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, data);
            stmt.setInt(2, idCliente);
            stmt.executeUpdate();
            // Obter o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idVenda = rs.getInt(1);
                }
            }
        }
 /* Salvar itens da venda
 for (DetalheVenda item : itens) {
 item.salvarDetalhe(conn, idVenda);
 }*/
    }
    // Método para adicionar um item na venda
    public int adicionarItem(int idProduto, int quantidade, double precoVenda) throws SQLException {
        // Verificar se o estoque é suficiente
        String consultaEstoque = "SELECT estoque FROM produto WHERE idproduto = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(consultaEstoque)) {
            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int estoqueAtual = rs.getInt("estoque");
                    if (estoqueAtual < quantidade) {
                        throw new SQLException("Estoque insuficiente para o produto ID: " + idProduto);
                    }
                } else {
                    throw new SQLException("Produto com ID " + idProduto + " não encontrado.");
                }
            }
        }
        if (this.idVenda==0) salvarVenda();
        // Criar o detalhe da venda
        int idDetalhe = 0;
        String sql = "INSERT INTO detalhevenda (idvenda, idproduto, quantidade, precovenda) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.idVenda);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, quantidade);
            stmt.setDouble(4, precoVenda);
            stmt.executeUpdate();
            // Obter o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idDetalhe = rs.getInt(1);
                }
            }
        }
        DetalheVenda detalhe = new DetalheVenda(idDetalhe, idVenda, idProduto, quantidade, precoVenda);
        this.itens.add(detalhe);
        // Atualizar o estoque do produto
        String atualizarEstoque = "UPDATE produto SET estoque = estoque - ? WHERE idproduto = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(atualizarEstoque)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        }
        // Adicionar o item à lista de itens
        itens.add(detalhe);
        return idDetalhe;
    }

    public double calcularTotal() throws SQLException {
        double total = 0.0;
        String sql = "SELECT SUM(quantidade * precovenda) AS total FROM detalhevenda WHERE idvenda = ?";
        try (PreparedStatement stmt = Menu.conn.prepareStatement(sql)) {
            stmt.setInt(1, this.idVenda);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        }
        return total;
    }
}