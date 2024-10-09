package reto;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    public static Connection con;

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

    public static Connection getCon() {
        return con;
    }
}
