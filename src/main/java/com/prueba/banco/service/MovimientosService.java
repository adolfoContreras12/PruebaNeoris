package com.prueba.banco.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.entity.Movimientos;
import com.prueba.banco.repository.CuentaRepository;
import com.prueba.banco.repository.MovimientosRepository;

@Service
public class MovimientosService {
	@Autowired
	private final MovimientosRepository movimientosRepository;
	
	public MovimientosService(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }

    public Movimientos crearMovimiento(Movimientos movimiento) {
        return movimientosRepository.save(movimiento);
    }

    public Optional<Movimientos> buscarMovimientoPorId(Long id) {
        return movimientosRepository.findById(id);
    }

    public void eliminarMovimientoPorId(Long id) {
        movimientosRepository.deleteById(id);
    }
    
    public List<Movimientos> findByCuentaAndFechaBetween(Cuenta cuenta, Date fechaInicio, Date fechaFin) {
        return movimientosRepository.findByCuentaAndFechaBetween(cuenta, fechaInicio, fechaFin);
    }
}
