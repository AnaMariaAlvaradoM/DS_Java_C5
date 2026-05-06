package shopSystem.Clase3.service;

import shopSystem.Clase3.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface IclienteService {
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorEmail(String email);
    Cliente guardar(Cliente cliente);
    Optional<Cliente> actualizar(Long id, Cliente datosNuevos);
    boolean eliminar(Long id);
}