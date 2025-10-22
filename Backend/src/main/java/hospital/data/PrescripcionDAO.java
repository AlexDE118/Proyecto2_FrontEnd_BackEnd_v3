package hospital.data;

import hospital.logic.Prescripcion;
import hospital.logic.Receta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDAO {
    Database db;

    public PrescripcionDAO() {
        db = Database.instance();
    }

    public void create(Prescripcion p) throws Exception {
        String sql = "insert into Prescripcion (estado, fechaConfeccion, fechaRetiro, receta, paciente) "
        + "values (?, ?, ?, ?, ?)";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, p.getEstado());
        stm.setString(2,p.getFechaConfeccion().toString());
        stm.setString(3,p.getFechaRetiro().toString());
        stm.setInt(4,p.getReceta().get(0).getNumero());
        stm.setString(5, p.getPaciente().getId());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Error al insertar prescripcion en database");
        }
    }

    public Prescripcion read(String id) throws Exception {
        String sql = "select * from Prescripcion p "
                + "inner join Paciente pac on p.paciente = pac.id "
                + "inner join Receta r on p.receta = r.numero "
                + "where p.id = ?";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        Prescripcion p;
        List<Receta> lista = new ArrayList<>();
        PacienteDAO pdao = new PacienteDAO();
        RecetaDAO rdao = new RecetaDAO();
        if (rs.next()) {
            p = from(rs, "p");
            p.setPaciente(pdao.from(rs,"pac"));
            lista.add(rdao.from(rs,"r"));
            p.setReceta(lista);
            return p;
        } else  {
            throw new Exception("Error al consultar prescripcion en database");
        }
    }

    public void update(Prescripcion p) throws Exception {

    }

    public void delete(Prescripcion p) throws Exception {
        String sql = "delete from Prescripcion p where p.id = ?";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1,p.getId());
    }

    public List<Prescripcion> findByID(String id) {
        List<Prescripcion> resultado = new ArrayList<>();
        try{
            String sql = "SELECT * FROM prescripcion p where id = ?";
            PreparedStatement stm = db.preparedStatement(sql);
            stm.setString(1,"%"+id+"%");
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Prescripcion p = new Prescripcion();
            }
        } catch (SQLException ex) { }
        return resultado;
    }
    public Prescripcion from(ResultSet rs, String alias) {
        PacienteDAO pdao = new PacienteDAO();
        RecetaDAO rdao = new RecetaDAO();
        try{
            Prescripcion p = new Prescripcion();
            p.setEstado(rs.getString(alias + ".estado"));
            p.setFechaConfeccion(LocalDate.parse(rs.getString(alias+ ".fechaConfeccion")));
            p.setFechaRetiro(LocalDate.parse(rs.getString(alias+ ".fechaRetiro")));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }

}
