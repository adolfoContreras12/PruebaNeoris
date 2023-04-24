package com.prueba.banco.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.banco.entity.Cliente;
import com.prueba.banco.entity.Cuenta;


public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
	Cuenta save(Cuenta cuenta);
	Optional<Cuenta> findById(Long id);
	Optional<Cuenta> findByNumeroCuenta(String NumeroCuenta);
	void deleteById(Long id);
	List<Cuenta> findByCliente(Cliente cliente);
}
