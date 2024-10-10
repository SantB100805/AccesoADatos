package es.cheste.Unidad1.agenda;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wrapper agenda = new Wrapper();

        // Seleccionar idioma
        ResourceBundle bundle = seleccionarIdioma(scanner);
        escogerOpcion(agenda, scanner, bundle);

    }

    // Aqui hago un metodo para Seleccionar el idioma
    public static ResourceBundle seleccionarIdioma(Scanner scanner) {
        System.out.println("Seleccione un idioma: 1. Inglés  2. Español  3. Valenciano ");
        int opcionIdioma = scanner.nextInt();
        scanner.nextLine();

        Locale idiomaSeleccionado;
        switch (opcionIdioma) {
            case 1:
                idiomaSeleccionado = new Locale("en");
                break;
            case 2:
                idiomaSeleccionado = new Locale("es");
                break;
            case 3:
                idiomaSeleccionado = new Locale("ca");
                break;
            default:
                System.out.println("Opción no válida, se seleccionará español por defecto.");
                idiomaSeleccionado = new Locale("es");
        }

        return ResourceBundle.getBundle("agendaLeng", idiomaSeleccionado);
    }

    // Aqui muestro el menu en el idioma
    public static void mostrarMenu(ResourceBundle bundle) {
        System.out.println(bundle.getString("form.titulo"));
        System.out.println(bundle.getString("form.contenido"));

        System.out.println("1. " + bundle.getString("menu.agregar"));
        System.out.println("2. " + bundle.getString("menu.modificar"));
        System.out.println("3. " + bundle.getString("menu.eliminar"));
        System.out.println("4. " + bundle.getString("menu.buscar"));
        System.out.println("5. " + bundle.getString("menu.mostrar"));
        System.out.println("6. " + bundle.getString("menu.salir"));
    }

    // metodo agregar contacto
    public static void agregarContacto(Wrapper agenda, Scanner scanner, ResourceBundle bundle) {
        System.out.println(bundle.getString("contacto.nombre"));
        String nombre = scanner.nextLine();

        System.out.println(bundle.getString("contacto.apellidos"));
        String apellidos = scanner.nextLine();

        System.out.println(bundle.getString("contacto.email"));
        String email = scanner.nextLine();

        System.out.println(bundle.getString("contacto.telefono1"));
        String telefono1 = scanner.nextLine();

        System.out.println(bundle.getString("contacto.telefono2"));
        String telefono2 = scanner.nextLine();

        System.out.println(bundle.getString("contacto.direccion"));
        String direccion = scanner.nextLine();

        // Crear un objeto Contacto y agregarlo a la agenda
        Contacto contacto = new Contacto(nombre, apellidos, email, telefono1, telefono2, direccion);
        agenda.agregarContacto(contacto);
    }


    public static void escogerOpcion(Wrapper agenda, Scanner scanner, ResourceBundle bundle) {
        boolean salir = false;
        while (!salir) {

            mostrarMenu(bundle);
            System.out.println(bundle.getString("form.contenido"));

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarContacto(agenda, scanner, bundle);
                    break;
                case 2:
                    modificarContacto(agenda, scanner, bundle);
                    break;
                case 3:
                    eliminarContacto(agenda, scanner, bundle);
                    break;
                case 4:
                    buscarContacto(agenda, scanner, bundle);
                    break;
                case 5:
                    mostrarContactos(agenda, bundle); // Opción para mostrar contactos
                    break;
                case 6:
                    System.out.println(bundle.getString("menu.salir"));
                    agenda.guardarAgenda("src/main/resources/csv/agenda.csv", "src/main/resources/xml/agenda.xml", "src/main/resources/json/agenda.json");
                    salir = true;
                    break;
                default:
                    System.out.println(bundle.getString("menu.opcionInvalida"));
            }
        }
    }
    public static void modificarContacto(Wrapper agenda, Scanner scanner, ResourceBundle bundle) {
        // Pedir un dato para buscar el contacto
        System.out.println(bundle.getString("contacto.campoBuscar"));
        String campoBusqueda = scanner.nextLine();

        //Metodo buscar a la persona
        Contacto contacto = agenda.buscarContactoPorCampo(campoBusqueda);

        // Si el contacto no existe, mostrar un mensaje
        if (contacto == null) {
            System.out.println(bundle.getString("contacto.noEncontrado"));
            return;
        }

        // Mostrar los datos actuales del contacto
        mostrarDatos(contacto,bundle);

        // Preguntar si desea modificar cada campo y actualizar si es necesario
        System.out.println(bundle.getString("contacto.modificarNombre"));
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()) {
            contacto.setNombre(nuevoNombre);
        }

        System.out.println(bundle.getString("contacto.modificarApellidos"));
        String nuevosApellidos = scanner.nextLine();
        if (!nuevosApellidos.isEmpty()) {
            contacto.setApellidos(nuevosApellidos);
        }

        System.out.println(bundle.getString("contacto.modificarEmail"));
        String nuevoEmail = scanner.nextLine();
        if (!nuevoEmail.isEmpty()) {
            contacto.setEmail(nuevoEmail);
        }

        System.out.println(bundle.getString("contacto.modificarTelefono1"));
        String nuevoTelefono1 = scanner.nextLine();
        if (!nuevoTelefono1.isEmpty()) {
            contacto.setTelefono1(nuevoTelefono1);
        }

        System.out.println(bundle.getString("contacto.modificarTelefono2"));
        String nuevoTelefono2 = scanner.nextLine();
        if (!nuevoTelefono2.isEmpty()) {
            contacto.setTelefono2(nuevoTelefono2);
        }

        System.out.println(bundle.getString("contacto.modificarDireccion"));
        String nuevaDireccion = scanner.nextLine();
        if (!nuevaDireccion.isEmpty()) {
            contacto.setDireccion(nuevaDireccion);
        }

        // Confirmar que el contacto ha sido modificado
        System.out.println(bundle.getString("contacto.modificado"));
    }

    public static void eliminarContacto(Wrapper agenda, Scanner scanner, ResourceBundle bundle) {
        System.out.println(bundle.getString("contacto.campoBuscar"));

        String campoBusqueda = scanner.nextLine();

        Contacto contacto = agenda.buscarContactoPorCampo(campoBusqueda);

        // Si el contacto no existe, mostrar un mensaje
        if (contacto == null) {
            System.out.println(bundle.getString("contacto.noEncontrado"));
            return;
        }

        // Confirmar eliminación
        System.out.println(bundle.getString("contacto.confirmarEliminacion") + " " + contacto.getNombre() + "? (s/n)");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            agenda.eliminarContacto(contacto);
            System.out.println(bundle.getString("contacto.eliminado"));
        } else {
            System.out.println(bundle.getString("contacto.eliminacionCancelada"));
        }
    }

    public static void buscarContacto(Wrapper agenda, Scanner scanner, ResourceBundle bundle) {
        // Mensaje que indica que se puede buscar por cualquier campo
        System.out.println(bundle.getString("contacto.campoBuscar"));
        String campoBusqueda = scanner.nextLine();

        // Buscar a la persona
        Contacto contacto = agenda.buscarContactoPorCampo(campoBusqueda);

        // Verificar si el contacto fue encontrado
        if (contacto == null) {
            System.out.println(bundle.getString("contacto.noEncontrado"));
            return;
        }

        mostrarDatos(contacto, bundle);
    }

    public static void mostrarDatos(Contacto contacto, ResourceBundle bundle) {

        System.out.println(bundle.getString("contacto.nombre") + ": " + contacto.getNombre());
        System.out.println(bundle.getString("contacto.apellidos") + ": " + contacto.getApellidos());
        System.out.println(bundle.getString("contacto.email") + ": " + contacto.getEmail());
        System.out.println(bundle.getString("contacto.telefono1") + ": " + contacto.getTelefono1());
        System.out.println(bundle.getString("contacto.telefono2") + ": " + contacto.getTelefono2());
        System.out.println(bundle.getString("contacto.direccion") + ": " + contacto.getDireccion());
    }

    public static void mostrarContactos(Wrapper agenda, ResourceBundle bundle) {
        // Obtener la lista de contactos
        List<Contacto> contactos = agenda.getListaContactos();

        // Verificar si la lista está vacía
        if (contactos.isEmpty()) {
            System.out.println(bundle.getString("contacto.sinContactos")); // Mensaje de que no hay contactos
            return;
        }

        // Ordenar los contactos por apellidos
        contactos.sort(null); // Aqui utilizo el compareTo() de Contacto se pone null para obligar a organizar en eso

        // Mostrar todos los contactos ordenados
        System.out.println(bundle.getString("contacto.listaContactos"));
        for (Contacto contacto : contactos) {
            mostrarDatos(contacto, bundle);
            System.out.println("-------------------");
        }
    }



}
