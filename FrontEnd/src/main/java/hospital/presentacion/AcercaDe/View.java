package hospital.presentacion.AcercaDe;

import javax.swing.*;

public class View {
    private JLabel Integrantes;
    private JLabel titulo;
    private JLabel Grupo;
    private JPanel AcercaDeJPanel;

    public JPanel getAcercaDeJPanel() {
        return AcercaDeJPanel;
    }

    public View() {
        titulo.setText("Despcho y Prescripcion de Recetas");
        Integrantes.setText("Alexander Dittel Escobar, Isaac Espinoza Alvarado");
        Grupo.setText("Grupo 01 - Lunes y Jueves 8:00am - 9:40am");

    }

}
