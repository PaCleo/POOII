package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres"; // substitua pelo seu usuário do MySQL
    private static final String PASSWORD = "password"; // substitua pela sua senha do MySQL
    public static Connection connect() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            JOptionPane.showMessageDialog(null, "Conexão com base de dados bem-sucedida!", "Status da Conexão",
                    JOptionPane.INFORMATION_MESSAGE);
            return connection;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão com base de dados: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}