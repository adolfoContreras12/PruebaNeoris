package com.prueba.banco.exceptions;

public class SaldoNoDisponibleException extends Exception {
    public SaldoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
