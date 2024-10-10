package es.cheste.Unidad1.basesDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_insert {
    private static final Logger LOGGER = LogManager.getRootLogger();


    public static void main(String[] args) {

        ConexionBD conexion = new ConexionBD();
        hacerInsert(conexion);
        conexion.desconectar();
    }

    public static void hacerInsert(ConexionBD conexion) {

        String sql = "INSERT INTO CLIENTES (DNI, APELLIDOS,CP) VALUES"
                + "('78901234X' ,'NADALANES','44126'), "
                +"('89012345E' ,'HOJAS', null), "
                + "('56789012B' ,'SAMPER','29730'), "
                +"('09876543K' ,'LAMIQUIZ',null); ";
        if(conexion == null) {
            LOGGER.error("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            System.out.println("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            return;
        }

        try (Statement stmt = conexion.getConnection().createStatement();){
            stmt.execute(sql);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
