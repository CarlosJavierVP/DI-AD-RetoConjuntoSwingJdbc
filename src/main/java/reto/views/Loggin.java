package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
import reto.dao.PeliculaDAO;
import reto.dao.UsuarioDAO;
import reto.models.Copia;
import reto.models.Pelicula;
import reto.models.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;


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
                UsuarioDAO dao = new UsuarioDAO(JdbcUtils.getCon());
                Usuario u = dao.validateUser(user.getText(), Arrays.toString(pass.getPassword()));
                CopiaDAO daoCopia = new CopiaDAO(JdbcUtils.getCon());

                if (u != null){
                    Copia miCopia = (Copia) daoCopia.findUser(u);
                    Principal miLista = new Principal();
                    miLista.setVisible(true);
                    dispose();

                }


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
