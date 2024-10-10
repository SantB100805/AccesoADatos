package es.cheste.Unidad1.agenda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {

    private List<Contacto> listaContactos;

    public Wrapper() {
        this.listaContactos = new ArrayList<>();
        cargarDesdeJSON("src/main/resources/json/agenda.json");
    }

    public void agregarContacto(Contacto contacto) {
        listaContactos.add(contacto);
    }

    public Contacto buscarContactoPorCampo(String busqueda) {
        for (Contacto contacto : listaContactos) {
            // Aqui comparo por nombre, apellidos, email y teléfonos
            if (contacto.getNombre().equalsIgnoreCase(busqueda) ||
                    contacto.getApellidos().equalsIgnoreCase(busqueda) ||
                    contacto.getEmail().equalsIgnoreCase(busqueda) ||
                    contacto.getTelefono1().equals(busqueda) ||
                    contacto.getTelefono2().equals(busqueda)) {
                return contacto; // Devuelve el contacto si hay coincidencia
            }
        }
        return null; // Retorna null si no encuentro el contacto
    }


    public void eliminarContacto(Contacto contacto) {
        listaContactos.remove(contacto);
    }


    public List<Contacto> getListaContactos() {
        return listaContactos;
    }
    public void guardarAgenda(String archivoCSV, String archivoXML, String archivoJSON) {
        guardarEnCSV(archivoCSV, listaContactos);
        guardarEnXML(archivoXML, listaContactos);
        guardarEnJSON(archivoJSON, listaContactos);
    }

    //Guardar archivos
    public void guardarEnCSV(String archivo, List<Contacto> listaContactos) {
        try (PrintWriter writer = new PrintWriter(archivo)) {
            for (Contacto contacto : listaContactos) {
                String linea = String.join(";",
                        contacto.getNombre(),
                        contacto.getApellidos(),
                        contacto.getEmail(),
                        contacto.getTelefono1(),
                        contacto.getTelefono2(),
                        contacto.getDireccion());
                writer.println(linea);
            }
            System.out.println("Agenda guardada en formato CSV.");
        } catch (FileNotFoundException e) {
            System.err.println("Error al guardar el archivo CSV: " + e.getMessage());
        }

    }
    public void cargarDesdeCSV(String archivo) {
        if (Files.exists(Paths.get(archivo))) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(";");
                    if (datos.length >= 6) {
                        Contacto contacto = new Contacto(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                        listaContactos.add(contacto);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            }
        }
    }

    public void guardarEnXML(String archivo, List<Contacto> listaContactos) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("contactos");
            doc.appendChild(rootElement);

            for (Contacto contacto : listaContactos) {
                Element contactoElement = doc.createElement("contacto");

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(contacto.getNombre()));
                contactoElement.appendChild(nombre);

                Element apellidos = doc.createElement("apellidos");
                apellidos.appendChild(doc.createTextNode(contacto.getApellidos()));
                contactoElement.appendChild(apellidos);

                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(contacto.getEmail()));
                contactoElement.appendChild(email);

                Element telefono1 = doc.createElement("telefono1");
                telefono1.appendChild(doc.createTextNode(contacto.getTelefono1()));
                contactoElement.appendChild(telefono1);

                Element telefono2 = doc.createElement("telefono2");
                telefono2.appendChild(doc.createTextNode(contacto.getTelefono2()));
                contactoElement.appendChild(telefono2);

                Element direccion = doc.createElement("direccion");
                direccion.appendChild(doc.createTextNode(contacto.getDireccion()));
                contactoElement.appendChild(direccion);

                rootElement.appendChild(contactoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivo));
            transformer.transform(source, result);

            System.out.println("Agenda guardada en formato XML.");
        } catch (Exception e) {
            System.err.println("Error al guardar el archivo XML: " + e.getMessage());
        }
    }

    public void cargarDesdeXML(String archivo) {
        if (Files.exists(Paths.get(archivo))) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(archivo));
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("contacto");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node nodo = nodeList.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) { //Importante por que trabaja con elementos y puede que procese como elemento un nodo como el comentario
                        Element elemento = (Element) nodo;
                        String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                        String apellidos = elemento.getElementsByTagName("apellidos").item(0).getTextContent();
                        String email = elemento.getElementsByTagName("email").item(0).getTextContent();
                        String telefono1 = elemento.getElementsByTagName("telefono1").item(0).getTextContent();
                        String telefono2 = elemento.getElementsByTagName("telefono2").item(0).getTextContent();
                        String direccion = elemento.getElementsByTagName("direccion").item(0).getTextContent();
                        Contacto nuevoContacto = new Contacto(nombre, apellidos, email, telefono1, telefono2, direccion);
                        // Verificar si el contacto ya existe antes de agregarlo
                        if (!listaContactos.contains(nuevoContacto)) {
                            listaContactos.add(nuevoContacto);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al leer el archivo XML: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo XML no existe: " + archivo);
        }
    }

    public void guardarEnJSON(String archivo, List<Contacto> listaContactos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(listaContactos, writer);
            System.out.println("Agenda guardada en formato JSON.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
    }

    public void cargarDesdeJSON(String archivo) {
        if (Files.exists(Paths.get(archivo))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileReader reader = new FileReader(archivo)) {

                // Deserializamos directamente una lista de contactos
                Type contactoListType = new TypeToken<List<Contacto>>() {}.getType();
                List<Contacto> contactosDesdeJSON = gson.fromJson(reader, contactoListType);

                // Si la lista no es nula, agregamos los contactos a la lista evitando duplicados
                if (contactosDesdeJSON != null) {
                    for (Contacto contacto : contactosDesdeJSON) {
                        if (!listaContactos.contains(contacto)) {
                            listaContactos.add(contacto);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo JSON no existe: " + archivo);
        }
    }


}

