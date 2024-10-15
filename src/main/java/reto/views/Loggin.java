package reto.views;

import reto.JdbcUtils;
import reto.Session;
import reto.UtilityDTO;
import reto.dao.CopiaDAO;
import reto.dao.UsuarioDAO;
import reto.models.Copia;
import reto.models.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static reto.Session.paramsnotnull;


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
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);


        iniciarSesionButton.addActionListener((e)->{
                UsuarioDAO daoUser = new UsuarioDAO(JdbcUtils.getCon());
                CopiaDAO daoCopia = new CopiaDAO(JdbcUtils.getCon());

                Usuario u = daoUser.validateUser(user.getText(), pass.getPassword());

                if (u != null){
                    List<Copia> miCopia = daoCopia.findUser(u);
                    u.setMicopia(miCopia);

                    UtilityDTO.copyDTO = miCopia;
                    Session.userSelected = u;

                    Principal miLista = new Principal();

                    miLista.setVisible(true);
                    dispose();

                }else{
                    JOptionPane.showMessageDialog(this,"Error al ingresar cuenta");
                    user.setText("");
                    pass.setText("");
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
