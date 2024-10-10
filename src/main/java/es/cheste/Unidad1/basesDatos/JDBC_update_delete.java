package es.cheste.Unidad1.basesDatos;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_update_delete {
    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        actualizarClientes(conexion);
        conexion.desconectar();
    }

    public static void actualizarClientes(ConexionBD conexion) {
        if (conexion == null) {
            LOGGER.error("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            System.out.println("No hay conexión con base de datos, no se puede ejecutar la consulta.");
            return;
        }

        try (Statement stmt = conexion.getConnection().createStatement();) {
            // DELETE: Eliminar los registros que no coinciden
            String sqlDelete = "DELETE FROM CLIENTES WHERE DNI NOT IN ('78901234X', '89012345E', '56789012B')";
            stmt.executeUpdate(sqlDelete);

            // UPDATE: Actualizar los registros existentes
            String sqlUpdate1 = "UPDATE CLIENTES SET APELLIDOS = 'NADALES', CP = '44126' WHERE DNI = '78901234X'";
            stmt.executeUpdate(sqlUpdate1);

            String sqlUpdate2 = "UPDATE CLIENTES SET APELLIDOS = 'ROJAS', CP = null WHERE DNI = '89012345E'";
            stmt.executeUpdate(sqlUpdate2);

            String sqlUpdate3 = "UPDATE CLIENTES SET APELLIDOS = 'SAMPER', CP = '29730' WHERE DNI = '56789012B'";
            stmt.executeUpdate(sqlUpdate3);

            System.out.println("Registros actualizados correctamente.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
