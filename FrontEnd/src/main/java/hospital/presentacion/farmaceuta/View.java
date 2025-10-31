package hospital.presentacion.farmaceuta;

//import javax.swing.*;
//import java.beans.PropertyChangeEvent;

import hospital.logic.Farmaceuta;
import hospital.logic.Usuario;
import hospital.logic.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel farmaceutaPanel;
    private JTextField id_textField;
    private JTextField nombre_textField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField nombreBuscar_textField;
    private JTextField idBuscar_textField;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable farmaceutas_Table;
    private JButton cargarListaButton;
    private JButton actualizarButton;

    private hospital.presentacion.farmaceuta.actualizar.View actualizarView;

    public JPanel getFarmaceutaPanel() {
        return farmaceutaPanel;
    }

    public View(){
        actualizarView = new hospital.presentacion.farmaceuta.actualizar.View();
        actualizarView.setController(this.controller);
        actualizarView.setModel(this.model);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Farmaceuta farmaceuta = new Farmaceuta();
                farmaceuta.setId(id_textField.getText());
                farmaceuta.setNombre(nombre_textField.getText());
                farmaceuta.setClave(id_textField.getText());
                Usuario usuario = new Usuario();
                usuario.setId(id_textField.getText());
                usuario.setClave(id_textField.getText());
                usuario.setUserType(farmaceuta.getClass().getSimpleName());
                try{
                    if (!validate()) {
                        return;
                    }

                    controller.createFarmaceuta(farmaceuta);
                    Service.instance().addUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Farmaceuta guardado exitosamente");
                    controller.loadFarmaceutas();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(farmaceutaPanel, "Error al crear la farmaceuta "+ex.getMessage());
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id_textField.setText("");
                nombre_textField.setText("");
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarView.setVisible(true);
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchFarmaceuta(nombreBuscar_textField.getText(),idBuscar_textField.getText());
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Farmaceuta farmaceuta = new Farmaceuta();
                farmaceuta.setId(id_textField.getText());
                try{
                    controller.deleteFarmaceuta(farmaceuta);
                    controller.loadFarmaceutas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(farmaceutaPanel, "Error al borrar el Doctor " + ex.getMessage());
                }

            }
        });

        cargarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadFarmaceutas();
                System.out.println(model.getListaFarmaceuta());
            }
        });

        farmaceutas_Table.getSelectionModel().addListSelectionListener( e->{
            if (!e.getValueIsAdjusting()) {
                int row = farmaceutas_Table.getSelectedRow();
                if(row !=-1){
                    Farmaceuta selected = model.getListaFarmaceuta().get(row);
                    model.setCurrent(selected);
                }
            }
        });
    }

    // ---- mvc ---- //

    Model model;
    Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
        if(actualizarView!=null){
            actualizarView.setController(controller);
        }
    }

    public void setModel(Model model){
        this.model = model;
        //model.addPropertyChangeListener(this);
        if(actualizarView!=null){
            actualizarView.setModel(model);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch (evt.getPropertyName()){
            case Model.CURRENT:
                id_textField.setText(model.getCurrent().getId());
                nombre_textField.setText(model.getCurrent().getNombre());
                break;
            case Model.LISTAFARMACEUTAS:
                int[] cols = {TableModel.ID, TableModel.NAME};
                TableModel tableModel = new TableModel(cols,model.getListaFarmaceuta());
                farmaceutas_Table.setModel(tableModel);
                farmaceutas_Table.updateUI();
                break;
        }
    }

    public boolean validate(){
        if(id_textField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID");
            return false;
        }
        if(nombre_textField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
            return false;
        }

        return true;
    }
}
