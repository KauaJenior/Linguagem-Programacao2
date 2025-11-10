import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class Calculator {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 350);
        frame.setLayout(new BorderLayout());

        
        JTextField display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(display, BorderLayout.NORTH);

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 1, 1));

        
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String command = e.getActionCommand();

                        if (command.equals("C")) {
                            
                            display.setText("0");

                        } else if (command.equals("=")) {
                                try {
                                    ScriptEngineManager mgr = new ScriptEngineManager();
                                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                                    Object result = engine.eval(display.getText());
                                    display.setText(String.valueOf(result));
                                } catch (ScriptException ex) {
                                    JOptionPane.showMessageDialog(frame, "Erro de cálculo: " + ex.getMessage());
                                    display.setText("0");
                                }
                            } else {
                            
                            if (display.getText().equals("0")) {
                                display.setText(command);
                            } else {
                                display.setText(display.getText() + command);
                            }
                        }
                    } 
                    catch (Exception ex) {
                        
                        JOptionPane.showMessageDialog(frame,
                            "Ocorreu um erro: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                        display.setText("0"); 
                    } 
                    finally {
                        display.requestFocus();
                    }
                }
            });
        }

       
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
      
    }
}
