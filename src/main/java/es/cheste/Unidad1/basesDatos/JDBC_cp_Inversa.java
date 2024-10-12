package es.cheste.Unidad1.basesDatos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBC_cp_Inversa {
    private static final Logger LOGGER = LogManager.getRootLogger();

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        List<String> resultados = hacerSelect(conexion);
        mostrarResultadosInverso(resultados);
        conexion.desconectar();
    }

    public static List<String> hacerSelect(ConexionBD conexion) {
        List<String> resultados = new ArrayList<>();  // Lista para almacenar los resultados
        int i = 1;

        try (Statement stmt = conexion.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT CP FROM CLIENTES");

            while (rs.next()) {
                String cpString = rs.getString("CP");  // Se obtiene el valor de CP como String en cada iteración
                if (cpString == null || cpString.isEmpty()) {
                    resultados.add("Fila número " + (i++) + ": No se puede obtener como getInt (valor nulo o vacío)");
                } else {
                    try {
                        int cpInt = rs.getInt("CP");  // Intentar convertir CP a entero
                        resultados.add("Fila número " + (i++) + ": CP (como entero): " + cpInt);
                    } catch (SQLException e) {
                        resultados.add("Fila número " + (i++) + ": Error al recuperar CP como entero: " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }

        return resultados;  // Devuelve la lista con los resultados
    }

    public static void mostrarResultadosInverso(List<String> resultados) {
        // Recorre la lista de resultados en orden inverso
        for (int i = resultados.size() - 1; i >= 0; i--) {
            System.out.println(resultados.get(i));
        }
    }
}

