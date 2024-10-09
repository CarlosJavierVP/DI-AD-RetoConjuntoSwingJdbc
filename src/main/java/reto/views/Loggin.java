package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
import reto.dao.UsuarioDAO;
import reto.models.Copia;
import reto.models.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                UsuarioDAO userConectado = new UsuarioDAO(JdbcUtils.con);
                //Usuario usuario = userConectado.DataCon(user.getText(), pass.getPassword());
                //CopiaDAO misCopias = new CopiaDAO(JdbcUtils.con);
                //Copia miCopia = (Copia) misCopias.findUser(usuario);


                List<Usuario> u = userConectado.findAll();


                Principal miLista = new Principal();
                miLista.setVisible(true);
                dispose();

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
