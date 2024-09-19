package com.sis.car.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sis.car.modelo.Pelicula;
import com.sis.car.repositorios.GeneroRepositorio;
import com.sis.car.repositorios.PeliculaRepositorio;
import com.sis.car.servicio.ArchivoServicioImpl;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private ArchivoServicioImpl servicio;


    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 4) Pageable pageable) {
        Page<Pelicula> peliculas = peliculaRepositorio.findAll(pageable);
        return new ModelAndView("admin/index").addObject("peliculas", peliculas);
    }

    @GetMapping("/peliculas/nuevo")
    public ModelAndView mostrarFormularioDeNuevaPelicula() {
        List<Pelicula> generos = generoRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos",generos);
    }

    @PostMapping("/peliculas/nuevo")
    public ModelAndView registrarPelicula(@Validated Pelicula pelicula,BindingResult bindingResult) {
        if(bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
            if(pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada","MultipartNotEmpty");
            }

            List<Pelicula> generos = generoRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula",pelicula)
                    .addObject("generos",generos);
        }

        String rutaPortada = servicio.almacenarArchivo(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);

        peliculaRepositorio.save(pelicula);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepositorio.getOne(id);
        List<Pelicula> generos = generoRepositorio.findAll(Sort.by("titulo"));

        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula",pelicula)
                .addObject("generos",generos);
    }

    @PostMapping("/peliculas/{id}/editar")
    public ModelAndView actualizarPelicula(@PathVariable Integer id,@Validated Pelicula pelicula,BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<Pelicula> generos = generoRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula",pelicula)
                    .addObject("generos",generos);
        }

        Pelicula peliculaDB = peliculaRepositorio.getReferenceById(id);
        peliculaDB.setTitulo(pelicula.getTitulo());
        peliculaDB.setSinopsis(pelicula.getSinopsis());
        peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
        peliculaDB.setYoutubeTrailerId(pelicula.getYoutubeTrailerId());
        peliculaDB.setGeneros(pelicula.getGeneros());

        if(!pelicula.getPortada().isEmpty()) {
            servicio.eliminarArchivo(peliculaDB.getRutaPortada());
            String rutaPortada = servicio.almacenarArchivo(pelicula.getPortada());
            peliculaDB.setRutaPortada(rutaPortada);
        }

        peliculaRepositorio.save(peliculaDB);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepositorio.getReferenceById(id);
        peliculaRepositorio.delete(pelicula);
        servicio.eliminarArchivo(pelicula.getRutaPortada());

        return "redirect:/admin";
    }
}