package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loggin extends JFrame {
    private JPanel ventana;
    private JPasswordField pass;
    private JTextField user;
    private JButton iniciarSesionButton;
    private JButton cerrarAppButton;
    private JPanel userpass;
    private JPanel button;
    private JPanel Imagen;


    public Loggin() {
        setContentPane(ventana);
        setTitle("loggin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,500);
        setLocationRelativeTo(null);
        setResizable(false);


        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });




        cerrarAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



    }




}
