package reto.views;

import reto.JdbcUtils;
import reto.Session;
import reto.dao.CopiaDAO;
import javax.swing.*;

import static reto.Session.*;

/**
 * Clase Detalle para la ventana de información de la copia seleccionada
 * @author Carlos Javier
 * */
public class Detalle extends JDialog {
    /**Atributo JPanel de la ventana de detalle*/
    private JPanel detailPane;
    /**Atributo JButton botón para volver a la ventana principal*/
    private JButton buttonBack;
    /**Atributo JTextField el campo del título de la película*/
    private JTextField titleDetail;
    /**Atributo JTextField el campo del género de la película*/
    private JTextField genreDetail;
    /**Atributo JTextField el campo del año de la película*/
    private JTextField yearDetail;
    /**Atributo JTextField el campo del director de la película*/
    private JTextField directorDetail;
    /**Atributo JTextArea el campo de la descripción de la película*/
    private JTextArea descriptionDetail;
    /**Atributo JTextField el campo del soporte de la copia*/
    private JTextField formatDetail;
    /**Atributo JTextField el campo del estadode la copia*/
    private JTextField conditionDetail;
    /**Atributo JButton botón para eliminar la copia de la lista*/
    private JButton btnEliminar;

    /**Atributo CopiaDAO para establecer la conexión JDBC */
    CopiaDAO cdao = new CopiaDAO(JdbcUtils.getCon());

    /**
     * Método Detalle() para mostrar la información específica de la copia seleccionada
     * */
    public Detalle(){
        setContentPane(detailPane);
        setModal(true);
        setTitle(peliDTO.getTitulo());
        setLocationRelativeTo(null);
        setResizable(false);


        titleDetail.setText(peliDTO.getTitulo());
        genreDetail.setText(peliDTO.getGenero());
        yearDetail.setText(peliDTO.getAño().toString());
        directorDetail.setText(peliDTO.getDirector());
        descriptionDetail.setText(peliDTO.getDescripcion());
        formatDetail.setText(copySelected.getSoporte());
        conditionDetail.setText(copySelected.getEstado());

        pack();

        buttonBack.addActionListener( e ->{
            ventanaAnterior();
        });

        btnEliminar.addActionListener( e -> {
            borrar();
        });
    }

    /**
     * Método ventanaAnterior() para volver a la ventana principal
     * */
    private void ventanaAnterior() {
        peliDTO = null;
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }

    /**
     * Método borrar() para eliminar la copia seleccionada de la lista
     * */
    private void borrar(){
        var resultado = JOptionPane.showConfirmDialog(this,"¿Desea borrar la copia?");
        if (resultado == JOptionPane.YES_OPTION){
            copyDTO.remove(copySelected);
            cdao.delete(copySelected);
            peliDTO = null;
        }
        dispose();
        var principal = new Principal();
        principal.setVisible(true);
    }

}
