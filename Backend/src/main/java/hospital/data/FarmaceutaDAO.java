package hospital.data;

import hospital.logic.Farmaceuta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDAO {
    Database db;

    public FarmaceutaDAO() {
        db = Database.instance();
    }

    public void create(Farmaceuta farmaceuta) throws Exception {
        String sql = "insert into Farmaceuta (id, nombre, clave)"
                + "values (?, ?, ?)" ;
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, farmaceuta.getId());
        stm.setString(2, farmaceuta.getNombre());
        stm.setString(3, farmaceuta.getClave());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Farmaceuta ya existe");
        }
    }

    public Farmaceuta read(String id) throws Exception {
        String sql = "select * from Farmaceuta f where id = ?";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        Farmaceuta farmaceuta;
        if (rs.next()) {
            farmaceuta = from(rs, "f");
            return farmaceuta;
        }
        else {
            throw new Exception("Farmaceuta no encontrado");
        }
    }

    public void update(Farmaceuta farmaceuta) throws Exception {
        String sql = "update Farmaceuta set nombre = ?, clave = ? where id = ?";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, farmaceuta.getNombre());
        stm.setString(2, farmaceuta.getClave());
        stm.setString(3, farmaceuta.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Error al actualizar farmaceuta");
        }
    }

    public void delete(Farmaceuta farmaceuta) throws Exception {
        String sql = "delete from Farmaceuta where id = ?";
        PreparedStatement stm = db.preparedStatement(sql);
        stm.setString(1, farmaceuta.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception ("Error aleliminar paciente");
        }
    }

    public List<Farmaceuta> findAll() throws Exception {
        String sql = "select * from Farmaceuta f";
        PreparedStatement stm = db.preparedStatement(sql);
        ResultSet rs = stm.executeQuery();
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        while (rs.next()) {
            Farmaceuta farmaceuta = from(rs, "f");
            farmaceutas.add(farmaceuta);
        }
        return farmaceutas;
    }

    public List<Farmaceuta> findByID(String filtro) throws Exception {
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            String sql = "select * from Farmaceuta f where f.id = ?";
            PreparedStatement stm = db.preparedStatement(sql);
            stm.setString(1, "%"+ filtro + "%");
            ResultSet rs = stm.executeQuery();
            Farmaceuta farmaceuta;
            while (rs.next()) {
                farmaceuta = from(rs, "f");
                farmaceutas.add(farmaceuta);
            }
        } catch (SQLException ex) {}
        return farmaceutas;
    }

    public List<Farmaceuta> findByName(String filtro) throws Exception {
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            String sql = "select * from Farmaceuta f where f.nombre = ?";
            PreparedStatement stm = db.preparedStatement(sql);
            stm.setString(1, "%"+ filtro + "%");
            ResultSet rs = stm.executeQuery();
            Farmaceuta farmaceuta;
            while (rs.next()) {
                farmaceuta = from(rs, "f");
                farmaceutas.add(farmaceuta);
            }
        } catch (SQLException ex) {}
        return farmaceutas;
    }

    public Farmaceuta from(ResultSet rs, String alias) {
        try{
            Farmaceuta farmaceuta = new Farmaceuta();
            farmaceuta.setId(rs.getString(alias + ".id"));
            farmaceuta.setNombre(rs.getString(alias + ".nombre"));
            farmaceuta.setClave(rs.getString(alias + ".clave"));
            return farmaceuta;
        } catch (SQLException ex) {
            return null;
        }
    }
}
