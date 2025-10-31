package hospital.data;

import hospital.logic.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    Database db;

    public DoctorDAO() {
        db = Database.instance();
    }

    public void create(Doctor doctor) throws Exception {

        String sql = "insert into doctor (id, nombre, especialidad, clave)"
                +"values (?, ?, ?, ?)";
//        System.out.println(doctor.getId());
//        System.out.println(doctor.getNombre());
//        System.out.println(doctor.getEspecialidad());
//        System.out.println(doctor.getClave());
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, doctor.getId());
        stm.setString(2, doctor.getNombre());
        stm.setString(3, doctor.getEspecialidad());
        stm.setString(4, doctor.getClave());
        int count = db.executeUpdate(stm);
//        System.out.println(db.executeUpdate(stm));
//        System.out.println(count);
        if(count == 0)
            throw new Exception(": Error al agregar doctor a database");
    }

    public Doctor read(String id) throws Exception {
        String sql = "select * from doctor d where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        Doctor doctor;
        if(rs.next()) {
            doctor = from(rs,"d");
            return doctor;
        }else
            throw new Exception("Error al obtener doctor de database");
    }

    public void update(Doctor doctor) throws Exception {
        String sql = "update doctor set nombre = ?, especialidad = ?, clave = ? where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stm.setString(1, doctor.getNombre());
        stm.setString(2, doctor.getEspecialidad());
        stm.setString(3, doctor.getClave());
        stm.setString(4, doctor.getId());

        int count = db.executeUpdate(stm);
        if(count == 0)
            throw new Exception("Error al actualizar doctor en database");
    }

    public void delete(String id) throws Exception {
        String sql = "delete from doctor where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, id);
        int count = db.executeUpdate(stm);
        if(count == 0)
            throw new Exception("Error al eliminar doctor en database");
    }

    public List<Doctor> findAll() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        try {
            String sql = "select * from doctor d";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = db.executeQuery(stm);
            Doctor doctor;
            while (rs.next()) {
                doctor = from(rs, "d");
                doctors.add(doctor);
            }
        } catch (SQLException ex) { }
        return doctors;
    }

    public List<Doctor> findByName(String filtro) throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        try {
            String sql = "select * from doctor d where d.nombre like ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs = db.executeQuery(stm);
            Doctor doctor;
            while(rs.next()) {
                doctor = from(rs,"d");
                doctors.add(doctor);
            }
        } catch (SQLException ex) {}
        return doctors;
    }

    public List<Doctor> findByID(String filtro) throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        try {
            String sql = "select * from doctor d where d.id like ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs = db.executeQuery(stm);
            Doctor doctor;
            while(rs.next()) {
                doctor = from(rs,"d");
                doctors.add(doctor);
            }
        } catch (SQLException ex) {}
        return doctors;
    }

    private Doctor from(ResultSet rs, String alias){
        try{
          Doctor doctor = new Doctor();
          doctor.setId(rs.getString(alias+".id"));
          doctor.setNombre(rs.getString(alias+".nombre"));
          doctor.setEspecialidad(rs.getString(alias+".especialidad"));
          doctor.setClave(rs.getString(alias+".clave"));

          return doctor;
        } catch (SQLException ex) {
            return null;
        }
    }

}
