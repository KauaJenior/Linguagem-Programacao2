import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaCadastroPaciente extends JFrame {

    private JTextField txtNome, txtIdade, txtPeso, txtAltura, txtPesquisa;
    private JButton btnIncluir, btnLimpar, btnApresentar, btnPesquisar, btnCreditos, btnSair;

    private PessoaDAO dao = new PessoaDAO();

    public TelaCadastroPaciente() {
        setTitle("Cadastro de Pacientes");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        
        JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Informações do Paciente"));

        painelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        painelForm.add(txtIdade);

        painelForm.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        painelForm.add(txtPeso);

        painelForm.add(new JLabel("Altura (m):"));
        txtAltura = new JTextField();
        painelForm.add(txtAltura);

        add(painelForm, BorderLayout.NORTH);

        
        JPanel painelPesquisa = new JPanel(new GridLayout(1, 2, 5, 5));
        painelPesquisa.setBorder(BorderFactory.createTitledBorder("Pesquisar por Nome"));

        txtPesquisa = new JTextField();
        painelPesquisa.add(txtPesquisa);

        btnPesquisar = new JButton("Pesquisar");
        painelPesquisa.add(btnPesquisar);

        add(painelPesquisa, BorderLayout.CENTER);

        
        JPanel painelBtns = new JPanel(new FlowLayout());

        btnIncluir = new JButton("Incluir");
        btnLimpar = new JButton("Limpar");
        btnApresentar = new JButton("Apresentar Dados");
        btnCreditos = new JButton("Créditos");
        btnSair = new JButton("Sair");

        painelBtns.add(btnIncluir);
        painelBtns.add(btnLimpar);
        painelBtns.add(btnApresentar);
        painelBtns.add(btnCreditos);
        painelBtns.add(btnSair);

        add(painelBtns, BorderLayout.SOUTH);


        btnIncluir.addActionListener(e -> incluir());
        btnLimpar.addActionListener(e -> limparCampos());
        btnApresentar.addActionListener(e -> apresentarDados());
        btnPesquisar.addActionListener(e -> pesquisar());
        btnCreditos.addActionListener(e -> mostrarCreditos());
        btnSair.addActionListener(e -> System.exit(0));
    }


    private void incluir() {
        try {
            
            Pessoa p = new Pessoa(
                    txtNome.getText().trim(),
                    Integer.parseInt(txtIdade.getText().trim()),
                    Float.parseFloat(txtPeso.getText().trim()),
                    Float.parseFloat(txtAltura.getText().trim())
            );

            dao.inserir(p);

            JOptionPane.showMessageDialog(this, "Paciente incluído com sucesso!");
            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao incluir: verifique os dados.\n" + e.getMessage());
        }
    }

    private void apresentarDados() {
        try {
            ArrayList<Pessoa> lista = dao.listar();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum paciente cadastrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Pessoa p : lista) {
                sb.append("ID: ").append(p.getId())
                  .append(" | Nome: ").append(p.getNome())
                  .append(" | Idade: ").append(p.getIdade())
                  .append(" | Peso: ").append(p.getPeso())
                  .append(" | Altura: ").append(p.getAltura())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao apresentar dados: " + e.getMessage());
        }
    }

    private void pesquisar() {
        String nome = txtPesquisa.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para pesquisar!");
            return;
        }

        try {
            ArrayList<Pessoa> lista = dao.pesquisar(nome);

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum paciente encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Pessoa p : lista) {
                sb.append("ID: ").append(p.getId())
                  .append(" | Nome: ").append(p.getNome())
                  .append(" | Idade: ").append(p.getIdade())
                  .append(" | Peso: ").append(p.getPeso())
                  .append(" | Altura: ").append(p.getAltura())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro na pesquisa: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtPesquisa.setText("");
    }

    private void mostrarCreditos() {
        JOptionPane.showMessageDialog(this,
                "Desenvolvido pela dupla:\n\n• Seu Nome 1\n• Seu Nome 2");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroPaciente().setVisible(true));
    }
}
