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
import com.onlib.core.service.BookFileProvider;
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
	public CommandLineRunner commandLineRunner(BookService repo) {
		return args -> {
			repo.AddBook("Слово пацана", new Author[] { new Author("Гурам Гурамыч") },
					getBytesFromFile("classpath:static/books/1.epub"));
			repo.AddBook("После бури", new Author[] { new Author("Луи Витон") },
					getBytesFromFile("classpath:static/books/2.epub"));
			repo.AddBook("Убийство в доме", new Author[] { new Author("Леша Такун") },
					getBytesFromFile("classpath:static/books/3.epub"));
			repo.AddBook("Шантарам", new Author[] { new Author("Гойдазавр Турлай") },
					getBytesFromFile("classpath:static/books/4.epub"));
			repo.AddBook("Путишествие слоника", new Author[] { new Author("Левый Правша") },
					getBytesFromFile("classpath:static/books/5.epub"));

		};
	}

	@Bean
	BookFileProvider bookFileProvider() throws IOException {
		return new DirectlyInProjectBookFileProvider(
				Paths.get(loader.getResource("classpath:static/books").getURI()).toString());
	}

}
