package es.cheste.Unidad1.basesDatos;

import java.sql.*;

public class JDBC_callable_statement {
    public static void main(String[] args) {
        // Crear una instancia de la clase ConexionBD para obtener la conexión con la base de datos.
        ConexionBD conexion = new ConexionBD();

        callable(conexion);

        // Desconectar la conexión a la base de datos una vez finalizado el uso.
        conexion.desconectar();
    }

    public static void callable (ConexionBD conexion) {
        try {
            // Obtener la conexión a la base de datos.
            Connection con = conexion.getConnection();

            // Preparar la llamada al procedimiento almacenado 'listado_parcial_clientes'.
            // Los parámetros están entre llaves y se definen con '?', uno de entrada (IN) y uno de salida (INOUT).
            CallableStatement s = con.prepareCall("{call listado_parcial_clientes(?,?)}");

            // Establecer el valor del primer parámetro de entrada (DNI del cliente).
            s.setString(1, "78901234x");

            // Establecer el valor inicial del parámetro INOUT (inout_long).
            s.setInt(2, 0);

            // Registrar el parámetro de salida (INOUT) para recibir el valor de la longitud calculada en el procedimiento.
            s.registerOutParameter(2, Types.INTEGER);

            // Ejecutar el procedimiento almacenado.
            s.execute();

            // Obtener el conjunto de resultados devueltos por el procedimiento almacenado.
            ResultSet rs = s.getResultSet();

            // Obtener el valor del parámetro de salida (la longitud calculada del apellido).
            int inout_long = s.getInt(2);
            System.out.println("=> inout long: " + inout_long);

            // Inicializar un contador para contar los clientes que se mostrarán.
            int nCli = 0;

            // Recorrer los resultados devueltos por el procedimiento y mostrar la información del cliente.
            while (rs.next()) {
                // Incrementar y mostrar el número del cliente actual.
                System.out.println("[" + (++nCli) + "] ");

                // Mostrar el DNI del cliente actual.
                System.out.println("DNI: " + rs.getString("DNI"));

                // Mostrar los apellidos del cliente actual.
                System.out.println("APELLIDOS: " + rs.getString("APELLIDOS"));
            }
        } catch (SQLException e) {
            // Capturar y mostrar los errores de SQL que puedan ocurrir durante la ejecución.
            System.out.println("Error  SQL: " + e.getMessage());
        } catch (Exception e) {
            // Capturar cualquier otro tipo de excepción y mostrar la traza de error.
            e.printStackTrace(System.err);
        }
    }
}

//DELIMITER //
//
//CREATE PROCEDURE listado_parcial_clientes
//        (IN in_dni CHAR(9), INOUT inout_long INT)
//BEGIN
//DECLARE apell VARCHAR(32) DEFAULT NULL;
//SELECT APELLIDOS FROM clientes WHERE DNI = in_dni  INTO apell;
//SET inout_long = inout_long + LENGTH(apell);
//        SELECT DNI, APELLIDOS FROM CLIENTES
//WHERE APELLIDOS <= apell ORDER BY APELLIDOS;
//END //
//
//        DELIMITER ;