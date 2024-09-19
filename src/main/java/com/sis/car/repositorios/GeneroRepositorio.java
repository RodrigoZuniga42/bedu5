package com.sis.car.repositorios;


import com.sis.car.modelo.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepositorio extends JpaRepository<Pelicula, Integer>{
}

