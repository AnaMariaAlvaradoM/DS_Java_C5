package cohorte4Java.Java_Prep.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Autor {
    private Long idAutor;


    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(min=2,max = 100, message = "El nombre debe estar entre 2 a 100 caracteres")
    private String nombre;

    @NotBlank(message = "Es obligatorio poner la nacionalidad")
    private String nacionalidad;

    @Email(message = "Debe contener un formato valido")
    @NotBlank(message = "El emial es obligatorio")
    private String email;

    public Autor() {
    }

    public Autor(Long idAutor, String nombre, String nacionalidad, String email) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.email = email;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
