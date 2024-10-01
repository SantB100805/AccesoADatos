package es.cheste.Unidad1.agenda;

import java.util.Objects;

public class Contacto implements Comparable<Contacto>{
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono1;
    private String telefono2;
    private String direccion;

    // Constructor
    public Contacto(String nombre, String apellidos, String email, String telefono1, String telefono2, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono1() { return telefono1; }
    public void setTelefono1(String telefono1) { this.telefono1 = telefono1; }

    public String getTelefono2() { return telefono2; }
    public void setTelefono2(String telefono2) { this.telefono2 = telefono2; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return nombre + " " + apellidos + ", Email: " + email + ", Tel1: " + telefono1 + ", Tel2: " + telefono2 + ", Dirección: " + direccion;
    }

    @Override
    public int compareTo(Contacto otroApellido) {
        return  this.apellidos.compareToIgnoreCase(otroApellido.apellidos);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacto)) return false;
        Contacto contacto = (Contacto) o;
        return nombre.equals(contacto.nombre) &&
                apellidos.equals(contacto.apellidos) &&
                email.equals(contacto.email) &&
                telefono1.equals(contacto.telefono1) &&
                telefono2.equals(contacto.telefono2) &&
                direccion.equals(contacto.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, email, telefono1, telefono2, direccion);
    }
}
