package com.prueba.banco.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.repository.ClienteRepository;
import com.prueba.banco.repository.CuentaRepository;


@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id) {
        try {
            Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
            if (cuentaOptional.isPresent()) {
                return ResponseEntity.ok(cuentaOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idCliente}")
    public ResponseEntity<Cuenta> crearCuenta(@PathVariable Long idCliente, @RequestBody Cuenta cuenta) {
        try {
            Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                cuenta.setCliente(cliente);
                cuenta.setEstado(true);
                Cuenta nuevaCuenta = cuentaRepository.save(cuenta);
                return ResponseEntity.created(URI.create("/api/cuentas/" + nuevaCuenta.getIdCuenta())).body(nuevaCuenta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaActualizada) {
        try {
            Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
            if (cuentaOptional.isPresent()) {
                Cuenta cuenta = cuentaOptional.get();
                cuenta.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
                cuenta.setTipoCuenta(cuentaActualizada.getTipoCuenta());
                cuenta.setSaldoInicial(cuentaActualizada.getSaldoInicial());
                cuenta.setEstado(cuentaActualizada.getEstado());
                cuentaRepository.save(cuenta);
                return ResponseEntity.ok(cuenta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        try {
            cuentaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
