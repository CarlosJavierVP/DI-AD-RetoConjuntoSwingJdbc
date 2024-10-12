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
import java.util.ArrayList;
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
    private JLabel error;

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
                UsuarioDAO daoUser = new UsuarioDAO(JdbcUtils.getCon());
                CopiaDAO daoCopia = new CopiaDAO(JdbcUtils.getCon());

                Usuario u = daoUser.validateUser(user.getText(), pass.getPassword());

                if (u != null){
                    List<Copia> miCopia = daoCopia.findUser(u);
                    System.out.println(miCopia);

                    Principal miLista = new Principal(miCopia);

                    miLista.setVisible(true);
                    dispose();
                }else{
                    error.setText("Error al ingresar usuario");
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
