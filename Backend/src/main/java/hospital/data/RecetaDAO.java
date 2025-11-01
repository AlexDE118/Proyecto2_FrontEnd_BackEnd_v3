package hospital.data;

import hospital.logic.Receta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RecetaDAO {
    Database db;

    public RecetaDAO() {
        db = Database.instance();
    }

    public Receta create(Receta r) throws Exception {
        // INSERT
        String sql = "INSERT INTO Receta (cantidad,duracion,indicaciones,medicamentos) VALUES (?,?,?,?)";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stm.setInt(1, r.getCantidad());
        stm.setInt(2, r.getDuracion());
        stm.setString(3, r.getIndicaciones());
        stm.setString(4, r.getMedicamentos().getCodigo());

        int count = db.executeUpdate(stm);
        if (count == 0) throw new Exception("Receta no se pudo insertar");

        // Obtener LAST_INSERT_ID con PreparedStatement
        PreparedStatement stmId = db.preparedStatement("SELECT LAST_INSERT_ID() AS id",0);
        ResultSet rs = db.executeQuery(stmId);
        if (rs.next()) {
            int generated = rs.getInt("id");
            r.setNumero(generated);
            System.out.println("DEBUG: ID generado = " + generated);
        } else {
            throw new Exception("No se pudo obtener el ID de la receta");
        }

        return r;
    }





    public Receta read(int numero) throws Exception {
        String sql = "select * from Receta r"
                + "inner join Medicamento m on r.medicamentos = m.id"
                + "where r.numero=?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setInt(1, numero);
        ResultSet rs = db.executeQuery(stm);
        Receta r;
        MedicamentoDAO md = new MedicamentoDAO();
        if (rs.next()) {
            r = from(rs, "r");
            r.setMedicamentos(md.from(rs, "m"));
            return r;
        } else {
            throw new Exception("Receta no encontrada");
        }
    }

    public void update(Receta r) throws Exception {

    }

    public void delete(Receta r) throws Exception {

    }

    public Receta from(ResultSet rs, String alias) {
        try {
            Receta r = new Receta();
            r.setNumero(rs.getInt(alias + ".numero"));
            r.setCantidad(rs.getInt(alias + ".cantidad"));
            r.setDuracion(rs.getInt(alias + ".duracion"));
            r.setIndicaciones(rs.getString(alias + ".indicaciones"));
            // Note: Medicamento will be set separately in the PrescripcionDAO
            return r;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
