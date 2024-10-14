package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_SinTransacciones {

    public static void main(String[] args) {

        ConexionBD conexion = new ConexionBD();
        inserciones(conexion);
        conexion.desconectar();
    }

    public static void inserciones(ConexionBD conexion) {
        Connection con = null;

        try {
            con = conexion.getConnection();

            PreparedStatement sInsert = con.prepareStatement("INSERT INTO CLIENTES1 (DNI, APELLIDOS, CP) VALUES (?, ?, ?)");

            // Primera inserción
            sInsert.setString(1, "12345678A");
            sInsert.setString(2, "PEREZ");
            sInsert.setString(3, "28001");
            sInsert.executeUpdate();

            // Segunda inserción
            sInsert.setString(1, "87654321B");
            sInsert.setString(2, "GARCIA");
            sInsert.setString(3, "28002");
            sInsert.executeUpdate();

            // Tercera inserción (falla por DNI duplicado)
            sInsert.setString(1, "12345678A");  // DNI duplicado
            sInsert.setString(2, "LOPEZ");
            sInsert.setString(3, "28003");
            sInsert.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en la inserción: " + e.getMessage());
        }
    }
}
/*

Diferencias clave entre los dos programas:
Con transacciones: Si ocurre un error (como un DNI duplicado),
se hace un rollback y ninguna de las inserciones se ejecuta, garantizando que no se ejecute la transacción.

Sin transacciones: Si ocurre un error, solo las inserciones previas al error se mantienen,
y las posteriores no se ejecutan.

Con transacciones: Si hay un error en la tercera inserción, ninguna fila es insertada.
Sin transacciones: Si hay un error en la tercera inserción, las primeras dos filas serán insertadas, pero la última no.
*/