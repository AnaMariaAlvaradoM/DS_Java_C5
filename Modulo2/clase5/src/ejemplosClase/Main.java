package ejemplosClase;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Hello, World!");
//
//        ejemplosClase.Perro miPerro = new ejemplosClase.Perro();
//        miPerro.nombre = "Firulais";
//        miPerro.raza = "Labrador";
//        miPerro.ladrar();
//
//        ejemplosClase.Perro otroPerro = new ejemplosClase.Perro();
//        otroPerro.nombre = "Rocky";
//        otroPerro.ladrar();
        //! ejemeplo libro

        Libro libro = new Libro();
//        libro.titulo = "Clean Code";
//        libro.autor = "Robert Martin";
//        libro.paginas = 464;
//        System.out.println(libro.descripcion());
//        libro.descripcion();


        libro.setTitulo("El principito");
        System.out.println(libro.getTitulo());
        libro.setAutor("No recuerdo nombe");
        System.out.println(libro.getAutor());

        System.out.println(libro);


        //! Ejemplo producto
        Producto vacio = new Producto();
        vacio.mostrar();

        Producto cafe = new Producto("Cafe", 12000.0);
        cafe.mostrar();



    }
}