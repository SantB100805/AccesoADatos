package es.cheste.Unidad1.A5_Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // Ruta del archivo de propiedades
        String filePath = "src/main/resources/config.properties";

        leerPWithInputStream(filePath);

        // Escribir una propiedad de ejemplo
        escribirPropiedades(filePath, "name", "MiAplicacion");
        leerPWithInputStream(filePath); // Leer de nuevo para ver el cambio
    }

    public static void leerPWithInputStream(String file) {
        Properties prop = new Properties();

        // Permite leer archivos que proceden del sistema de archivos
        try (FileInputStream input = new FileInputStream(file)) {
            prop.load(input);
            System.out.println("Propiedades leídas con FileInputStream:");
            for (Object key : prop.keySet()) {
                System.out.println(key + " = " + prop.getProperty(key.toString()));
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void escribirPropiedades(String filePath, String key, String value) {
        // Creamos un objeto Properties para manejar las propiedades
        Properties propiedades = new Properties();

        // Intentamos cargar las propiedades existentes desde el archivo
        try (FileInputStream input = new FileInputStream(filePath)) {
            propiedades.load(input); // Cargamos las propiedades
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo. Se creará uno nuevo.");
        }

        // Añadimos o modificamos la propiedad
        propiedades.setProperty(key, value); // Establecemos la nueva propiedad

        // Intentamos guardar las propiedades en el archivo
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            propiedades.store(output, "Propiedad añadida/modificada"); // Guardamos las propiedades
            System.out.println("Propiedad guardada: " + key + " = " + value);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }
}
