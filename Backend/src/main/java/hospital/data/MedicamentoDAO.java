package hospital.data;

import hospital.logic.Medicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {
    Database db;

    public MedicamentoDAO() {
        db = Database.instance();
    }

    public void create(Medicamento medicamento) throws Exception {
        String sql = "insert into Medicamento (id, nombre, presentacion)"
                + "values (?,?,?)";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, medicamento.getCodigo());
        stm.setString(2, medicamento.getNombre());
        stm.setString(3, medicamento.getPresentacion());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Error al insertar el Medicamento");
        }
    }

    public Medicamento read(String codigo) throws Exception {
        String sql = "select * from Medicamento m where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        Medicamento medicamento;
        if (rs.next()) {
            medicamento = from(rs, "m");
            return medicamento;
        } else {
            throw new Exception("Error al leer medicamento");
        }
    }

    public void update(Medicamento medicamento) throws Exception {
        String sql = "update Medicamento set nombre = ?, presentacion = ? where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, medicamento.getNombre());
        stm.setString(2, medicamento.getPresentacion());
        stm.setString(3, medicamento.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
        if (count == 0) {
            throw new Exception("Medicamento ya existe");
        }
    }

    public void delete(Medicamento medicamento) throws Exception {
        String sql = "delete from Medicamento where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, medicamento.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> findAll() throws Exception {
        String sql = "select * from Medicamento m";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = db.executeQuery(stm);
        List<Medicamento> medicamentos = new ArrayList<>();
        while (rs.next()) {
            Medicamento medicamento = from(rs, "m");
            medicamentos.add(medicamento);
        }
        return medicamentos;
    }

    public List<Medicamento> findByName(String filtro) throws Exception {
        List<Medicamento> resultado = new ArrayList<>();
        try {
            String sql = "select * from Medicamento m "
                    + "where m.nombre like ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(2, "%" + filtro + "%");
            ResultSet rs = db.executeQuery(stm);
            Medicamento medicamento;
            while (rs.next()) {
                medicamento = from(rs, "m");
                resultado.add(medicamento);
            }
        } catch (SQLException ex) { }
        return resultado;
    }

    public List<Medicamento> findByID(String filtro) throws Exception {
        List<Medicamento> resultado = new ArrayList<>();
        try {
            String sql = "select * from Medicamento m "
                    + "where m.id like ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "%" + filtro + "%");
            ResultSet rs = db.executeQuery(stm);
            Medicamento medicamento;
            while (rs.next()) {
                medicamento = from(rs, "m");
                resultado.add(medicamento);
            }
        } catch (SQLException ex) { }
        return resultado;
    }

    public Medicamento from(ResultSet rs, String alias) {
        try {
            Medicamento medicamento = new Medicamento();
            medicamento.setCodigo(rs.getString(alias + ".id"));
            medicamento.setNombre(rs.getString(alias + ".nombre"));
            medicamento.setPresentacion(rs.getString(alias + ".presentacion"));
            return medicamento;
        } catch (SQLException ex) {
            return null;
        }
    }

}
