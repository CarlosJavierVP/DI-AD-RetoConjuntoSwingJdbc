package reto.views;

import javax.swing.*;

public class AddCopia extends JDialog {

    private JPanel ventanaAdd;
    private JPanel DatosAdd;
    private JComboBox soporteCombo;
    private JComboBox soportePelicula;
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


    }
}
