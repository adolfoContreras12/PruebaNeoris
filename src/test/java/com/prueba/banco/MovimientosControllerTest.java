package com.prueba.banco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import com.prueba.banco.entity.Cliente;
import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.entity.Movimientos;
import com.prueba.banco.exceptions.SaldoNoDisponibleException;
import com.prueba.banco.model.CuentaReporte;
import com.prueba.banco.repository.ClienteRepository;
import com.prueba.banco.repository.CuentaRepository;
import com.prueba.banco.repository.MovimientosRepository;
import com.prueba.banco.controller.MovimientosController;

@RunWith(SpringRunner.class)
@WebMvcTest(MovimientosController.class)
public class MovimientosControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientosRepository movimientosRepository;

    @MockBean
    private CuentaRepository cuentaRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    private Cuenta cuenta;
    private Cliente cliente;
    private Movimientos movimiento;
    
    @Before
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");

        cuenta = new Cuenta();
        cuenta.setIdCuenta(1L);
        cuenta.setNumeroCuenta("123456");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);

        movimiento = new Movimientos();
        movimiento.setId(1L);
        movimiento.setFecha(new Date());
        movimiento.setTipoMovimiento("CREDITO");
        movimiento.setValor(500.0);
        movimiento.setCuenta(cuenta);
    }
    
    @Test
    public void getMovimientosTest() throws Exception {
        List<Movimientos> movimientos = Arrays.asList(movimiento);
        when(movimientosRepository.findByCuentaNumeroCuenta("123456")).thenReturn(movimientos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movimientos/123456"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].tipoMovimiento").value("CREDITO"))
            .andExpect(jsonPath("$[0].valor").value(500.0));

        verify(movimientosRepository, times(1)).findByCuentaNumeroCuenta("123456");
    }   
    
    @Test
    public void crearMovimientoTest() throws Exception {
        when(cuentaRepository.findByNumeroCuenta("123456")).thenReturn(Optional.of(cuenta));
        when(movimientosRepository.findByCuentaNumeroCuenta("123456")).thenReturn(new ArrayList<Movimientos>());
        when(movimientosRepository.save(any(Movimientos.class))).thenReturn(movimiento);

        String movimientoJson = "{\"tipoMovimiento\": \"CREDITO\", \"valor\": 500.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movimientoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoMovimiento").value("CREDITO"))
                .andExpect(jsonPath("$.valor").value(500.0));

        verify(cuentaRepository, times(1)).findByNumeroCuenta("123456");
        verify(movimientosRepository, times(1)).findByCuentaNumeroCuenta("123456");
        verify(movimientosRepository, times(1)).save(any(Movimientos.class));
    }    
    
}
