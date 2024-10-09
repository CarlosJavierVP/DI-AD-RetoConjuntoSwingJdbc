package reto.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase principal para el marco de la ventana
 * */
public class Principal extends JFrame{
    private JPanel ventanaLista;
    private JTable listadoPelis;
    private DefaultTableModel model;
    private JLabel title;
    private JButton btnVolver;
    private JButton btnSalir;
    private JPanel botones;

    /**
     * Método principal para mostrar el listado de películas y las funcionalidades de la ventana
     */
    public Principal(){
        //Establece los campos de la tabla
        String [] campos ={"Título","Estado","Soporte"};
        model = new DefaultTableModel(campos,0);
        //Inicializa la tabla
        listadoPelis.setModel(model);




        setContentPane(ventanaLista);
        setTitle("Listado de Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();

        listadoPelis.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loggin ventanaAntes = new Loggin();
                ventanaAntes.setVisible(true);
                dispose();
            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


}
