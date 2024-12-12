package app;
public class DetalheVenda {
    int iddetalhe;
    int idvenda;
    int idproduto;
    int quantidade;
    double precoVenda;
    /*
     * ALTER TABLE `detalhevenda` ADD `precovenda` DECIMAL(10,2) NOT NULL AFTER `quantidade`;
     */
    public DetalheVenda(int iddetalhe, int idvenda, int idproduto, int quantidade, double precoVenda) {
        super();
        this.iddetalhe = iddetalhe;
        this.idvenda = idvenda;
        this.idproduto = idproduto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }
    public int getIddetalhe() {
        return iddetalhe;
    }
    public void setIddetalhe(int iddetalhe) {
        this.iddetalhe = iddetalhe;
    }
    public int getIdvenda() {
        return idvenda;
    }
    public void setIdvenda(int idvenda) {
        this.idvenda = idvenda;
    }
    public int getIdproduto() {
        return idproduto;
    }
    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getPrecoVenda() {
        return precoVenda;
    }
    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
}