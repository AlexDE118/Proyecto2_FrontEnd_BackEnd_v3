package hospital.data;

import hospital.logic.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class
UsuarioDAO {
    Database db;
    public UsuarioDAO() {
        db = Database.instance();

    }

    public void create(Usuario usuario) throws Exception {
        String sql = "insert into Usuario (id, clave, UserType, message, logged)"
                + " values (?, ?, ?, ?, ?)";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, usuario.getId());
        stm.setString(2, usuario.getClave());
        stm.setString(3, usuario.getUserType());
        stm.setString(4, "");
        stm.setBoolean(5, false);
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("No se pudo registrar el usuario");
        }
    }

    public Usuario read(Usuario usuario) throws Exception {
        String sql = "select * from Usuario u "
                + "where u.id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, usuario.getId());
        ResultSet rs = db.executeQuery(stm);
        Usuario u;
        if (rs.next()) {
            u = from(rs, "u");
            return u;
        }else {
            throw new Exception("No se encontro el usuario");
        }
    }

    public void updateLogged(Usuario usuario) throws Exception {
        String sql = "update Usuario set logged = ? where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setBoolean(1, usuario.getLogged());
        stm.setString(2, usuario.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("UsuarioDAO - No se pudo logear");
        }
    }

    public void updateMessage(Usuario usuario) throws Exception {
        String sql = "update Usuario set message = ? where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, usuario.getMessage());
        stm.setString(2, usuario.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("UsuarioDAO - No se pudo agregar el mensaje");
        }

    }

    public void update(Usuario usuario) throws Exception {
        String sql = "update Usuario set id = ?, clave = ?, UserType = ?"
                + "where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, usuario.getId());
        stm.setString(2, usuario.getClave());
        stm.setString(3, usuario.getUserType());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Usuario ya existe");
        }
        if (count == 0) {
            throw new Exception("Usuario no existe");
        }
    }

    public void delete(Usuario usuario) throws Exception {
        String sql = "delete from Usuario where id = ?";
        PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stm.setString(1,usuario.getId());
        int count = db.executeUpdate(stm);
        if(count == 0) {
            throw new Exception("Usuario no existe");
        }
    }

    public List<Usuario> findAll(){
        List<Usuario> usuarios = new ArrayList<>();
        try{
            String sql = "select * from usuario u";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = db.executeQuery(stm);
            Usuario usuario;
            while(rs.next()){
                usuario = from(rs, "u");
                usuarios.add(usuario);
            }
        } catch (SQLException e) { }
        return usuarios;
    }

    public List<Usuario> findByID(Usuario filtro) {
        List<Usuario> usuarios = new ArrayList<>();
        try{
            String sql = "select * from usuario u where u.id = ?";
            PreparedStatement stm = db.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,"%"+ filtro.getId() + "%");
            ResultSet rs = db.executeQuery(stm);
            Usuario usuario;
            while(rs.next()){
                usuario = from(rs, "u");
                usuarios.add(usuario);
            }
        } catch (SQLException ex) { }
        return usuarios;
    }


    public Usuario from(ResultSet rs, String alias) {
        try{
            Usuario usuario = new Usuario();
            usuario.setId(rs.getString(alias + ".id"));
            usuario.setClave(rs.getString(alias + ".clave"));
            usuario.setUserType(rs.getString(alias  + ".userType"));
            usuario.setMessage(rs.getString(alias + ".message"));
            usuario.setLogged(rs.getBoolean(alias + ".logged"));
            return usuario;
        } catch (SQLException e) {
            return null;
        }
    }
}
