package com.onlib.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.onlib.core.model.Book;
import com.onlib.core.model.User;
import com.onlib.core.repository.BookRepository;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		
	}

	@Bean
	public CommandLineRunner commandLineRunner(BookRepository repo){
		return args -> {
			repo.save(new Book("Matan"));	
			repo.save(new Book("Norm kNiga"));	
		};
	}

}
