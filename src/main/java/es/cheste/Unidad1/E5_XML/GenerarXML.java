package es.cheste.Unidad1.E5_XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GenerarXML {

    public static void main(String[] args) {
        // Datos proporcionados
        String[] moduls = {
                "Accés a Dades",
                "Programació de serveis i processos",
                "Desenvolupament d'interfícies",
                "Programació Multimèdia i dispositius mòbils",
                "Sistemes de Gestió Empresarial",
                "Empresa i iniciativa emprenedora"
        };
        int[] hores = {6, 3, 6, 5, 5, 3};
        double[] notes = {8.45, 9.0, 8.0, 7.34, 8.2, 7.4};

        // Generar el archivo XML
        generarXML(moduls, hores, notes);
    }

    public static void generarXML(String[] moduls, int[] hores, double[] notes) {
        try {
            // Crear un objeto DocumentBuilder para construir el XML
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Crear el elemento raíz <curs>
            Element root = document.createElement("curs");
            document.appendChild(root);

            // Iterar sobre los datos de los módulos
            for (int i = 0; i < moduls.length; i++) {
                // Crear el elemento <modul> para cada módulo
                Element modulElement = document.createElement("modul");
                root.appendChild(modulElement);

                // Añadir el nombre del módulo
                Element nomElement = document.createElement("nom");
                nomElement.appendChild(document.createTextNode(moduls[i]));
                modulElement.appendChild(nomElement);

                // Añadir las horas del módulo
                Element horesElement = document.createElement("hores");
                horesElement.appendChild(document.createTextNode(String.valueOf(hores[i])));
                modulElement.appendChild(horesElement);

                // Añadir la nota del módulo
                Element notaElement = document.createElement("qualificacio");
                notaElement.appendChild(document.createTextNode(String.valueOf(notes[i])));
                modulElement.appendChild(notaElement);
            }

            // Guardar el XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("src/main/resources/xml/modulos.xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("Archivo XML generado correctamente.");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
