package reto.views;

import reto.JdbcUtils;
import reto.UtilityDTO;
import reto.dao.CopiaDAO;

import javax.swing.*;

import static reto.Session.copySelected;
import static reto.UtilityDTO.peliDTO;

public class Detalle extends JDialog {
    private JPanel detailPane;
    private JButton buttonBack;
    private JTextField titleDetail;
    private JTextField genreDetail;
    private JTextField yearDetail;
    private JTextField directorDetail;
    private JTextField descriptionDetail;
    private JTextField formatDetail;
    private JTextField conditionDetail;
    private JButton btnEliminar;


    public Detalle(){
        setContentPane(detailPane);
        setModal(true);
        setTitle(UtilityDTO.peliDTO.getTitulo());
        setLocationRelativeTo(null);

        titleDetail.setText(peliDTO.getTitulo());
        genreDetail.setText(peliDTO.getGenero());
        yearDetail.setText(peliDTO.getAño().toString());
        directorDetail.setText(peliDTO.getDirector());
        descriptionDetail.setText(peliDTO.getDescripcion());
        formatDetail.setText(copySelected.getSoporte());
        conditionDetail.setText(copySelected.getEstado());

        pack();

        buttonBack.addActionListener( e ->{
            var principal = new Principal();
            principal.setVisible(true);
            dispose();
        });

        btnEliminar.addActionListener( e -> {
            borrar();
        });


    }

    private void borrar(){
        var resultado = JOptionPane.showConfirmDialog(this,"¿Desea borrar la copia?");
        if (resultado == JOptionPane.YES_OPTION){
            CopiaDAO copyDao = new CopiaDAO(JdbcUtils.getCon());
            copyDao.delete(copySelected);
        }
    }

}
