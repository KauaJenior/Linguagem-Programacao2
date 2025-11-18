import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class RH {

    private static JTextField txtnome;
    private static JTextField txtSalario;
    private static JTextField cargotxt;
    private static ResultSet resultSet;
    private static int currentIndex = -1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TRABALHO PRATICO 04");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200);

        frame.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel labelNome = new JLabel("Nome:");
        panelSuperior.add(labelNome);

        JTextField textFieldNome = new JTextField(20); // Campo para digitar o nome
        panelSuperior.add(textFieldNome);

        JButton buttonPesquisar = new JButton("Pesquisar");
        panelSuperior.add(buttonPesquisar);

        frame.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(4,2,5,5));

        JLabel nomelabel = new JLabel("Nome:");
        panelInferior.add(nomelabel);

        // Inicializando os campos de texto para mostrar os resultados
        txtnome = new JTextField(20);
        panelInferior.add(txtnome);

        JLabel salariolabel = new JLabel("Salário:");
        panelInferior.add(salariolabel);

        txtSalario = new JTextField(20);
        panelInferior.add(txtSalario);

        JLabel cargolabel = new JLabel("Cargo:");
        panelInferior.add(cargolabel);

        cargotxt = new JTextField(20);
        panelInferior.add(cargotxt);

        JButton btnAnterior = new JButton("Anterior");
        panelInferior.add(btnAnterior);

        JButton btnProx = new JButton("Próximo");
        panelInferior.add(btnProx);

        frame.add(panelInferior, BorderLayout.CENTER);

        frame.setVisible(true);

        // Estabelecendo a conexão com o banco de dados
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Ação do botão "Pesquisar"
            buttonPesquisar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nomePesquisado = textFieldNome.getText().trim();  // Remove espaços extras

                    if (nomePesquisado.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Por favor, insira um nome para pesquisa.");
                        return;
                    }

                    System.out.println("Pesquisando por: " + nomePesquisado); // Para depuração

                    try {
                        // Consulta SQL para buscar o nome
                        String sql = "SELECT * FROM tbfuncs WHERE nome_func LIKE ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, "%" + nomePesquisado + "%");  // LIKE para busca parcial
                        resultSet = stmt.executeQuery();

                        // Se houver resultados, configurar o primeiro funcionário
                        if (resultSet.next()) {
                            currentIndex = 0;
                            updateUI();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Nenhum funcionário encontrado.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Ação do botão "Próximo"
            btnProx.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (resultSet != null && resultSet.next()) {
                            currentIndex++;
                            updateUI();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Ação do botão "Anterior"
            btnAnterior.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (resultSet != null && resultSet.previous()) {
                            currentIndex--;
                            updateUI();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
    }

    // Método para atualizar a interface com os dados do resultSet
    private static void updateUI() throws SQLException {
        if (resultSet != null) {
            // Atualize os campos com os dados do resultSet
            String nome = resultSet.getString("nome_func");
            double salario = resultSet.getDouble("sal_func");
            int cargoId = resultSet.getInt("cod_cargo");

            // Defina esses valores nos campos de texto
            txtnome.setText(nome);
            txtSalario.setText(String.valueOf(salario));
            cargotxt.setText(String.valueOf(cargoId)); // Aqui você pode fazer a tradução do cargo se necessário
        }
    }
}
