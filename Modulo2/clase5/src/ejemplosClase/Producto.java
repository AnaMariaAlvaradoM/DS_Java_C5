package ejemplosClase;

public class Producto {
    String nombre;
    double precio;

    Producto() {
        this.nombre = "Sin nombre";
        this.precio = 0.0;
    }

    Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    void mostrar() {
        System.out.println(nombre + " -> $" + precio);
    }
}