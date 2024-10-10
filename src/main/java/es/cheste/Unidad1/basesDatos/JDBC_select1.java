package es.cheste.Unidad1.basesDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_select1 {
    private static final Logger LOGGER = LogManager.getRootLogger();
    public static void main(String[] args) {

        ConexionBD conexion = new ConexionBD();
        hacerSelect(conexion);
        conexion.desconectar();
    }

    public static void hacerSelect(ConexionBD conexion) {

        int i = 1;

        try (Statement stmt = conexion.getConnection().createStatement();){
            ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENTES");
            while (rs.next()){
                System.out.println("[" + (i++) + "]");
                System.out.println("DNI: ");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
