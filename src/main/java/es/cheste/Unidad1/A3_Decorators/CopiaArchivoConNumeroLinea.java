package es.cheste.Unidad1.A3_Decorators;

import java.io.*;

public class CopiaArchivoConNumeroLinea {

    public static void main(String[] args) {

        /*
            BufferedReader: Se utiliza para leer el archivo línea por línea de manera eficiente.
            BufferedWriter: Se utiliza para escribir en el archivo de salida.
            FileReader y FileWriter: Se utilizan para leer y escribir archivos.
        */

        String file_Entrada = "src/main/resources/txt/entrada.txt";
        String file_Salida = "src/main/resources/txt/salida.txt";

        // Llamada al método estático correctamente
        copiarArchivoConNumeroLinea(file_Entrada, file_Salida);
    }

    // Cambiar el nombre del método para evitar confusión
    public static void copiarArchivoConNumeroLinea(String entrada, String salida) {
        BufferedReader lector = null;
        BufferedWriter escritor = null;

        try {
            // Crear un lector de archivos con BufferedReader para lectura eficiente
            lector = new BufferedReader(new FileReader(entrada));

            // Crear un escritor de archivos con BufferedWriter para escritura eficiente
            escritor = new BufferedWriter(new FileWriter(salida));

            String linea;
            int numeroLinea = 1; // Contador para el número de línea

            // Leer el archivo línea por línea
            while ((linea = lector.readLine()) != null) {

                // Escribir el número de línea seguido de la línea original en el archivo de salida
                escritor.write(numeroLinea + ": " + linea);
                escritor.newLine(); // Salto de línea en el archivo de salida
                numeroLinea++;
            }

            System.out.println("Archivo copiado con números de línea correctamente.");

        } catch (IOException e) {
            // Captura y manejo de excepciones por posibles errores de E/S
            System.err.println("Error al copiar el archivo: " + e.getMessage());

        } finally {
            // Cerrar los flujos de entrada y salida de manera segura
            try {
                if (lector != null) lector.close();
                if (escritor != null) escritor.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar los archivos: " + e.getMessage());
            }
        }
    }
}
