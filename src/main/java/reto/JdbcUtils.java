package reto;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase JdbcUtils para establecer conexión con la base de datos
 * @author Carlos Javier
 * */
public class JdbcUtils {
    /**Atributo estático de Connection*/
    private static Connection con;

    /**Bloque estático con los atributos necesarios para establecer y setear la conexión*/
    static{
        String url="jdbc:mysql://localhost:3306/peliculas";
        String user="root";
        String pass= System.getenv("MYSQL_ROOT_PASSWORD");

        try {
            con = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método estático getCon() para llamar a la conexión
     * */
    public static Connection getCon() {
        return con;
    }
}
