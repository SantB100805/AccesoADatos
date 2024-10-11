package es.cheste.Unidad1.basesDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_cp_conGetInt {
    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        hacerSelect(conexion);
        conexion.desconectar();
    }

    public static void hacerSelect(ConexionBD conexion) {
        int i = 1;

        try (Statement stmt = conexion.getConnection().createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT CP FROM CLIENTES");

            while (rs.next()) {
                String cpString = rs.getString("CP");  // Se obtiene el valor de CP como String en cada iteración
                if (cpString == null || cpString.isEmpty()) {
                    System.out.println("Fila número " + (i++) + ": No se puede obtener como getInt (valor nulo o vacío)");
                } else {
                    try {
                        int cpInt = rs.getInt("CP");  // Intentar convertir CP a entero
                        System.out.println("Fila número " + (i++) + ": CP (como entero): " + cpInt);
                    } catch (SQLException e) {
                        System.out.println("Fila número " + (i++) + ": Error al recuperar CP como entero: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

        public static void mostrarResultadosInverso() {

        }
    }


