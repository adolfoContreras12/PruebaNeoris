package com.prueba.banco.entity;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos")
public class Movimientos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    
    @Column(name = "fecha")
    private Date fecha;
    
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;
    
    @Column(name = "valor")
    private Double valor;
    
    @Column(name = "saldo")
    private Double saldo;

	public Movimientos() {
	}

	public Movimientos(Long id, Cuenta cuenta, Date fecha, String tipoMovimiento, Double valor, Double saldo) {
		this.id = id;
		this.cuenta = cuenta;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.valor = valor;
		this.saldo = saldo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimientos other = (Movimientos) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Movimientos [id=" + id + ", fecha=" + fecha + ", tipoMovimiento=" + tipoMovimiento + ", valor=" + valor
				+ ", saldo=" + saldo + "]";
	}


}
