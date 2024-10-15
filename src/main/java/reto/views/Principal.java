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
import static reto.Session.paramsnotnull;
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
    private JButton btnAdd;

    /**
     * Método principal para mostrar el listado de películas y las funcionalidades de la ventana
     */
    public Principal(){
        //Establece los campos de la tabla
        String [] campos ={"Título","Estado","Soporte"};
        model = new DefaultTableModel(campos,0);
        //Inicializa la tabla
        listadoPelis.setModel(model);
        //Modificación de la anchura de la columna título, para dar más espacio en detrimento de "estado" y "soporte"
        var columnaTituloPeli = listadoPelis.getColumnModel().getColumn(0);
        columnaTituloPeli.setPreferredWidth(300);

        /*
        * se itera la lista de copias (que previamente en loggin se había seteado en copyDTO), y se añade cada elemento
        * de la copia que interesa poner en la tabla, se setea la Película de cada copia y se añade el título
        * */
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
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        //pack();


        /*
        * Funcionalidad al seleccionar un item de la tabla, en el que muestra una ventana de detalle con la información
        * de la copia y la película a la que se refiere
        * */
        listadoPelis.getSelectionModel().addListSelectionListener( e ->{
            PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());
            if (e.getValueIsAdjusting()) return;

            int select = listadoPelis.getSelectedRow();
            copySelected = copyDTO.get(select);
            var idPeli = copyDTO.get(select).getId_pelicula();

            peliDTO = peliDao.findById(idPeli);

            var detalle = new Detalle();
            detalle.setVisible(true);
            dispose();

        });

        btnAdd.addActionListener( e ->{
            var añadidCopia = new AddCopia();
            añadidCopia.setVisible(true);
            dispose();

        });

        //Botón volver a loggin
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loggin ventanaAntes = new Loggin();
                ventanaAntes.setVisible(true);
                dispose();
                paramsnotnull();
            }
        });

        //Botón cerrar aplicación
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paramsnotnull();
                dispose();
            }
        });
    }


}
