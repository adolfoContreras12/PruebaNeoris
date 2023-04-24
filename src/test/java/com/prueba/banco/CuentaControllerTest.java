package com.prueba.banco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.prueba.banco.controller.CuentaController;
import com.prueba.banco.entity.Cliente;
import com.prueba.banco.entity.Cuenta;
import com.prueba.banco.repository.ClienteRepository;
import com.prueba.banco.repository.CuentaRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaRepository cuentaRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);

        cuenta = new Cuenta();
        cuenta.setIdCuenta(1L);
        cuenta.setNumeroCuenta("123456");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);
    }

    @Test
    public void obtenerCuentaTest() throws Exception {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCuenta").value(1L))
                .andExpect(jsonPath("$.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.tipoCuenta").value("Ahorros"))
                .andExpect(jsonPath("$.saldoInicial").value(1000.0))
                .andExpect(jsonPath("$.estado").value(true));

        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    public void obtenerCuentaNotFoundTest() throws Exception {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1"))
                .andExpect(status().isNotFound());

        verify(cuentaRepository, times(1)).findById(1L);
    }
	
    @Test
    public void crearCuentaTest() throws Exception {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        String cuentaJson = "{ \"numeroCuenta\": \"123456\", \"tipoCuenta\": \"Ahorros\", \"saldoInicial\": 1000.0, \"estado\": true }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCuenta").value(1L))
                .andExpect(jsonPath("$.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.tipoCuenta").value("Ahorros"))
                .andExpect(jsonPath("$.saldoInicial").value(1000.0))
                .andExpect(jsonPath("$.estado").value(true));

        verify(clienteRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    public void crearCuentaNotFoundTest() throws Exception {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        String cuentaJson = "{ \"numeroCuenta\": \"123456\", \"tipoCuenta\": \"Ahorros\", \"saldoInicial\": 1000.0, \"estado\": true }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson))
                .andExpect(status().isNotFound());

        verify(clienteRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(0)).save(any(Cuenta.class));
    }

    
    @Test
    public void actualizarCuentaTest() throws Exception {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        String cuentaJson = "{ \"numeroCuenta\": \"123456\", \"tipoCuenta\": \"Ahorros\", \"saldoInicial\": 1500.0, \"estado\": true }";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCuenta").value(1L))
                .andExpect(jsonPath("$.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.tipoCuenta").value("Ahorros"))
                .andExpect(jsonPath("$.saldoInicial").value(1500.0))
                .andExpect(jsonPath("$.estado").value(true));

        verify(cuentaRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    public void actualizarCuentaNotFoundTest() throws Exception {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty());

        String cuentaJson = "{ \"numeroCuenta\": \"123456\", \"tipoCuenta\": \"Ahorros\", \"saldoInicial\": 1500.0, \"estado\": true }";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cuentas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cuentaJson))
                .andExpect(status().isNotFound());

        verify(cuentaRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(0)).save(any(Cuenta.class));
    }
    
}
