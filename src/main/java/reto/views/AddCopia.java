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
    private JLabel warning;

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

        if ( peliDTO != null && soporteCombo != null && estadoPeli()) {
            estadoPeli();
            //Setear la copia nueva
            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado(copySelected.getEstado());
            nuevaCopia.setSoporte((String) soporteCombo.getSelectedItem());
            nuevaCopia.setId_pelicula(peliDTO.getId());
            nuevaCopia.setId_usuario(userSelected.getId());
            nuevaCopia.setPeli(peliDTO);
            nuevaCopia.setU(userSelected);

            //Añadir al listado de copias la copiaNueva
            copyDTO.add(nuevaCopia);
            //guardar la copia en la base de datos
            cDao.save(nuevaCopia);

            var principal = new Principal();
            dispose();
            principal.setVisible(true);

        } else{
            warning.setText("Introduce todos los valores");
        }

    }
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

    private void cancelar() {
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }
}
