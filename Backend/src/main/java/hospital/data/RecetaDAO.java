package hospital.data;

import hospital.logic.Receta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecetaDAO {
    Database db;

    public RecetaDAO() {
        db = Database.instance();
    }

    public void create(Receta r) throws Exception {
        String sql = "insert into Receta (numero,cantidad,duracion,indicaciones,medicamentos)"
                +"values (?,?,?,?,?)";  
        PreparedStatement stm = db.preparedStatement(sql);
        //stm.setString(1,"0");
        stm.setInt(2,r.getCantidad());
        stm.setInt(3,r.getDuracion());
        stm.setString(4,r.getIndicaciones());
        stm.setString(5,r.getMedicamentos().getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Receta ya existe");
        }
    }

    public Receta read(int numero) throws Exception {
        String sql = "select * from Receta r"
                + "inner join Medicamento m on r.medicamentos = m.id"
                + "where r.numero=?";
        PreparedStatement stm = db.preparedStatement(sql);
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
        MedicamentoDAO md = new MedicamentoDAO();
        try{
            Receta r = new Receta();
            r.setCantidad(rs.getInt(alias+ ".cantidad"));
            r.setDuracion(rs.getInt(alias+ ".duracion"));
            r.setIndicaciones(rs.getString(alias+ ".indicaciones"));
            //r.setMedicamentos();
            return r;
        } catch (SQLException ex){
            return null;
        }
    }
}
