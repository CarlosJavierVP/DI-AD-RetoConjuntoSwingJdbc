package reto.views;

import reto.JdbcUtils;
import reto.Session;
import reto.dao.CopiaDAO;
import javax.swing.*;
import static reto.Session.copySelected;
import static reto.Session.peliDTO;


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

    CopiaDAO cdao = new CopiaDAO(JdbcUtils.getCon());

    public Detalle(){
        setContentPane(detailPane);
        setModal(true);
        setTitle(peliDTO.getTitulo());
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
            peliDTO = null;
            var principal = new Principal();
            principal.setVisible(true);
            dispose();
        });

        btnEliminar.addActionListener( e -> {
            cdao.delete(copySelected);
            peliDTO = null;
            var principal = new Principal();
            principal.setVisible(true);
            dispose();
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
