package reto.views;

import reto.UtilityDTO;

import javax.swing.*;

import static reto.Session.copySelected;
import static reto.UtilityDTO.peliDTO;

public class Detalle extends JDialog {
    private JPanel detailPane;
    private JButton buttonBack;
    private JLabel titleDetail;
    private JLabel genreDetail;
    private JLabel yearDetail;
    private JLabel directorDetail;
    private JLabel descriptionDetail;
    private JLabel formatDetail;
    private JLabel conditionDetail;


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


    }

}
