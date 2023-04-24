package com.prueba.banco.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prueba.banco.entity.Cliente;



public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente save(Cliente cliente);
	Optional<Cliente> findById(Long id);
	void deleteById(Long id);
}
