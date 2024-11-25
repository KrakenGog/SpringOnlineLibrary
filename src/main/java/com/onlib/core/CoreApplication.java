package com.onlib.core;

import java.util.Arrays;

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
			repo.save(new Book("Слово пацана", new String[]{"Роберт Гараев"}, "slova.epub"));
			repo.save(new Book("После бури", new String[]{"Фредрик Бакман"}, "posle.epub"));	
			repo.save(new Book("Убийства во Флит-Хаусе", new String[]{"Люсинда Райли"}, "kill.epub"));	
			repo.save(new Book("Путешествие в Элевсин", new String[]{"Виктор Пелевин"}, "travel.epub"));	
			repo.save(new Book("Knigga",new String[]{"Knigger","Nikolas Kowalski"},"knigga.epub"));
			
			
		};
	}

}
