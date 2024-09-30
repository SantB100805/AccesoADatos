package es.cheste.Unidad1.E5_XML;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class LeerXML {

    public static void main(String[] args) {
        // Especificar la ruta del archivo XML
        File archivoXML = new File("src/main/resources/xml/modulos.xml");

        // Leer y mostrar el contenido del archivo XML
        leerYMostrarXML(archivoXML);
    }

    public static void leerYMostrarXML(File archivoXML) {
        try {
            // Crear un objeto DocumentBuilder para leer el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivoXML);

            // Obtener una lista de todos los elementos <modul>
            NodeList moduls = document.getElementsByTagName("modul");

            // Iterar sobre los módulos y mostrar su contenido formateado
            for (int i = 0; i < moduls.getLength(); i++) {
                String nom = document.getElementsByTagName("nom").item(i).getTextContent();
                String hores = document.getElementsByTagName("hores").item(i).getTextContent();
                String nota = document.getElementsByTagName("qualificacio").item(i).getTextContent();

                System.out.println("Módulo: " + nom);
                System.out.println("Horas: " + hores);
                System.out.println("Nota: " + nota);
                System.out.println("-----------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
