package com.sis.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration
@SpringBootApplication
public class SisCarApplication {

	public static void main(String[] args) {

		SpringApplication.run(SisCarApplication.class, args);

		String url = "jdbc:mysql://localhost:3306/sis_car";
		String usuario = "root";
		String contraseña = "";

		try {
			Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
			Statement statement = conexion.createStatement();

			// Ejecuta una consulta para verificar si hay registros en la tabla sis_car.genero
			String consulta = "SELECT COUNT(*) FROM sis_car.genero";
			ResultSet resultSet = statement.executeQuery(consulta);

			if (resultSet.next()) {
				int cantidadRegistros = resultSet.getInt(1);
				if (cantidadRegistros > 0) {
					System.out.println("La tabla sis_car.genero contiene registros.");
				} else {
					System.out.println("La tabla sis_car.genero está vacía.");
				}
			}

			resultSet.close();
			statement.close();
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
		}





	}


	}


