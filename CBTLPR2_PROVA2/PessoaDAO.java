import java.sql.*;
import java.util.ArrayList;

public class PessoaDAO {

    private final String url = "jdbc:sqlite:pacientes.db";

    public PessoaDAO() {
        criarTabela();
    }

    private void criarTabela() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS pacientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "idade INTEGER NOT NULL," +
                    "peso REAL NOT NULL," +
                    "altura REAL NOT NULL)");
        } catch (Exception ex) {
            System.out.println("Erro ao criar tabela: " + ex.getMessage());
        }
    }

    public void inserir(Pessoa p) throws Exception {
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO pacientes(nome, idade, peso, altura) VALUES (?, ?, ?, ?)");

        ps.setString(1, p.getNome());
        ps.setInt(2, p.getIdade());
        ps.setFloat(3, p.getPeso());
        ps.setFloat(4, p.getAltura());

        ps.execute();
        conn.close();
    }

    public ArrayList<Pessoa> listar() throws Exception {
        ArrayList<Pessoa> lista = new ArrayList<>();

        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pacientes");

        while (rs.next()) {
            lista.add(new Pessoa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getFloat("peso"),
                    rs.getFloat("altura")
            ));
        }

        conn.close();
        return lista;
    }

    public ArrayList<Pessoa> pesquisar(String nome) throws Exception {
        ArrayList<Pessoa> lista = new ArrayList<>();

        Connection conn = DriverManager.getConnection(url);
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM pacientes WHERE nome LIKE ?");
        ps.setString(1, "%" + nome + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new Pessoa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getFloat("peso"),
                    rs.getFloat("altura")
            ));
        }

        conn.close();
        return lista;
    }
}
