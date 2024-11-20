package com.onlib.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.onlib.core.model.Book;

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
			repo.save(new Book("Horsman Java"));	
			repo.save(new Book("A piece of peace"));	
			repo.save(new Book("Why c++ is better then Java"));
			repo.save(new Book("Nigger encyclopedia"));
			repo.save(new Book("Knigga"));
		};
	}

}
