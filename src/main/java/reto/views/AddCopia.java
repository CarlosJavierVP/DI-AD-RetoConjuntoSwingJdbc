package reto.views;

import reto.JdbcUtils;
import reto.dao.PeliculaDAO;
import reto.models.Copia;
import reto.models.Pelicula;
import javax.swing.*;
import java.util.List;
import static reto.Session.*;


public class AddCopia extends JDialog {
    private JPanel ventanaAdd;
    private JPanel DatosAdd;
    private JComboBox<String> soporteCombo;
    private JComboBox<String> peliculaCombo;
    private JRadioButton buenoRadioButton;
    private JRadioButton dañadoRadioButton;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private Pelicula p = new Pelicula();

    public AddCopia(){
        setContentPane(ventanaAdd);
        setModal(true);
        setTitle("Añadir Copia");
        setLocationRelativeTo(null);
        setSize(300,250);
        //setResizable(false);
        //pack();

        PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getCon());

        //añadir al comboBox de soporte las opciones
        var opcionesSoporte = new DefaultComboBoxModel<String>();
        soporteCombo.setModel(opcionesSoporte);
        opcionesSoporte.addElement("");
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");


        //añadir al comboBox de pelis las opciones
        var opcionesPeliculas = new DefaultComboBoxModel<String>();
        peliculaCombo.setModel(opcionesPeliculas);
        List<Pelicula> listaPelis = peliDao.findAll();


        for (Pelicula peli : listaPelis){
            opcionesPeliculas.addElement(peli.getTitulo());
        }
        //Setear en peliDTO la película elegida
        p = (Pelicula) opcionesPeliculas.getSelectedItem();
        peliDTO = p;


        btnGuardar.addActionListener( e ->{
            guardarCopia();
        });

        btnCancelar.addActionListener( e ->{
            cancelar();
        });

    }

    private void guardarCopia() {
        copySelected = new Copia();

        if ( peliDTO != null) {

            if (buenoRadioButton.isSelected()){
                copySelected.setEstado("bueno");
            } else if (dañadoRadioButton.isSelected()) {
                copySelected.setEstado("dañado");
            }

            //Setear la copia nueva
            Copia nuevaCopia = new Copia(
                    null,
                    copySelected.getEstado(),
                    soporteCombo.toString(),
                    peliDTO.getId(),
                    userSelected.getId()
            );
            nuevaCopia.setPeli(peliDTO);
            //Añadir al listado de copias la copiaNueva
            copyDTO.add(nuevaCopia);
        }
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }

    private void cancelar() {
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }
}
