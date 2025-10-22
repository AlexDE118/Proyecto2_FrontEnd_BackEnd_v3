package hospital;

import hospital.logic.Service;

import javax.swing.*;

public class AppTest {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) { ex.printStackTrace();}


        Service service = Service.instance(); // Initialize service

        JFrame window = new JFrame();
        window.setSize(1300, 500);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Proyecto_1");

        // Add a test button

        JButton testButton = new JButton("Test XML Generation");
        testButton.addActionListener(e -> {
            try {
                service.stop(); // This will trigger XML storage
                System.out.println(Service.instance().loadListaFarmaceutas());
                System.out.println(Service.instance().loadListaMedicamentos());
                System.out.println(Service.instance().loadListaPacientes());
                System.out.println(Service.instance().loadListaRecetas());
                System.out.println(Service.instance().loadListaPrescripciones());
                JOptionPane.showMessageDialog(window, "XML file generated/updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window, ex.getMessage());
            }
        });

        window.add(testButton);
        window.setVisible(true);
    }
}
