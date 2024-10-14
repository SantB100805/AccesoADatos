package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_CreateTableFactura {
    public static void main(String[] args) {

        //se omite declaracion de variables para los datos de conexion

        ConexionBD conexion = new ConexionBD();
        //crearTablaFactura(conexion);
        //crearTablaLineasFactura(conexion);
        conexion.desconectar();
    }

    public static void crearTablaFactura(ConexionBD conexion) {

        Connection con = null;
        Statement stmt = null;

        try {

            // Obtener la conexión
            con = conexion.getConnection();

            // Crear una declaración SQL
            stmt = con.createStatement();

            // Consulta SQL para crear la tabla FACTURAS
            String sqlCreateTable = "CREATE TABLE FACTURAS (" +
                    "NUM_FACTURA INTEGER AUTO_INCREMENT NOT NULL, " +
                    "DNI_CLIENTE CHAR(9) NOT NULL, " +
                    "PRIMARY KEY (NUM_FACTURA), " +
                    "FOREIGN KEY (DNI_CLIENTE) REFERENCES CLIENTES(DNI))";

            // Ejecutar la consulta SQL para crear la tabla
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabla FACTURAS creada con éxito.");

        }catch (SQLException e) {
            System.err.println("Error al crear la tabla FACTURAS: " + e.getMessage());
        }


    }

    public static void crearTablaLineasFactura(ConexionBD conexion) {

        Connection con = null;
        Statement stmt = null;

        try {

            // Obtener la conexión
            con = conexion.getConnection();

            // Crear una declaración SQL
            stmt = con.createStatement();

            // Consulta SQL para crear la tabla FACTURAS
            String sqlCreateTable = "CREATE TABLE LINEAS_FACTURA (" +
                    "NUM_FACTURA INTEGER NOT NULL, " +
                    "LINEA_FACTURA SMALLINT NOT NULL, " +
                    "CONCEPTO VARCHAR(32) NOT NULL, " +
                    "CANTIDAD SMALLINT NOT NULL, " +
                    "PRIMARY KEY (NUM_FACTURA, LINEA_FACTURA), " +
                    "FOREIGN KEY (NUM_FACTURA) REFERENCES FACTURAS(NUM_FACTURA))";

            // Ejecutar la consulta SQL para crear la tabla
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Tabla LINEAS_FACTURAS creada con éxito.");

        }catch (SQLException e) {
            System.err.println("Error al crear la tabla LINEAS_FACTURAS: " + e.getMessage());
        } finally {

            try {
                // Cerrar el statement y la conexión
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }

        }


    }


}
