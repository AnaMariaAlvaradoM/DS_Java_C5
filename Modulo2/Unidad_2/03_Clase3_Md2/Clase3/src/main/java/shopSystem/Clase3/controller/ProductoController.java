package shopSystem.Clase3.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopSystem.Clase3.model.Producto;
import shopSystem.Clase3.service.IProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> listarDisponibles() {
        return ResponseEntity.ok(productoService.listarDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@Valid @RequestBody Producto producto) {
        return ResponseEntity.status(201).body(productoService.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        return productoService.actualizar(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/menores-de")
    public ResponseEntity<List<Producto>> menoresDe(@RequestParam Double precio) {
        return ResponseEntity.ok(productoService.obtenerProductosMenoresDe(precio));
    }

    @GetMapping("/mas-vendidos")
   public ResponseEntity<List<Object[]>> masVendidos() {
        return ResponseEntity.ok(productoService.obtenerProductosMasVendidos());
   }

   @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double PrecioMin,
            @RequestParam(required = false) Double PrecioMax){
        return ResponseEntity.ok(productoService.buscarConFiltros(nombre, PrecioMin, PrecioMax));
   }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
            boolean actualizado = productoService.actualizarStock(id, cantidad);
            if (actualizado) {
                return ResponseEntity.ok("Stock actualizado correctamente.");
            } else {
                return ResponseEntity.notFound().build();
            }
    }



















}