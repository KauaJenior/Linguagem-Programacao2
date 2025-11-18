import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Método para estabelecer a conexão com o banco de dados SQLite
    public static Connection getConnection() throws SQLException {
        try {
            // URL do banco de dados SQLite (se não existir, será criado automaticamente)
            String url = "jdbc:sqlite:aulajava.db";  // Caminho do banco de dados
            return DriverManager.getConnection(url);  // Estabelece a conexão
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            throw e;  // Repassa a exceção
        }
    }
}
