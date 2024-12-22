package com.onlib.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.onlib.core.service.UserService;

import com.onlib.core.model.LibraryUser;
import com.onlib.core.repository.UserRepository;
import com.onlib.core.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import com.onlib.core.model.Author;

import com.onlib.core.repository.BookRepository;
import com.onlib.core.service.IBookFileProvider;
import com.onlib.core.service.BookService;
import com.onlib.core.service.DirectlyInProjectBookFileProvider;

@SpringBootApplication
public class CoreApplication {

	@Autowired
	private ResourceLoader loader;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);

	}

	public byte[] getBytesFromFile(String relativePath) throws IOException {
		Resource res = loader.getResource(relativePath);
		return Files.readAllBytes(res.getFile().toPath());
	}

	@Bean
	public CommandLineRunner commandLineRunner(BookService bookService,
											   BookRepository bookRepository,
											   UserRepository userRepository,
											   ReviewService reviewService,
											   UserService userService) {

		return args -> {
			userService.addUser("user", "password");
			bookService.AddBook("Слово пацана", "Описание для Слово пацана",
					Arrays.asList(new Author("Гурам Гурамыч"), new Author("Мирон Федоров")),
					getBytesFromFile("classpath:static/books/1.epub"));
			bookService.AddBook("После бури", "Описание для После бури", Arrays.asList(new Author("Луи Витон")),
					getBytesFromFile("classpath:static/books/2.epub"));
			bookService.AddBook("Убийство в доме", "Описание для Убийство в доме",
					Arrays.asList(new Author("Леша Такун")),
					getBytesFromFile("classpath:static/books/3.epub"));
			bookService.AddBook("Путешествие слоника", "Описание для Путешествие слоника",
					Arrays.asList(new Author("Левый Правша")),
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Властелин Колец", "Описание для Властелин Колец",
					Arrays.asList(new Author("Джон Толкин")),
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Минск - Город Сталина", "Описание для Минск - Город Сталина",
					Arrays.asList(new Author("Иван Турлай")),
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Курс ДИиИ", "Описание для Курс ДИиИ",
					Arrays.asList(new Author("Григорий Фихтенгольц")),
					getBytesFromFile("classpath:static/books/5.epub"));

			// userRepository.save(new LibraryUser("Kraken", "zZzZzZzZ"));
			userService.addUser("Kraken", "zZzZzZzZ");
			userService.addUser("Nikvader", "KrasavchikLuchsheAngeli");
			userService.addUser("Michel", "********");

			reviewService.addReview(
					userRepository.findByName("Kraken")
							.orElseThrow()
							.getId(),
					bookRepository.findByName("Минск - Город Сталина")
							.orElseThrow()
							.getId(),
					"It's the best book over resource. " +
							"You should read it instead of prayer before sleeping.",
					50L
			);
			reviewService.addReview(1L, 1L, "Review1", 1L);

		};
	}

	@Bean
	IBookFileProvider bookFileProvider() throws IOException {
		return new DirectlyInProjectBookFileProvider(
				Paths.get(loader.getResource("classpath:static/books").getURI()).toString());
	}

}
