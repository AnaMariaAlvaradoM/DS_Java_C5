package cohorte4Java.Java_Prep.model;

public class Libro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Double precio;

    public Libro() {
    }

    public Libro(Long id, String titulo, String autor, String isbn, Double precio) {
        this.id     = id;
        this.titulo = titulo;
        this.autor  = autor;
        this.isbn   = isbn;
        this.precio = precio;
    }

    public Long   getId()     { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor()  { return autor; }
    public String getIsbn()   { return isbn; }
    public Double getPrecio() { return precio; }


    public void setId(Long id)         { this.id     = id; }
    public void setTitulo(String t)    { this.titulo = t; }
    public void setAutor(String a)     { this.autor  = a; }
    public void setIsbn(String i)      { this.isbn   = i; }
    public void setPrecio(Double p)    { this.precio = p; }

    @Override
    public String toString() {
        return "Libro{id=" + id +
               ", titulo='" + titulo + '\'' +
               ", autor='" + autor + '\'' +
               ", isbn='" + isbn + '\'' +
               ", precio=" + precio + '}';
    }
}
