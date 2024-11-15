package sistema.colegio.eduxsystem.Clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String nombre;
    public String apellido;
    public String rol;
    public String dni;
    public String celular;
    public String email;
    public String direccion;
    public String user;
    public String password;

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String apellido, String rol, String dni, String celular, String email, String direccion, String user, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.dni = dni;
        this.celular = celular;
        this.email = email;
        this.direccion = direccion;
        this.user = user;
        this.password = password;
    }



    // Getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getRol() {
        return rol;
    }

    public String getDni() {
        return dni;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}


