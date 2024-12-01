package com.onlib.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.catalina.servlets.WebdavServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.core.io.Resource;
import com.onlib.core.model.Author;
import com.onlib.core.model.Book;

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
	public CommandLineRunner commandLineRunner(BookService bookService, BookRepository repo) {
		return args -> {
			bookService.AddBook("Слово пацана", new Author[] { new Author("Гурам Гурамыч"), new Author("Мирон Федоров") },
					getBytesFromFile("classpath:static/books/1.epub"));
			bookService.AddBook("После бури", new Author[] { new Author("Луи Витон") },
					getBytesFromFile("classpath:static/books/2.epub"));
			bookService.AddBook("Убийство в доме", new Author[] { new Author("Леша Такун") },
					getBytesFromFile("classpath:static/books/3.epub"));
			bookService.AddBook("Путишествие слоника", new Author[] { new Author("Левый Правша") },
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Властелин Колец", new Author[] { new Author("Джон Толкин") },
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Минск - Город Сталина", new Author[] { new Author("Иван Турлай") },
					getBytesFromFile("classpath:static/books/5.epub"));
			bookService.AddBook("Курс ДИиИ", new Author[] { new Author("Григорий Фихтенгольц") },
					getBytesFromFile("classpath:static/books/5.epub"));

		};
	}

	@Bean
	IBookFileProvider bookFileProvider() throws IOException {
		return new DirectlyInProjectBookFileProvider(
				Paths.get(loader.getResource("classpath:static/books").getURI()).toString());
	}

}
