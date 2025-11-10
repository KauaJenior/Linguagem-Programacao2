import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class PessoaGUI {
    public static void main(String[] args) {
        Locale brasil = new Locale("pt", "BR");

        JFrame frame = new JFrame("TP02 - LP02");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,180);

        
            JPanel panelSuperior = new JPanel(new GridLayout(4,2,10,10));
        
           

            JTextField txtnumero = new JTextField();
            JTextField txtnome = new JTextField();
            String n = txtnome.getText();
            JTextField txtidade = new JTextField();
            String i_ = txtidade.getText();
            int i = Integer.parseInt(i_);
            JTextField txtsexo = new JTextField();
            String s = txtsexo.getText();

            panelSuperior.add(new JLabel("Número:"));
            panelSuperior.add(txtnumero);
            panelSuperior.add(new JLabel("Nome:"));
            panelSuperior.add(txtnome);
            panelSuperior.add(new JLabel("Sexo:"));
            panelSuperior.add(txtsexo);
            panelSuperior.add(new JLabel("Idade:"));
            panelSuperior.add(txtidade);

            JPanel panelInferior = new JPanel(new GridLayout(1,4));
            JButton bOk = new JButton("Ok");
            JButton bLimpar = new JButton("Limpar");
            JButton bMostrar = new JButton("Mostrar");
            JButton bSair = new JButton("Sair");

            panelInferior.add(bOk);
            panelInferior.add(bLimpar);
            panelInferior.add(bMostrar);
            panelInferior.add(bSair);

            frame.add(panelSuperior, BorderLayout.NORTH);
            frame.add(panelInferior, BorderLayout.SOUTH);

            frame.setVisible(true);

            
    }
}
