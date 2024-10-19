package reto.views;

import reto.JdbcUtils;
import reto.dao.UsuarioDAO;
import reto.models.Usuario;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static reto.Session.copyDTO;
import static reto.Session.userSelected;

/**
 * Clase AddUser para la ventana de registrar un usuario nuevo
 * @author Carlos Javier
 * */
public class AddUser extends JDialog {
    /**Atributo JPanel de la ventana de registrar usuario*/
    private JPanel panelNewUser;
    /**Atributo JTextField campo para ingresar nombre de nuevo usuario*/
    private JTextField newUser;
    /**Atributo JPasswordField campo para ingresar contraseña de nuevo usuario*/
    private JPasswordField newPass;
    /**Atributo JPasswordField campo para validar la contraseña de nuevo usuario*/
    private JPasswordField newPass2;
    /**Atributo JButton para guardar el nuevo usuario y logear con la cuenta*/
    private JButton btnGuardar;
    /**Atributo JButton para cancelar el registro de nuevo usuario*/
    private JButton btnCancelar;

    /**Atributo UsuarioDAO para establecer la conexión con JDBC*/
    UsuarioDAO userDao = new UsuarioDAO(JdbcUtils.getCon());

    /**
     * Método AddUser() para mostrar la ventana de registrar usuario y sus funcionalidades
     * */
    public AddUser(){
        setContentPane(panelNewUser);
        setModal(true);
        setTitle("Registrar nuevo usuario");
        setLocationRelativeTo(null);
        setSize(350,250);
        setResizable(false);

        //guardar usuario
        btnGuardar.addActionListener((e)->{
            //validar nuevo usuario
            validarNuevoUsuario();
        });

        btnCancelar.addActionListener((e)->{
            cancelar();
        });
    }

    /**
     * Método para cancelar el registro de nuevo usuario
     * */
    private void cancelar() {
        dispose();
        var loggin = new Loggin();
        loggin.setVisible(true);
    }

    /**
     * Método para validar un nuevo usuario y registrarlo
     * */
    private void validarNuevoUsuario() {
        Usuario nuevoUsuario = userDao.validateNewUser(newUser.getText());

        if (nuevoUsuario == null){
            if (Arrays.equals(newPass.getPassword(), newPass2.getPassword())){
                nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre_usuario(newUser.getText());
                String passString = new String(newPass.getPassword());
                nuevoUsuario.setPassword(passString);

                userSelected = nuevoUsuario;
                copyDTO = new ArrayList<>();
                userSelected.setMicopia(copyDTO);
                userDao.save(userSelected);

                dispose();
                var principal = new Principal();
                principal.setVisible(true);

            }else {
                JOptionPane.showMessageDialog(this,"La contraseña no coincide");
            }
        }else {
            System.out.println(nuevoUsuario);
            JOptionPane.showMessageDialog(this,"Ese nombre de usuario no está disponible");
        }

    }


}
