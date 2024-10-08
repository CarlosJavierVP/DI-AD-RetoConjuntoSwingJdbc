package views;

import com.mysql.cj.jdbc.JdbcConnection;
import dao.CopiaDAO;
import dao.UsuarioDAO;
import models.Copia;
import models.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class Loggin extends JFrame {
    private JPanel ventana;
    private JPasswordField pass;
    private JTextField user;
    private JButton iniciarSesionButton;
    private JButton cerrarAppButton;
    private JPanel userpass;
    private JPanel button;
    private JPanel Imagen;

    Connection c = getCon();
    UsuarioDAO usuarios = new UsuarioDAO(c);
    CopiaDAO copias = new CopiaDAO(c);

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
                Usuario userConectado = usuarios.DataCon(user.getText(),pass.getPassword());
                //método DAO -> llamar a copia del usuario
                Copia misCopias = (Copia) copias.findUser(userConectado);

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
