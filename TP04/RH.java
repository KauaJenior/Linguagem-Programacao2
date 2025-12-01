import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

class Funcionario {
    String nome;
    double salario;
    int cargo;

    Funcionario(String nome, double salario, int cargo) {
        this.nome = nome;
        this.salario = salario;
        this.cargo = cargo;
    }
}

public class RH {

    private static JTextField txtnome;
    private static JTextField txtSalario;
    private static JTextField cargotxt;

    private static ArrayList<Funcionario> lista = new ArrayList<>();
    private static int currentIndex = 0;

    public static void main(String[] args) {

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco!");
            return;
        }

        JFrame frame = new JFrame("TRABALHO PRATICO 04");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 220);
        frame.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel labelNome = new JLabel("Nome:");
        panelSuperior.add(labelNome);

        JTextField textFieldNome = new JTextField(20);
        panelSuperior.add(textFieldNome);

        JButton buttonPesquisar = new JButton("Pesquisar");
        panelSuperior.add(buttonPesquisar);

        frame.add(panelSuperior, BorderLayout.NORTH);

        // ---------------- CAMPOS ----------------
        JPanel panelInferior = new JPanel(new GridLayout(4, 2, 5, 5));

        panelInferior.add(new JLabel("Nome:"));
        txtnome = new JTextField(20);
        panelInferior.add(txtnome);

        panelInferior.add(new JLabel("Salário:"));
        txtSalario = new JTextField(20);
        panelInferior.add(txtSalario);

        panelInferior.add(new JLabel("Cargo:"));
        cargotxt = new JTextField(20);
        panelInferior.add(cargotxt);

        JButton btnAnterior = new JButton("Anterior");
        panelInferior.add(btnAnterior);

        JButton btnProximo = new JButton("Próximo");
        panelInferior.add(btnProximo);

        frame.add(panelInferior, BorderLayout.CENTER);

        // ---------------- PESQUISAR ----------------
        Connection finalConn = conn;
        buttonPesquisar.addActionListener(e -> {

            String nomePesq = textFieldNome.getText().trim();

            if (nomePesq.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um nome para pesquisar.");
                return;
            }

            lista.clear(); 

            try {
                String sql = "SELECT * FROM tbfuncs WHERE nome_func LIKE ?";
                PreparedStatement stmt = finalConn.prepareStatement(sql);
                stmt.setString(1, "%" + nomePesq + "%");

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    lista.add(new Funcionario(
                            rs.getString("nome_func"),
                            rs.getDouble("sal_func"),
                            rs.getInt("cod_cargo")
                    ));
                }

                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nenhum funcionário encontrado.");
                    return;
                }

                currentIndex = 0;
                atualizarCampos();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnProximo.addActionListener(e -> {
            if (!lista.isEmpty() && currentIndex < lista.size() - 1) {
                currentIndex++;
                atualizarCampos();
            }
        });

        btnAnterior.addActionListener(e -> {
            if (!lista.isEmpty() && currentIndex > 0) {
                currentIndex--;
                atualizarCampos();
            }
        });

        frame.setVisible(true);
    }

    private static void atualizarCampos() {
        Funcionario f = lista.get(currentIndex);
        txtnome.setText(f.nome);
        txtSalario.setText(String.valueOf(f.salario));
        cargotxt.setText(String.valueOf(f.cargo));
    }
}
