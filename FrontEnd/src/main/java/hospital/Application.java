package hospital;

import hospital.logic.Service;
import hospital.logic.Usuario;
import hospital.presentacion.Sesion;
import hospital.presentacion.dashboard.View2;
import hospital.presentacion.doctor.Controller;
import hospital.presentacion.doctor.Model;
import hospital.presentacion.doctor.View;
import hospital.presentacion.medicamentos.MedicamentosController;
import hospital.presentacion.medicamentos.MedicamentosModel;
import hospital.presentacion.medicamentos.View3;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) { ex.printStackTrace(); }

        // ----- INICIALIZACIÓN DE VISTAS, MODELOS Y CONTROLADORES -----

        // DOCTORES
        View doctorView = new View();
        System.out.println(doctorView);
        Model doctorModel = new Model();
        System.out.println(doctorModel);
        Controller controllerDoctor = new Controller(doctorView, doctorModel);

        // FARMACEUTAS
        hospital.presentacion.farmaceuta.View farmaceutaView = new hospital.presentacion.farmaceuta.View();
        hospital.presentacion.farmaceuta.Model farmaceutaModel = new hospital.presentacion.farmaceuta.Model();
        hospital.presentacion.farmaceuta.Controller controllerFarmaceuta = new hospital.presentacion.farmaceuta.Controller(farmaceutaView, farmaceutaModel);

        // PACIENTES
        hospital.presentacion.paciente.View pacienteView = new hospital.presentacion.paciente.View();
        hospital.presentacion.paciente.Model pacienteModel = new hospital.presentacion.paciente.Model();
        hospital.presentacion.paciente.Controller pacienteController = new hospital.presentacion.paciente.Controller(pacienteView, pacienteModel);

        // MEDICAMENTOS
        MedicamentosModel medicamentosModel = new MedicamentosModel();
        View3 medicamentosView = new View3();
        MedicamentosController medicamentosController = new MedicamentosController(medicamentosView, medicamentosModel);

        // PRESCRIPCIÓN
        hospital.presentacion.prescripcion.View prescripcionView = new hospital.presentacion.prescripcion.View();
        hospital.presentacion.prescripcion.Model prescripcionModel = new hospital.presentacion.prescripcion.Model();
        hospital.presentacion.prescripcion.Controller prescripcionController = new hospital.presentacion.prescripcion.Controller(prescripcionView, prescripcionModel);

        // DASHBOARD
        View2 dashboardView = new View2();
        hospital.presentacion.dashboard.Model dashboardModel = new hospital.presentacion.dashboard.Model();
        hospital.presentacion.dashboard.Controller dashboardController = new hospital.presentacion.dashboard.Controller(dashboardModel, dashboardView);

        // DESPACHO
        hospital.presentacion.despacho.View despachoView = new hospital.presentacion.despacho.View();
        hospital.presentacion.despacho.Model despachoModel = new hospital.presentacion.despacho.Model();
        hospital.presentacion.despacho.Controller controllerDespacho = new hospital.presentacion.despacho.Controller(despachoModel, despachoView);

        //USUARIOS
