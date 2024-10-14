package es.cheste.Unidad1.basesDatos;

import java.sql.*;

public class JDBC_prepared_statement {

    public static void main(String[] args) {

        //se omite declaracion de variables para los datos de conexion

        ConexionBD conexion = new ConexionBD();
        insertarDatosConSentenciasPreparadas(conexion);
        conexion.desconectar();
    }

    public static void insertarDatosConSentenciasPreparadas(ConexionBD conexion) {
        try (Connection con = conexion.getConnection()) {

            PreparedStatement sInsert = con.prepareStatement("INSERT INTO CLIENTES1(DNI,APELLIDOS,CP) VALUES(?,?,?)");
            sInsert.setString(1,"78901234x");
            sInsert.setString(2,"NADALES");
            sInsert.setInt(3,44126);
            sInsert.executeUpdate();
            int i = 1;
            sInsert.setString(i++, "89012345E");
            sInsert.setString(i++, "ROJAS");
            sInsert.setNull(i++, Types.INTEGER);
            sInsert.executeUpdate();
            i=1;
            sInsert.setString(i++, "56789012B");
            sInsert.setString(i++, "SAMPER");
            sInsert.setInt(i++,29730);
            sInsert.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
