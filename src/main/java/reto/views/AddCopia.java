package reto.views;

import reto.JdbcUtils;
import reto.dao.CopiaDAO;
import reto.models.Copia;

import javax.swing.*;

import static reto.Session.copySelected;

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

        CopiaDAO cDAO = new CopiaDAO(JdbcUtils.getCon());
        Copia nuevaCopia = new Copia();



        var opcionesSoporte = new DefaultComboBoxModel<String>();
        soporteCombo.setModel(opcionesSoporte);
        opcionesSoporte.addElement("");
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");
        //setear el soporte en la nueva copia
        nuevaCopia.setSoporte(opcionesSoporte.toString());

        //setear peliculaCombo





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

    }
}
