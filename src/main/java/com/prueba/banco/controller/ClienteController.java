package com.prueba.banco.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.banco.entity.Cliente;
import com.prueba.banco.repository.ClienteRepository;




@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	@Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
       try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent()) {
                return ResponseEntity.ok(cliente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
    	 try {
             Optional<Cliente> clienteExistenteOptional = clienteRepository.findById(id);

             if (!clienteExistenteOptional.isPresent()) {
                 return ResponseEntity.notFound().build(); 
             }

             Cliente clienteExistente = clienteExistenteOptional.get();
             clienteExistente.setNombre(clienteActualizado.getNombre());
             clienteExistente.setGenero(clienteActualizado.getGenero());
             clienteExistente.setEdad(clienteActualizado.getEdad());
             clienteExistente.setIdentificacion(clienteActualizado.getIdentificacion());
             clienteExistente.setDireccion(clienteActualizado.getDireccion());
             clienteExistente.setTelefono(clienteActualizado.getTelefono());
             clienteExistente.setContrasena(clienteActualizado.getContrasena());
             clienteExistente.setEstado(clienteActualizado.getEstado());

             Cliente clienteActualizadoGuardado = clienteRepository.save(clienteExistente);
             return ResponseEntity.ok(clienteActualizadoGuardado);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
    }   
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        try {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
