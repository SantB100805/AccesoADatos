package es.cheste.Unidad1.basesDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_claves_autogeneradas {
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        autoGeneradas(conexion);
        conexion.desconectar();
    }

    public static void autoGeneradas(ConexionBD conexion) {
        try {
            Connection con = conexion.getConnection();
            try {
                // Preparar la sentencia SQL para insertar en la tabla FACTURAS
                // PreparedStatement.RETURN_GENERATED_KEYS permite obtener las claves autogeneradas después de la inserción
                PreparedStatement sInsertFact = con.prepareStatement("INSERT INTO FACTURAS(DNI_CLIENTE) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);

                // Preparar la sentencia SQL para insertar en la tabla LINEAS_FACTURA
                PreparedStatement  sInsertLineaFact = con.prepareStatement(
                        "INSERT INTO LINEAS_FACTURA(NUM_FACTURA, LINEA_FACTURA, CONCEPTO, CANTIDAD) VALUES (?,?,?,?);"
                );

                // Se inicia una transacción
                con.setAutoCommit(false);

                int i = 1;

                // Insertar un nuevo cliente en FACTURAS
                sInsertFact.setString(i++, "78901234x");
                sInsertFact.executeUpdate();

                // Obtener la clave autogenerada (número de factura)
                ResultSet rs = sInsertFact.getGeneratedKeys(); // Este ResultSet contiene las claves generadas
                rs.next(); // Avanzar a la primera fila (la única clave generada en este caso)

                // Obtener el número de factura generado por la base de datos
                int numFact = rs.getInt(1); // Obtener la clave autogenerada (número de factura)
                int lineaFact = 1; // Línea de factura (primer ítem)

                // Insertar la primera línea de la factura (concepto: TUERCAS)
                i = 1;
                sInsertLineaFact.setInt(i++, numFact); // Asociar la línea de factura con el número de factura autogenerado
                sInsertLineaFact.setInt(i++, lineaFact++); // Número de línea
                sInsertLineaFact.setString(i++, "TUERCAS"); // Concepto de la línea
                sInsertLineaFact.setInt(i++, 25); // Cantidad
                sInsertLineaFact.executeUpdate();

                // Insertar la segunda línea de la factura (concepto: TORNILLOS)
                i = 1;
                sInsertLineaFact.setInt(i++, numFact); // De nuevo, usar el número de factura generado
                sInsertLineaFact.setInt(i++, lineaFact++); // Siguiente número de línea
                sInsertLineaFact.setString(i++, "TORNILLOS"); // Concepto de la línea
                sInsertLineaFact.setInt(i++, 250); // Cantidad
                sInsertLineaFact.executeUpdate();

                // Confirmar la transacción
                con.commit();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
                try {
                    con.rollback(); // Si ocurre un error, deshacer la transacción (rollback)
                    System.err.println("Se hace ROLLBACK");
                } catch (Exception er) {
                    System.err.println("ERROR haciendo rollback");
                    er.printStackTrace(System.err);
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR de conexion");
            e.printStackTrace(System.err);
        }
    }
}
