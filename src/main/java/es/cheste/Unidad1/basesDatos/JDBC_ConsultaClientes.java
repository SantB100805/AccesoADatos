package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_ConsultaClientes {
    public static void main(String[] args) {

        ConexionBD conexion = new ConexionBD();

        // Lista de DNIs a consultar
        String[] dnis = {"54320198V", "76543210S", "90123456A"};

        for (String dni : dnis) {
            mostrarDatosCliente(conexion, dni);
        }

        conexion.desconectar();
    }

    public static void mostrarDatosCliente(ConexionBD conexion,String dniCliente){

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = conexion.getConnection();

            // Preparar la consulta SQL
            String sql = "SELECT * FROM CLIENTES1 WHERE DNI = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dniCliente);  // Sustituir el parámetro de la consulta por el DNI

            // Ejecutar la consulta
            rs = pstmt.executeQuery();

            // Verificar si hay un resultado
            if (rs.next()) {
                // Obtener los datos del cliente desde el ResultSet
                String dni = rs.getString("DNI");
                String apellidos = rs.getString("APELLIDOS");
                String cp = rs.getString("CP");

                // Mostrar los datos del cliente
                System.out.println("DNI: " + dni + ", Apellidos: " + apellidos + ", CP: " + cp);
            } else {
                System.out.println("No se encontró ningún cliente con DNI: " + dniCliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta: " + e.getMessage());
        } finally {
            try {
                // Cerrar el ResultSet, PreparedStatement y la conexión
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
