package reto.views;

import reto.JdbcUtils;
import reto.dao.PeliculaDAO;
import reto.models.Copia;
import reto.models.Pelicula;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import static reto.Session.*;

/**
 * Clase principal para el marco de la ventana
 * @author Carlos Javier
 * */
public class Principal extends JFrame{
    /**Atributo JPanel de la ventana donde va la lista de copias del usuario*/
    private JPanel ventanaLista;
    /**Atributo JTable de la tabla donde vienen recogidas las copias del usuario*/
    private JTable listadoPelis;
    /**Atributo DefaultTableModel para establecer los campos de la fila 0 de la tabla*/
    private DefaultTableModel model;
    /**Atributo JButton botón para volver a la ventana de loggin*/
    private JButton btnVolver;
    /**Atributo JButton botón para cerrar la aplicación*/
    private JButton btnSalir;
    /**Atributo JButton botón para añadir una copia a la lista*/
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
        mostrarListaCopias();

        setContentPane(ventanaLista);
        setTitle("Listado de Películas - "+userSelected.getNombre_usuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        //pack();

        /*
        * Funcionalidad al seleccionar un item de la tabla, en el que muestra una ventana de detalle con la información
        * de la copia y la película a la que se refiere
        * */
        listadoPelis.getSelectionModel().addListSelectionListener(this::detalleCopia);

        btnAdd.addActionListener( e ->{
            ventanaAñadirCopia();
        });

        //Botón volver a loggin
        btnVolver.addActionListener((e)-> {
            volverLoggin();
        });

        //Botón cerrar aplicación
        btnSalir.addActionListener((e) -> {
                paramsnotnull();
                dispose();
        });
    }

    /**
     * Método mostrarListaCopias() muestra las copias en la tabla
     * */
    private void mostrarListaCopias() {
        for(Copia c : copyDTO){
            PeliculaDAO daoPeli = new PeliculaDAO(JdbcUtils.getCon());
            Pelicula peli = daoPeli.findById(c.getId_pelicula());
            c.setPeli(peli);
            Object[] fila ={peli.getTitulo(),c.getEstado(), c.getSoporte()};
            model.addRow(fila);
        }
    }

    /**
     * Método volverLoggin llamada al método que vuelve a la ventana anterior
     * */
    private void volverLoggin() {
        Loggin ventanaAntes = new Loggin();
        ventanaAntes.setVisible(true);
        dispose();
        paramsnotnull();
    }

    /**
     * Método ventanaAñadirCopia() que conduce a la ventana donde crear y guardar una copia
     * */
    private void ventanaAñadirCopia() {
        peliDTO = null;
        copySelected = null;
        var añadidCopia = new AddCopia();
        añadidCopia.setVisible(true);
        dispose();
    }

    /**
     * Método detalleCopia al seleccionar una copia de la lista la abre y muestra el detalle de la misma
     * @param e selección de un item de la lista
     * */
    private void detalleCopia(ListSelectionEvent e) {
        PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());
        if (e.getValueIsAdjusting()) return;

        int select = listadoPelis.getSelectedRow();
        copySelected = copyDTO.get(select);
        var idPeli = copyDTO.get(select).getId_pelicula();

        peliDTO = peliDao.findById(idPeli);

        var detalle = new Detalle();
        detalle.setVisible(true);
        dispose();
    }


}
