package reto.views;

import reto.JdbcUtils;
import reto.dao.PeliculaDAO;
import reto.models.Copia;
import reto.models.Pelicula;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static reto.Session.copySelected;
import static reto.UtilityDTO.copyDTO;
import static reto.UtilityDTO.peliDTO;

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

        for(Copia c : copyDTO){
            PeliculaDAO daoPeli = new PeliculaDAO(JdbcUtils.getCon());
            Pelicula peli = daoPeli.findById(c.getId_pelicula());
            c.setPeli(peli);
            Object[] fila ={peli.getTitulo(),c.getEstado(), c.getSoporte()};
            model.addRow(fila);
        }

        setContentPane(ventanaLista);
        setTitle("Listado de Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();


        listadoPelis.getSelectionModel().addListSelectionListener( e ->{
            PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());
            if (e.getValueIsAdjusting()) return;
            var titulo = model.getValueAt(listadoPelis.getSelectedRow(),0).toString();
            var estado = model.getValueAt(listadoPelis.getSelectedRow(),1).toString();
            var soporte = model.getValueAt(listadoPelis.getSelectedRow(),2).toString();

            int select = listadoPelis.getSelectedRow();
            copySelected = copyDTO.get(select);
            var idPeli = copyDTO.get(select).getId_pelicula();

            peliDTO = peliDao.findById(idPeli);

            System.out.println(peliDTO);

            var detalle = new Detalle();
            detalle.setVisible(true);
            dispose();

        });

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
