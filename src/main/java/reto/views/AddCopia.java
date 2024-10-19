package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
import reto.dao.PeliculaDAO;
import reto.models.Copia;
import reto.models.Pelicula;
import javax.swing.*;
import java.util.List;
import static reto.Session.*;

/**
 * Clase AddCopia para el marco de añadir una copia a la lista
 * @author Carlos Javier
 * */
public class AddCopia extends JDialog {
    /**Atributo JPanel de la ventana de añadir copia*/
    private JPanel ventanaAdd;
    /**Atributo JComboBox desplegable de opciones de soporte*/
    private JComboBox<String> soporteCombo;
    /**Atributo JComboBox desplegable de opciones de películas de la base de datos*/
    private JComboBox<String> peliculaCombo;
    /**Atributo JRadioButton estado de la copia bueno*/
    private JRadioButton buenoRadioButton;
    /**Atributo JRadioButton estado de la copia dañado*/
    private JRadioButton dañadoRadioButton;
    /**Atributo JButton botón para guardar la copia*/
    private JButton btnGuardar;
    /**Atributo JButton botón para cancelar la copia*/
    private JButton btnCancelar;
    /**Atributo JLabel aviso cuando faltan campos de la nueva copia por rellenar*/
    private JLabel warning;

    /**Atributo PeliculaDAO para establecer la conexión JDBC*/
    private PeliculaDAO pDao = new PeliculaDAO(JdbcUtils.getCon());
    /**Atributo CopiaDAO para establecer la conexión JDBC*/
    private CopiaDAO cDao = new CopiaDAO(JdbcUtils.getCon());
    /**Atributo PeliculaDAO para establecer la conexión JDBC*/
    PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());

    /**
     * Método AddCopia para mostrar la ventana de añadir copia y fijar sus parámetros
     * */
    public AddCopia(){
        setContentPane(ventanaAdd);
        setModal(true);
        setTitle("Añadir Copia");
        setLocationRelativeTo(null);
        setSize(300,250);
        setResizable(false);
        //pack();

        //añadir al comboBox de soporte las opciones
        setearOpcionesSoporte();

        //añadir al comboBox de pelis las opciones
        setearOpcionesPeliculas(peliDao);

        btnGuardar.addActionListener( e ->{
            guardarCopia();
        });

        btnCancelar.addActionListener( e ->{
            cancelar();
        });

    }

    /**
     * Método setearOpcionesPelículas para establecer las opciones de películas en el ComboBox
     * @param peliDao conexión JDBC con PeliculaDAO
     * */
    private void setearOpcionesPeliculas(PeliculaDAO peliDao) {
        var opcionesPeliculas = new DefaultComboBoxModel<String>();
        peliculaCombo.setModel(opcionesPeliculas);
        List<Pelicula> listaPelis = peliDao.findAll();
        for (Pelicula peli : listaPelis){
            opcionesPeliculas.addElement(peli.getTitulo());
        }
    }

    /**
     * Método setearOpcionesSoporte para establecer las opciones de soporte en el ComboBox
     * */
    private void setearOpcionesSoporte() {
        var opcionesSoporte = new DefaultComboBoxModel<String>();
        soporteCombo.setModel(opcionesSoporte);
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");
    }

    /**
     * Método guardarCopia para guardar la copia en la base de datos y setearla en la lista de copias
     * */
    private void guardarCopia() {
        copySelected = new Copia();
        String titulo = (String) peliculaCombo.getSelectedItem();
        peliDTO = pDao.findByTitle(titulo);

        if ( peliDTO != null && soportePeli() && estadoPeli()) {
            soportePeli();
            estadoPeli();

            //Setear la copia nueva
            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado(copySelected.getEstado());
            nuevaCopia.setSoporte(copySelected.getSoporte());
            nuevaCopia.setId_pelicula(peliDTO.getId());
            nuevaCopia.setId_usuario(userSelected.getId());
            nuevaCopia.setPeli(peliDTO);
            nuevaCopia.setU(userSelected);

            //Añadir al listado de copias la copiaNueva
            copyDTO.add(nuevaCopia);
            //guardar la copia en la base de datos
            cDao.save(nuevaCopia);

            dispose();
            var principal = new Principal();
            principal.setVisible(true);

        } else{
            warning.setText("Introduce todos los valores");
        }
    }
    /**
     * Método soportePeli para establecer el formato de la Película con ComboBox
     * */
    private boolean soportePeli(){
        boolean flag = false;
        if (soporteCombo.getSelectedItem() != null){
            copySelected.setSoporte((String)soporteCombo.getSelectedItem());
            flag = true;
        }
        return flag;
    }

    /**
     * Método estadoPeli para establecer el estado de la Película con RadioButton
     * */
    private boolean estadoPeli() {
        boolean flag = false;
        if (buenoRadioButton.isSelected()){
            copySelected.setEstado("bueno");
            flag = true;
        } else if (dañadoRadioButton.isSelected()) {
            copySelected.setEstado("dañado");
            flag = true;
        }
        return flag;
    }

    /**
     * Método cancelar para deshacer la copia que se está creando
     * */
    private void cancelar() {
        dispose();
        var principal = new Principal();
        principal.setVisible(true);
    }
}
