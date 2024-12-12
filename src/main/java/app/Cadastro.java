package app;
import java.util.List;
public interface Cadastro {
    public void adicionar();
    public void atualizar();
    public void excluir();
    public List<Busca> buscar(String nome);

}