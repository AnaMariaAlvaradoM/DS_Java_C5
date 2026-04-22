package com.devsenior.vetclinicdb.service;

import com.devsenior.vetclinicdb.model.Propietario;
import com.devsenior.vetclinicdb.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    // Constructor injection – la forma preferida sobre @Autowired en campo
    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> listarTodos() {
        return propietarioRepository.findAll();
    }

    public Optional<Propietario> buscarPorId(Long id) {
        return propietarioRepository.findById(id);
    }

    public Propietario guardar(Propietario propietario) {

        return propietarioRepository.save(propietario);
    }

    public void eliminar(Long id) {
        propietarioRepository.deleteById(id);
    }
}
