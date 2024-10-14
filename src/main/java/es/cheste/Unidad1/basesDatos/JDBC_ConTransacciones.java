package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_ConTransacciones {

    public static void main(String[] args) {

            ConexionBD conexion = new ConexionBD();
            transaccion(conexion);
            conexion.desconectar();
    }

    public static void transaccion(ConexionBD conexion) {
            Connection con = null;

            try {
                con = conexion.getConnection();
                con.setAutoCommit(false);  // Iniciar transacción

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

                con.commit();  // Commit de la transacción si todas las inserciones son exitosas

            } catch (SQLException e) {
                System.err.println("Error en la inserción: " + e.getMessage());
                try {
                    if (con != null) {
                        con.rollback();  // Hacer rollback si hay un error
                        System.out.println("Transacción revertida.");
                    }
                } catch (SQLException rollbackEx) {
                    System.err.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
        }
    }

