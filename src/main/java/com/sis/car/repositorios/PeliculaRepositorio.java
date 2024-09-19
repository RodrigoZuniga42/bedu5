package com.sis.car.repositorios;

import com.sis.car.modelo.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer>  {

}
