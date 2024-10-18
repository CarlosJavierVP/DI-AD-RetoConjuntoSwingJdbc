package reto.views;

import reto.JdbcUtils;
import reto.dao.UsuarioDAO;
import reto.models.Copia;
import reto.models.Usuario;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static reto.Session.copyDTO;
import static reto.Session.userSelected;

public class AddUser extends JDialog {
    private JPanel panelNewUser;
    private JTextField newUser;
    private JPasswordField newPass;
    private JPasswordField newPass2;
    private JButton btnGuardar;
    private JButton btnCancelar;

    UsuarioDAO userDao = new UsuarioDAO(JdbcUtils.getCon());

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
            dispose();
            var loggin = new Loggin();
            loggin.setVisible(true);

        });

    }

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
