package com.sis.car.exepciones;

public class ArchivoExepcion extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ArchivoExepcion(String mensaje) {
        super(mensaje);
    }

    public ArchivoExepcion(String mensaje, Throwable excepcion) {
        super(mensaje, excepcion);
    }
}
