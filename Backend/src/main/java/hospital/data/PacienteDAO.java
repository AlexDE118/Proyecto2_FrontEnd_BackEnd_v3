package hospital.data;

import hospital.logic.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    Database db;

    public PacienteDAO() {
        db = Database.instance();
    }

    public void create(Paciente paciente) throws Exception {
        try {
            String sql = "INSERT INTO paciente (ID, Nombre, Telefono) VALUES (?, ?, ?)";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, paciente.getId());
            stm.setString(2, paciente.getNombre());
            stm.setString(3, paciente.getNumeroTelefono());

            int count = db.executeUpdate(stm);
            if (count == 0) {
                throw new Exception("Error al insertar Paciente a database - no rows affected");
            }
        } catch (SQLException e) {
            throw new Exception("DATABASE Error en base de datos al crear paciente: " + e.getMessage(), e);
        }
    }

//    public void create(Paciente paciente) throws Exception {
//        String sql = "INSERT INTO PACIENTE (id, nombre, telefono) " +
//                "VALUES (?, ?, ?)";
//
//        PreparedStatement stm = db.preparedStatement(sql);
//        stm.setString(1, paciente.getId());
//        stm.setString(2, paciente.getNombre());
//        stm.setString(3, paciente.getNumeroTelefono());
//        //LocalDate date = LocalDate.parse("2007-11-02");
//        //stm.setString(4, paciente.getFechaNacimiento().toString());
//        int count = db.executeUpdate(stm);
//        if (count == 0){
//            throw new Exception("Error al insertar Paciente a database");
//        }
//    }

    public Paciente read(String id) throws Exception {
        String sql = "SELECT * FROM PACIENTE P "
                + "WHERE id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        Paciente paciente;
        if (rs.next()){
            paciente = from (rs,"p");
            return paciente;
        }
        else {
            throw new Exception("DATABASE Paciente no existe");
        }
    }

    public void update(Paciente paciente) throws Exception {
        String sql =  "update Paciente set nombre=?, telefono=? where id=?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, paciente.getNombre());
        stm.setString(2, paciente.getNumeroTelefono());
        stm.setString(3, paciente.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("DATABASE Error al actualizar Paciente");
        }
    }

    public void delete(Paciente p) throws Exception {
        String sql = "DELETE FROM PACIENTE WHERE id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, p.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("DATABASE Error al eliminar Paciente");
        }
    }


    public List<Paciente> findAll(){
        List<Paciente> resultado = new ArrayList();
        try {
            String sql = "select * from paciente p";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = db.executeQuery(stm);
            Paciente p;
            while (rs.next()){
                p = from (rs,"p");
                resultado.add(p);
            }
        } catch (SQLException ex) {}
        System.out.println("Pacientes found: " + resultado.size());
        return resultado;
    }

    public List<Paciente> findByName(Paciente paciente) {
        List<Paciente> resultado = new ArrayList<Paciente>();
        try{
            String sql = "SELECT * FROM PACIENTE P WHERE P.NOMBRE LIKE ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "%"+paciente.getNombre()+"%");
            ResultSet rs = db.executeQuery(stm);
            Paciente p;
            while (rs.next()){
                p = from (rs,"P");
                resultado.add(p);
            }
        } catch (SQLException ex) { }
        return resultado;
    }

    public List<Paciente> findByID(Paciente paciente) {
        List<Paciente> resultado = new ArrayList<Paciente>();
        try{
            String sql = "SELECT * FROM PACIENTE P WHERE p.id like ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "%"+paciente.getId()+"%");
            ResultSet rs = db.executeQuery(stm);
            Paciente p;
            while (rs.next()){
                p = from (rs,"P");
                resultado.add(p);
            }
        } catch (SQLException ex) { }
        return resultado;
    }

    public Paciente from(ResultSet rs, String alias) {
        try{
            Paciente paciente = new Paciente();
            paciente.setId(rs.getString(alias +".id"));
            paciente.setNombre(rs.getString(alias +".nombre"));
            paciente.setNumeroTelefono(rs.getString(alias +".telefono"));

            return paciente;
        } catch (SQLException ex){
            return null;
        }
    }

    //END OF CLASS
}
