package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loggin extends JFrame {
    private JPanel ventana;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton iniciarSesionButton;
    private JButton cerrarAppButton;
    private JPanel userpass;
    private JPanel button;


    public Loggin() {
        setContentPane(ventana);
        setTitle("loggin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,400);
        setLocationRelativeTo(null);
        setResizable(false);


        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });


        cerrarAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeButton();
            }
        });


    }

    private void closeButton(){
        dispose();
    }





}