//        hospital.presentacion.usuario.View usuarioView1 = null;
//        hospital.presentacion.usuario.Model usuarioModel1 = null;
//        hospital.presentacion.usuario.Controller usuarioController = null;

        // LOGIN
        hospital.presentacion.login.View loginView = new hospital.presentacion.login.View();
        hospital.presentacion.login.Model loginModel = new hospital.presentacion.login.Model();
        hospital.presentacion.login.Controller loginController = new hospital.presentacion.login.Controller(loginView, loginModel);

        //HISTORICO
        hospital.presentacion.historico.View historicoView = new hospital.presentacion.historico.View();
        hospital.presentacion.historico.Model historicoModel = new hospital.presentacion.historico.Model();
        hospital.presentacion.historico.Controller historicoController = new hospital.presentacion.historico.Controller(historicoView,historicoModel);

        // ACERCA DE
        hospital.presentacion.AcercaDe.View acercaDeView = new hospital.presentacion.AcercaDe.View();

        // ----- CREAR EL FRAME PRINCIPAL CON TABBED PANE -----
        JFrame window = new JFrame("Proyecto_1");
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setSize(1300, 500);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setContentPane(tabbedPane);
        //window.add(tabbedPane);

        // AGREGAR TODOS LOS TABS
        tabbedPane.addTab("Doctores", doctorView.getMedicos_JPanel());
        tabbedPane.addTab("Medicamentos", medicamentosView.getMedicamentos_JPanel());
        tabbedPane.addTab("Farmaceutas", farmaceutaView.getFarmaceutaPanel());
        tabbedPane.addTab("Pacientes", pacienteView.getPacientesJPanel());
        tabbedPane.addTab("Prescripcion", prescripcionView.getPrescripcionJPanel());
        tabbedPane.addTab("Despacho", despachoView.getDespacho_JPanel());
        tabbedPane.addTab("Dashboard", dashboardView.getDashboard_JPanel());
        tabbedPane.addTab("Historico",historicoView.getHistoricoJPanel());
        tabbedPane.addTab("Acerca de", acercaDeView.getAcercaDeJPanel());


        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, false);
        }


        JDialog loginDialog = new JDialog(window, "Login", true);
        loginDialog.setContentPane(loginView.getLoginJPanel());
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(window);




        loginView.getLogInButton().addActionListener(e -> {

            String idIngresado = loginView.getIdTextField().getText();
            String claveIngresada = new String(loginView.getPasswordField1().getText());

            Usuario u = new Usuario();
            u.setId(idIngresado);
            u.setClave(claveIngresada);

            try {
                Usuario usuarioReal = loginController.login(u);
                Sesion.setUsuario(usuarioReal);
                String tipo = Sesion.getUsuario().getUserType();

                hospital.presentacion.usuario.View usuarioView = new hospital.presentacion.usuario.View();
                hospital.presentacion.usuario.Model usuarioModel = new hospital.presentacion.usuario.Model();
                hospital.presentacion.usuario.Controller usuarioController = new hospital.presentacion.usuario.Controller(usuarioView, usuarioModel);

                tabbedPane.addTab("Usuarios", usuarioView.getJPanel_usuario());

                switch (tipo) {
                    case "Doctor":
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Prescripcion"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Dashboard"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Historico"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Acerca de"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Usuarios"), true);
                        break;
                    case "Farmaceuta":
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Despacho"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Dashboard"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Historico"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Acerca de"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Usuarios"), true);
                        break;
                    case "Admin":
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Doctores"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Farmaceutas"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Pacientes"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Medicamentos"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Dashboard"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Historico"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Acerca de"), true);
                        tabbedPane.setEnabledAt(tabbedPane.indexOfTab("Usuarios"), true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(window, "Usuario inválido");
                        return;
                }

                loginDialog.dispose();
                window.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(loginDialog, "Error: " + ex.getMessage());
            }
        });


        loginDialog.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    // Get the current logged-in user from Sesion
                    Usuario currentUser = Sesion.getUsuario();
                    if (currentUser != null && currentUser.getId() != null && !currentUser.getId().isEmpty()) {
                        System.out.println("Cerrando sesión automáticamente para: " + currentUser.getId());

                        // Call logout to set logged = false in backend
                        Service.instance().logout(currentUser);

                        System.out.println("Logout automático exitoso para: " + currentUser.getId());
                    }
                } catch (Exception ex) {
                    System.err.println("Error durante logout automático: " + ex.getMessage());
                    // Continue with shutdown even if logout fails
                } finally {
                    // Always stop the service
                    Service.instance().stop();
                }
            }
        });

//        window.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                try {
//                    // Get the current logged-in user from Sesion
//                    Usuario currentUser = Sesion.getUsuario();
//                    if (currentUser != null) {
//                        // Call logout to set logged = false in backend
//                        Service.instance().logout(currentUser);
//                        System.out.println("Usuario " + currentUser.getId() + " ha cerrado sesión automáticamente");
//                    }
//                } catch (Exception ex) {
//                    System.err.println("Error durante logout automático: " + ex.getMessage());
//                } finally {
//                    // Always stop the service
//                    Service.instance().stop();
//                }
//                //Service.instance().stop();
//            }
//        });
    }
}



