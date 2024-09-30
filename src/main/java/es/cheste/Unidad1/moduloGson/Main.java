package es.cheste.Unidad1.moduloGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Leer el archivo JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileReader reader = new FileReader("src/main/resources/json/SW.json");
            Wrapper wrapper = gson.fromJson(reader, Wrapper.class);
            reader.close();

            List<Personaje> personajes = wrapper.getPersonatges();

            // Aqui filtro los personajes que no tienen vehiculos
            List<Personaje> sinVehiculos = new ArrayList<>();
            for (Personaje p : personajes) {
                if (p.getVehicles().isEmpty()) {
                    sinVehiculos.add(p);
                }
            }

            // Aqui muestro lo de arriba
            System.out.println("Personajes que no condujeron vehículos:");
            for (Personaje p : sinVehiculos) {
                System.out.println(p.getName());
            }

            // Ordeno los personajes
            Collections.sort(personajes);// Ordenar los personajes

            // Mostrar los personajes ordenados
            System.out.println("\nPersonajes ordenados por número de películas:");
            for (Personaje p : personajes) {
                System.out.println(p.getName() + ": " + p.getFilms().size() + " películas");
            }

            // Crear el archivo XML
            FileWriter xmlWriter = new FileWriter("src/main/resources/xml/resumen_pelicula.xml");
            xmlWriter.write("<characters>\n");
            for (Personaje p : personajes) {
                xmlWriter.write("\t<character films=\"" + p.getFilms().size() + "\" vehicles=\"" + p.getVehicles().size() + "\">\n");
                xmlWriter.write("\t\t<name>" + p.getName() + "</name>\n");
                xmlWriter.write("\t\t<mass>" + p.getMass() + "</mass>\n");
                xmlWriter.write("\t\t<url>" + p.getUrl() + "</url>\n");
                xmlWriter.write("\t</character>\n");
            }

            xmlWriter.write("</characters>");
            xmlWriter.close();

            System.out.println("\nArchivo XML creado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
