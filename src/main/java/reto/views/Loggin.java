package reto.views;

import reto.JdbcUtils;
import reto.Session;
import reto.dao.CopiaDAO;
import reto.dao.UsuarioDAO;
import reto.models.Copia;
import reto.models.Usuario;
import javax.swing.*;
import java.util.List;

import static reto.Session.paramsnotnull;

/**
 * Clase Loggin representa la ventana de conexión de un usuario a su cuenta
 * @author Carlos Javier
 * */
public class Loggin extends JFrame {
    /**Atributo JPanel de ventana*/
    private JPanel ventana;
    /**Atributo JPassworfField campo de la contraseña*/
    private JPasswordField pass;
    /**Atributo JTextField campo de usuario*/
    private JTextField user;
    /**Atributo JButton botón para iniciar sesión*/
    private JButton iniciarSesionButton;
    /**Atributo JButton botón para cerrar la apliación*/
    private JButton cerrarAppButton;
    /**Atributo JButton para registrar usuario nuevo*/
    private JButton addUser;


    /**
     * Método Loggin() para establecer la ventana y sus funcionalidades
     * */
    public Loggin() {
        setContentPane(ventana);
        setTitle("loggin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarSesionButton.addActionListener((e)->{
            sesionConectada();
        });

        cerrarAppButton.addActionListener((e) -> {
                paramsnotnull();
                dispose();
        });

        addUser.addActionListener((e)->{
            paramsnotnull();
            AddUser nuevoUsuario = new AddUser();
            nuevoUsuario.setVisible(true);
            dispose();
        });

    }

    /**
     * Método sesionConectada() para establecer la conexión con la base de datos e identificando al usuario
     * */
    private void sesionConectada() {
        UsuarioDAO daoUser = new UsuarioDAO(JdbcUtils.getCon());
        CopiaDAO daoCopia = new CopiaDAO(JdbcUtils.getCon());

        Usuario u = daoUser.validateUser(user.getText(), pass.getPassword());

        if (u != null){
            List<Copia> miCopia = daoCopia.findByUser(u);
            u.setMicopia(miCopia);

            Session.copyDTO = miCopia;
            Session.userSelected = u;

            Principal miLista = new Principal();
            miLista.setVisible(true);
            dispose();

        }else{
            JOptionPane.showMessageDialog(this,"Error al ingresar cuenta");
            user.setText("");
            pass.setText("");
        }
    }

}
