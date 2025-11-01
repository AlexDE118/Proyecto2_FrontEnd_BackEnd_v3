package hospital.data;

import hospital.logic.Medicamento;
import hospital.logic.Prescripcion;
import hospital.logic.Receta;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDAO {
    Database db;

    public PrescripcionDAO() {
        db = Database.instance();
    }

    public void create(Prescripcion p) throws Exception {
        if (p.getReceta() == null || p.getReceta().isEmpty())
            throw new Exception("No hay recetas asociadas a la prescripcion");

        if (p.getPaciente() == null || p.getPaciente().getId() == null)
            throw new Exception("Paciente inválido");

        System.out.println("Prescripcion:");
        System.out.println("Estado: " + p.getEstado());
        System.out.println("FechaConfeccion: " + p.getFechaConfeccion());
        System.out.println("FechaRetiro: " + p.getFechaRetiro());
        System.out.println("Receta: " + (p.getReceta().isEmpty() ? "VACIA" : p.getReceta().getFirst().getNumero()));
        System.out.println("Paciente: " + (p.getPaciente() == null ? "NULL" : p.getPaciente().getId()));



        String sql = "INSERT INTO Prescripcion (estado, fechaConfeccion, fechaRetiro, receta, paciente) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stm.setString(1, p.getEstado());
        stm.setDate(2, java.sql.Date.valueOf(p.getFechaConfeccion()));
        stm.setDate(3, java.sql.Date.valueOf(p.getFechaRetiro()));

        stm.setInt(4, p.getReceta().getFirst().getNumero());
        stm.setString(5, p.getPaciente().getId());

        try {
            int count = db.executeUpdate(stm);
            if(count == 0) throw new Exception("Error al insertar prescripcion en database");
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    public Prescripcion read(String id) throws Exception {
        String sql = "select * from Prescripcion p "
                + "inner join Paciente pac on p.paciente = pac.id "
                + "inner join Receta r on p.receta = r.numero "
                + "where p.id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        String sql = "UPDATE Prescripcion SET estado = ?, fechaConfeccion = ?, fechaRetiro = ? WHERE id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stm.setString(1, p.getEstado());
        stm.setDate(2, java.sql.Date.valueOf(p.getFechaConfeccion()));
        stm.setDate(3, java.sql.Date.valueOf(p.getFechaRetiro()));
        stm.setString(4, p.getId());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Prescripción no encontrada para actualizar");
        }
    }

    public void delete(Prescripcion p) throws Exception {
        String sql = "delete from Prescripcion p where p.id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1,p.getId());
    }

    public List<Prescripcion> findAll() throws Exception {
        List<Prescripcion> resultado = new ArrayList<>();

        String sql = "SELECT p.*, pac.*, r.*, m.* FROM Prescripcion p " +
                "INNER JOIN Paciente pac ON p.paciente = pac.id " +
                "INNER JOIN Receta r ON p.receta = r.numero " +
                "INNER JOIN Medicamento m ON r.medicamentos = m.id " +
                "ORDER BY p.fechaConfeccion DESC";

        PreparedStatement stm = db.preparedStatement(sql, Statement.NO_GENERATED_KEYS);
        ResultSet rs = db.executeQuery(stm);

        PacienteDAO pdao = new PacienteDAO();
        RecetaDAO rdao = new RecetaDAO();
        MedicamentoDAO mdao = new MedicamentoDAO();

        while (rs.next()) {
            Prescripcion p = from(rs, "p");
            p.setPaciente(pdao.from(rs, "pac"));
            Receta receta = rdao.from(rs, "r");
            if (receta != null) {
                Medicamento medicamento = mdao.from(rs, "m");
                receta.setMedicamentos(medicamento);
                List<Receta> recetas = new ArrayList<>();
                recetas.add(receta);
                p.setReceta(recetas);
            }

            resultado.add(p);
        }
        return resultado;
    }

    public List<Prescripcion> findByID(String id) {
        List<Prescripcion> resultado = new ArrayList<>();
        try{
            String sql = "SELECT * FROM prescripcion p where id = ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
            p.setId(rs.getString(alias+ ".id"));
            p.setEstado(rs.getString(alias + ".estado"));
            p.setFechaConfeccion(LocalDate.parse(rs.getString(alias+ ".fechaConfeccion")));
            p.setFechaRetiro(LocalDate.parse(rs.getString(alias+ ".fechaRetiro")));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }

}
