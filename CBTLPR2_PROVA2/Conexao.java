import java.sql.*;

public class Conexao {
    public static Connection conectar() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:pacientes.db");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
