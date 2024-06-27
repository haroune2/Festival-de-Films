package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.domain.model.Film;
import com.example.demo.repository.FilmRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	 

	@Autowired 
	FilmRepository  filmRepository; 
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		
		// for (int i = 2; i < 30; i++) {
		// 	Film film = new Film(null, "null", "null", "null", "null"); 
		// 	filmRepository.save(film);
			
		// }

 
		





	}

}
