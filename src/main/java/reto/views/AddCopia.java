package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
import reto.dao.PeliculaDAO;
import reto.models.Copia;
import reto.models.Pelicula;

import javax.swing.*;

import java.util.List;

import static reto.Session.userSelected;

public class AddCopia extends JDialog {

    private JPanel ventanaAdd;
    private JPanel DatosAdd;
    private JComboBox soporteCombo;
    private JComboBox peliculaCombo;
    private JRadioButton buenoRadioButton;
    private JRadioButton dañadoRadioButton;
    private JButton btnGuardar;
    private JButton btnCancelar;


    public AddCopia(){
        setContentPane(ventanaAdd);
        setModal(true);
        setTitle("Añadir Copia");
        setLocationRelativeTo(null);
        setSize(300,250);
        setResizable(false);
        //pack();

        CopiaDAO cDao = new CopiaDAO(JdbcUtils.getCon());
        PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());
        Copia nuevaCopia = new Copia();
        nuevaCopia.setU(userSelected);
        nuevaCopia.setId_usuario(userSelected.getId());

        //añadir al comboBox de soporte las opciones
        var opcionesSoporte = new DefaultComboBoxModel<String>();
        soporteCombo.setModel(opcionesSoporte);
        opcionesSoporte.addElement("");
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");
        //setear el soporte en la nueva copia
        nuevaCopia.setSoporte(opcionesSoporte.toString());

        //añadir al comboBox de pelis las opciones
        var opcionesPeliculas = new DefaultComboBoxModel<String>();
        peliculaCombo.setModel(opcionesPeliculas);
        List<Pelicula> listaPelis = peliDao.findAll();

        for (Pelicula peli : listaPelis){
            opcionesPeliculas.addElement(peli.getTitulo());
            //Setear la id_pelicula de la copia con la id de la peli seleccionada en el comboBox
            nuevaCopia.setId_pelicula(peli.getId());

            //Setear la película de la copia con la peli seleccionada
            nuevaCopia.setPeli(peli);
        }

        buenoRadioButton.addActionListener( e ->{
            if (buenoRadioButton.isSelected()){
                dañadoRadioButton.setSelected(false);
                nuevaCopia.setEstado("bueno");
            }
        });

        dañadoRadioButton.addActionListener( e ->{
            if (dañadoRadioButton.isSelected()){
                buenoRadioButton.setSelected(false);
                nuevaCopia.setEstado("dañado");
            }
        });

        btnGuardar.addActionListener( e ->{
            nuevaCopia.setId_usuario(userSelected.getId());
            nuevaCopia.setU(userSelected);
            var principal = new Principal();
            principal.setVisible(true);
            dispose();
        });

        btnCancelar.addActionListener( e ->{
            cDao.delete(nuevaCopia);

        });

    }
}
