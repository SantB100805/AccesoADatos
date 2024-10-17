package es.cheste.Unidad1.basesDatos;

import java.sql.*;

public class JDBC_callable_statement {
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        callable(conexion);
        conexion.desconectar();

    }

    public static void callable (ConexionBD conexion) {
        try {
            Connection con = conexion.getConnection();
            CallableStatement s = con.prepareCall("(call listado_parcial_clientes(?,?)}");
            s.setString(1,"78901234x");
            s.setInt(2,0);
            s.registerOutParameter(2, Types.INTEGER);
            s.execute();

            ResultSet rs = s.getResultSet();

            int inout_long = s.getInt(2);
            System.out.println("=> inout long: " + inout_long);
            int nCli = 0;

            while (rs.next()) {
                System.out.println("[" + (++nCli) + "] ");
                System.out.println("DNI:" + rs.getString("DNI"));
                System.out.println("APELLIDOS:" + rs.getString("APELLIDOS"));
            }
        }catch (SQLException e) {
            System.out.println("Error  SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
