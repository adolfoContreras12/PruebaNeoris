package com.prueba.banco.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.prueba.banco.entity.Cliente;
import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.repository.CuentaRepository;

@Service
public class CuentaService {
	private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> buscarCuentaPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public void eliminarCuentaPorId(Long id) {
        cuentaRepository.deleteById(id);
    }
    
    public Optional<Cuenta> buscarCuentaPorNumeroCuenta(String NumeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(NumeroCuenta);
    }    
    
    public List<Cuenta> buscarCuentaPorDeClientes(Cliente cliente) {
        return cuentaRepository.findByCliente(cliente);
    }
}
