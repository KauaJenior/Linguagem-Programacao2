import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

    public static void main(String[] args) {

        // URL para o banco de dados SQLite (será criado no mesmo diretório)
        String url = "jdbc:sqlite:aulajava.db"; 

        // Comando para criar a tabela tbcargos
        String sqlCreate1 = "CREATE TABLE IF NOT EXISTS tbcargos (" +
            "cd_cargo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +  // Ajustado para INTEGER e AUTOINCREMENT
            "ds_cargo VARCHAR(20) NOT NULL" +  // Usando VARCHAR para o campo de cargo
        ");";

        // Comando para criar a tabela tbfuncs
        String sqlCreate2 = "CREATE TABLE IF NOT EXISTS tbfuncs (" +
            "cod_func INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome_func VARCHAR(30) NULL," +  // Usando VARCHAR para o nome
            "sal_func REAL NULL," +  // Usando REAL para o salário (ao invés de MONEY)
            "cod_cargo INTEGER NULL," +
            "FOREIGN KEY (cod_cargo) REFERENCES tbcargos(cd_cargo)" +
        ");";

        // Comando para inserir cargos na tabela tbcargos
        String sqlInsertCargos = "INSERT INTO tbcargos (ds_cargo) VALUES " +
            "('Administrativo'), " +
            "('Financeiro'), " +
            "('RH'), " +
            "('TI'), " +
            "('Marketing'), " +
            "('Vendas');";

        // Comando para inserir funcionários na tabela tbfuncs
        String sqlInsertFunc = "INSERT INTO tbfuncs (nome_func, sal_func, cod_cargo) VALUES " +
            "('João Silva', 3500.00, 1), " +
            "('Maria Oliveira', 4500.00, 2), " +
            "('Carlos Souza', 5000.00, 3), " +
            "('Ana Pereira', 3800.00, 4), " +
            "('Fernanda Costa', 4200.00, 5);";

        // Tentando conectar e executar os comandos SQL
        try (Connection conn = DriverManager.getConnection(url)) {

            // Usando Statement para executar os comandos SQL
            try (Statement stmt = conn.createStatement()) {
                // Criando as tabelas
                stmt.executeUpdate(sqlCreate1);
                stmt.executeUpdate(sqlCreate2);

                // Inserindo dados nas tabelas
                stmt.executeUpdate(sqlInsertCargos);
                stmt.executeUpdate(sqlInsertFunc);

                System.out.println("Tabelas e dados inseridos com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao executar comandos SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}
