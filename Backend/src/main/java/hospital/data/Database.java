package hospital.data;

import java.sql.*;
import java.util.Properties;

public class Database {
    private static Database theInstance;
    public static Database instance() {
        if (theInstance == null) {
            theInstance = new Database();
        }
        return theInstance;
    }

    public static final String PROPERTY_FILE_NAME = "/database.properties";
    Connection cnx;
    public Database() {
        cnx = this.getConnection();
        if (cnx == null){
            System.out.println("Error: No connection established - cnx is NULL");
            System.out.println(cnx);
        }
    }

    public Connection getConnection() {
        try {
            Properties prop = new Properties();
            prop.load(getClass().getResourceAsStream(PROPERTY_FILE_NAME));

            String driver = prop.getProperty("database_driver");
            String server = prop.getProperty("database_server");
            String port = prop.getProperty("database_port");
            String user = prop.getProperty("database_user");
            String password = prop.getProperty("database_password");
            String database = prop.getProperty("database_name");

            String URL_conexion = "jdbc:mysql://" + server + ":" + port + "/" +
                    database + "?user=" + user + "&password=" + password + "&serverTimezone=UTC";

            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(URL_conexion);

            System.out.println("Conexión establecida a la base de datos " + database);
            return cnx;

        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
            System.out.println("CNX:" + cnx);
            System.exit(-1);
            return null;
        }
    }


    public PreparedStatement preparedStatement(String statement, int returnGeneratedKeys) throws SQLException {
        System.out.print("STM:" + statement);
        System.out.println("CNX:" + cnx);
        return cnx.prepareStatement(statement, returnGeneratedKeys);
    }


    public int executeUpdate(PreparedStatement statement) throws SQLException {
        try{
            statement.executeUpdate();
            return statement.getUpdateCount();
        }catch (SQLException ex) {
            return 0;
        }
    }

    public ResultSet executeQuery (PreparedStatement statement) {
        try{
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void close() throws Exception {
        if(cnx != null && !cnx.isClosed()) {
            cnx.close();
        }
    }

    //END OF CLASS
}
