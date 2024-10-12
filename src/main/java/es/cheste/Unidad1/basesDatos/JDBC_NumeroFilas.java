package es.cheste.Unidad1.basesDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_NumeroFilas {
    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        obtenerNumeroFilas(conexion);
        conexion.desconectar();
    }

    public static void obtenerNumeroFilas(ConexionBD conexion) {
        try (Statement stmt = conexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENTES");

            // Mover el cursor a la última fila
            if (rs.last()) {
                // Obtener el número de la última fila, que es el total de filas
                int numeroFilas = rs.getRow();
                System.out.println("Número total de filas: " + numeroFilas);
            } else {
                System.out.println("No se encontraron filas.");
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
}

//Filas ->
//Columnas  1
//          v