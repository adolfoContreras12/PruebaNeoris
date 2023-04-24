package com.prueba.banco.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prueba.banco.entity.Cliente;
import com.prueba.banco.repository.ClienteRepository;


@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void eliminarClientePorId(Long id) {
        clienteRepository.deleteById(id);
    }
}
