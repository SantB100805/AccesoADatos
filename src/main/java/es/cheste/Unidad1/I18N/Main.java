package es.cheste.Unidad1.I18N;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) {
        Locale en = new Locale("en");
        Locale es = new Locale("es");
        Locale ca = new Locale("ca");

        cambiarIdioma(en);
        cambiarIdioma(es);
        cambiarIdioma(ca);
    }

    public static void cambiarIdioma(Locale idiomaActual) {
        ResourceBundle bundle = ResourceBundle.getBundle("strings", idiomaActual);

        System.out.println(bundle.getString("form.titulo"));
        System.out.println(bundle.getString("form.contenido"));

        //Usa ResourceBundle.getBundle("strings", idiomaActual);
        // , y ResourceBundle automáticamente buscará los archivos correctos en el classpath según el Locale especificado.

    }
}