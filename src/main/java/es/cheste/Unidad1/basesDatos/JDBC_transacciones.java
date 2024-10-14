package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class JDBC_transacciones {

    public static void main(String[] args) {

        //se omite declaracion de variables para los datos de conexion

        ConexionBD conexion = new ConexionBD();
        transaccion(conexion);
        conexion.desconectar();
    }

    public static  void transaccion(ConexionBD conexion){

        Connection con = null;

        try {
            con = conexion.getConnection();
            con.setAutoCommit(false); // Desactivar auto-commit

            PreparedStatement sInsert = con.prepareStatement("INSERT INTO CLIENTES1(DNI, APELLIDOS, CP) VALUES (?, ?, ?)");


            // Primera inserción
            int i = 1;  // Los índices deben empezar en 1 osino da erro al empezar en 0
            sInsert.setString(i++, "54320198V");
            sInsert.setString(i++, "CARVAJAL");
            sInsert.setString(i++, "10109");
            sInsert.executeUpdate();

            // Segunda inserción
            i = 1;  // Reiniciar el índice para la siguiente inserción
            sInsert.setString(i++, "76543210S");
            sInsert.setString(i++, "MARQUEZ");
            sInsert.setString(i++, "46987");
            sInsert.executeUpdate();

            // Tercera inserción
            i = 1;
            sInsert.setString(i++, "90123456A");
            sInsert.setString(i++, "MOLINA");
            sInsert.setString(i++, "35153");
            sInsert.executeUpdate();

            con.commit(); // Hacer commit de la transacción

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());

            try {
                con.rollback();  // Hacer rollback en caso de error
                System.err.println("Se hace ROLLBACK");
            }catch (SQLException er){
                System.err.println("Error al ejecutar la rollback: " + er.getMessage());
            }

        } catch (Exception e) {
            System.err.println("ERROR de conexion");
            e.printStackTrace(System.err);
        }

    }
}
