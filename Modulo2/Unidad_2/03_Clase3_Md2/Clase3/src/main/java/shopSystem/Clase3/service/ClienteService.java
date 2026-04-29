package shopSystem.Clase3.service;

import org.springframework.stereotype.Service;
import shopSystem.Clase3.model.Cliente;
import shopSystem.Clase3.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        return clienteRepository.save(cliente);
    }
    public Optional<Cliente> actualizar(Long id, Cliente datosNuevos) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(datosNuevos.getNombre());
            clienteExistente.setEmail(datosNuevos.getEmail());
            return clienteRepository.save(clienteExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}