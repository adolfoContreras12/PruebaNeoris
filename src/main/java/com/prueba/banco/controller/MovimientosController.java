package com.prueba.banco.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.prueba.banco.entity.Cliente;
import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.entity.Movimientos;
import com.prueba.banco.exceptions.SaldoNoDisponibleException;
import com.prueba.banco.model.CuentaReporte;
import com.prueba.banco.repository.ClienteRepository;
import com.prueba.banco.repository.CuentaRepository;
import com.prueba.banco.repository.MovimientosRepository;

@RestController
@RequestMapping("/api")
public class MovimientosController {
		
	    @Autowired
	    private MovimientosRepository movimientosRepository;
	    
	    @Autowired
	    private CuentaRepository cuentaRepository;
	    
	    @Autowired
	    private ClienteRepository clienteRepository;
	    
	    @GetMapping("/movimientos/{numeroCuenta}")
	    public List<Movimientos> getMovimientos(@PathVariable String numeroCuenta) {
	        return movimientosRepository.findByCuentaNumeroCuenta(numeroCuenta);
	    }
	    
	    @PostMapping("/movimientos/{numeroCuenta}")
	    public ResponseEntity<Movimientos> crearMovimiento(@PathVariable String numeroCuenta , @RequestBody Movimientos movimiento) throws SaldoNoDisponibleException {	    	
	    	Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
	                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
	        	    	
	        double saldoActual = calcularSaldoActual(numeroCuenta);
      
	        
	        if (movimiento.getTipoMovimiento().equals("CREDITO")) {
	        	saldoActual += movimiento.getValor();
	        } else if (movimiento.getTipoMovimiento().equals("DEBITO")) {
	        	
	            if (saldoActual < movimiento.getValor()) {
	                throw new SaldoNoDisponibleException("Saldo no disponible");
	            }
	            
	            saldoActual -= movimiento.getValor();
	            movimiento.setValor(movimiento.getValor() * -1);
	        }
	        
	        Date fechaActual = new Date();
	        movimiento.setFecha(fechaActual);
	        movimiento.setCuenta(cuenta);
	        movimiento.setSaldo(saldoActual);

	        movimientosRepository.save(movimiento);
	        return ResponseEntity.ok(movimiento);
  	               
	    }

	    @DeleteMapping("/movimientos/{id}")
	    public void deleteMovimiento(@PathVariable Long id) {
	    	movimientosRepository.deleteById(id);
	    }


	    @GetMapping("/reportes")
	    public List<CuentaReporte> generarReporte(
	            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaInicio,
	            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaFin,
	            @RequestParam Long idCliente) {

	    	 List<CuentaReporte> cuentasReporte = new ArrayList<>();
	    	 Cliente cliente = new Cliente();
	    	 List<Cuenta> cuentas = new ArrayList<>();
	    	 List<Movimientos> movimientos = new ArrayList<>();
	    	 
	    	try {
				
	    		Optional<Cliente> optionalCliente = clienteRepository.findById(idCliente);
	    	    if (optionalCliente.isPresent()) {
	    	        cliente = optionalCliente.get();
	    	        cuentas = cuentaRepository.findByCliente(cliente);
	    	    } else {
	    	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
	    	    }
	    	   	    	    
	        for (Cuenta cuenta : cuentas) {
   	
	            movimientos = movimientosRepository.findByCuentaAndFechaBetween(cuenta, fechaInicio, fechaFin);
	            	           	            
	            for (Movimientos movimiento : movimientos) {
	            	CuentaReporte cuentaReporte = new CuentaReporte();
	            	cuentaReporte.setFecha( movimiento.getFecha() );
	            	cuentaReporte.setCliente(cliente.getNombre() );
	            	cuentaReporte.setNumeroCuenta( cuenta.getNumeroCuenta() );
	            	cuentaReporte.setTipo( cuenta.getTipoCuenta() );
	            	cuentaReporte.setSaldoInicial(cuenta.getSaldoInicial() );
	            	cuentaReporte.setEstado( cuenta.getEstado() );
	            	cuentaReporte.setMovimiento( movimiento.getValor()  );
	            	cuentaReporte.setSaldoDisponible(  movimiento.getSaldo()   );
	            	cuentasReporte.add(cuentaReporte);
	            }            
	           
	        }
	        
			} catch (Exception e) {
				 System.out.println("e  >>> "+ e );
			}
	        return cuentasReporte;
	    }	    
	    
	    
	    
	    public double calcularSaldoActual(String numeroCuenta) throws SaldoNoDisponibleException {
	        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
	                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
	        
	        List<Movimientos> movimientos = movimientosRepository.findByCuentaNumeroCuenta(numeroCuenta);
	        double saldoActual = 0;
	        
	        if (!movimientos.isEmpty()) {	          
	            Collections.sort(movimientos, Collections.reverseOrder(Comparator.comparing(Movimientos::getFecha)));	     
	            saldoActual = movimientos.get(0).getSaldo();   
	        } else {
	        	saldoActual = cuenta.getSaldoInicial();
	        }
       
	        return saldoActual;
	    }
}
