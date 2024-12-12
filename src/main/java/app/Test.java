package app;
import java.sql.Connection;
import java.sql.SQLException;
public class Test {
    public static void main(String[] args) {
        try {
// Tenta estabelecer a conexão com o banco de dados
            Connection connection = DatabaseConnection.connect();
            if (connection != null) {
                System.out.println("Conexão bem-sucedida com o banco de dados!");
// Fecha a conexão após o teste
                connection.close();
            } else {
                System.out.println("Falha ao conectar com o banco de dados.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados:");
            e.printStackTrace();
        }
    }
}