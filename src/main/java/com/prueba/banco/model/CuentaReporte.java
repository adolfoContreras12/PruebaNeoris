package com.prueba.banco.model;

import java.util.Date;
import java.util.List;

import com.prueba.banco.entity.Movimientos;

public class CuentaReporte {
	
	private Date fecha;
	private String cliente;
	private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
    
	public CuentaReporte() {
	}

	public CuentaReporte(Date fecha, String cliente, String numeroCuenta, String tipo, Double saldoInicial,
			boolean estado, double movimiento, double saldoDisponible) {
		this.fecha = fecha;
		this.cliente = cliente;
		this.numeroCuenta = numeroCuenta;
		this.tipo = tipo;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
		this.movimiento = movimiento;
		this.saldoDisponible = saldoDisponible;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public double getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(double movimiento) {
		this.movimiento = movimiento;
	}

	public double getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	@Override
	public String toString() {
		return "CuentaReporte [Fecha=" + fecha + ", Cliente=" + cliente + ", Numero Cuenta=" + numeroCuenta + ", Tipo="
				+ tipo + ", Saldo Inicial=" + saldoInicial + ", Estado=" + estado + ", Movimiento=" + movimiento
				+ ", Saldo Disponible=" + saldoDisponible + "]";
	}   
	
	
}
