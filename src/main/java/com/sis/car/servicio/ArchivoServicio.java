package com.sis.car.servicio;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface ArchivoServicio {
    //Para indicar que este metodo se ejecuta cuando hay una nueva instancia de esta clase
    @PostConstruct
    void iniciarAlmacenDeArchivos();

    String almacenarArchivo(MultipartFile archivo);

    Path cargarArchivo(String nombreArchivo);

    Resource cargarComoRecurso(String nombreArchivo);

    void eliminarArchivo(String nombreArchivo);
}
