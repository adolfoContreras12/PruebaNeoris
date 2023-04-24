package com.prueba.banco.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.entity.Movimientos;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long>{
	Movimientos save(Movimientos movimientos);
	Optional<Movimientos> findById(Long id);
	void deleteById(Long id);
	List<Movimientos> findByCuentaNumeroCuenta(String numeroCuenta);
	List<Movimientos> findByCuentaAndFechaBetween(Cuenta cuenta, Date fechaInicio, Date fechaFin);
}
