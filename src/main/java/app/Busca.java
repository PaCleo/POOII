package app;
public class Busca {
    private int codigo;
    private String nome;
    // Construtor
    public Busca(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
    // Métodos Getters e Setters
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    // Método para representação em texto
    @Override
    public String toString() {
        return "Busca{" + "codigo=" + codigo + ", nome='" + nome + '\'' + '}';
    }
}