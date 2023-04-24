package com.prueba.banco.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Cliente extends Persona {
    
    @Column(name = "contrasena")
    private String contrasena;
    
    @Column(name = "estado")
    private Boolean estado;

	public Cliente() {
	}

	public Cliente(String contrasena, Boolean estado) {
		this.contrasena = contrasena;
		this.estado = estado;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cliente [contrasena=" + contrasena + ", estado=" + estado + "]";
	}
	
}