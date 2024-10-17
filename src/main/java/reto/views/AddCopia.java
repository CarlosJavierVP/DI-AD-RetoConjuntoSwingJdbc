package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
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
    private PeliculaDAO pDao = new PeliculaDAO(JdbcUtils.getCon());
    private CopiaDAO cDao = new CopiaDAO(JdbcUtils.getCon());

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


        btnGuardar.addActionListener( e ->{
            guardarCopia();
        });

        btnCancelar.addActionListener( e ->{
            cancelar();
        });

    }

    private void guardarCopia() {
        copySelected = new Copia();
        String titulo = (String) peliculaCombo.getSelectedItem();
        peliDTO = pDao.findByTitle(titulo);

        if ( peliDTO != null) {

            if (buenoRadioButton.isSelected()){
                copySelected.setEstado("bueno");
            } else if (dañadoRadioButton.isSelected()) {
                copySelected.setEstado("dañado");
            }

            //Setear la copia nueva
            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado(copySelected.getEstado());
            nuevaCopia.setSoporte((String) soporteCombo.getSelectedItem());
            nuevaCopia.setId_pelicula(peliDTO.getId());
            nuevaCopia.setId_usuario(userSelected.getId());
            nuevaCopia.setPeli(peliDTO);

            //Añadir al listado de copias la copiaNueva
            copyDTO.add(nuevaCopia);
            //guardar la copia en la base de datos
            cDao.save(nuevaCopia);

        }
        var principal = new Principal();
        dispose();
        principal.setVisible(true);

    }

    private void cancelar() {
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }
}
