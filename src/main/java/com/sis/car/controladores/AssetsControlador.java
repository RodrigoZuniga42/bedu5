package com.sis.car.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sis.car.servicio.ArchivoServicioImpl;

@RestController
@RequestMapping("/assets")
public class AssetsControlador {

    @Autowired
    private ArchivoServicioImpl servicio;

    @GetMapping("/{filename:.+}")
    public Resource obtenerRecurso(@PathVariable("filename") String filename) {
        return servicio.cargarComoRecurso(filename);
    }

}
