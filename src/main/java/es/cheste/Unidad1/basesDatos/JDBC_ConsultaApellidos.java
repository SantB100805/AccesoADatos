package es.cheste.Unidad1.basesDatos;

import java.sql.*;

public class JDBC_ConsultaApellidos {
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();

        // Lista de DNIs a consultar
        String[] dnis = {"56789012B", "76543210S", "90123456A"};

        for (String dni : dnis) {
            obtenerApellidos(conexion, dni);
        }

        conexion.desconectar();
    }

    public static void obtenerApellidos(ConexionBD conexion, String dniCliente) {
        Connection con = null;
        CallableStatement cs = null;

        try {
            // Obtener la conexión a la base de datos
            con = conexion.getConnection();

            // Preparar la llamada a la función almacenada
            String sql = "{ ? = call obtener_apellidos(?) }";
            cs = con.prepareCall(sql);

            // Registrar el primer parámetro como salida (para los apellidos)
            cs.registerOutParameter(1, Types.VARCHAR);

            // Establecer el segundo parámetro como el DNI del cliente
            cs.setString(2, dniCliente);

            // Ejecutar la función
            cs.execute();

            // Obtener el valor devuelto por la función (apellidos)
            String apellidos = cs.getString(1);

            // Mostrar los apellidos del cliente
            if (apellidos != null) {
                System.out.println("DNI: " + dniCliente + ", Apellidos: " + apellidos);
            } else {
                System.out.println("No se encontraron apellidos para el DNI: " + dniCliente);
            }

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }

    }
}

//Poner en consola para que funcione

//DELIMITER $$

//CREATE FUNCTION obtener_apellidos(dni_cliente CHAR(9))
//RETURNS VARCHAR(100)
//BEGIN
//DECLARE apellidos_cliente VARCHAR(100);
//
//    -- Selecciona los apellidos del cliente basado en su DNI
//SELECT APELLIDOS INTO apellidos_cliente
//FROM CLIENTES
//WHERE DNI = dni_cliente;
//
//    -- Devuelve los apellidos
//RETURN apellidos_cliente;
//END$$
//
//        DELIMITER ;
