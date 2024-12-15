package com.onlib.core;

import com.onlib.core.controller.AuthorController;
import com.onlib.core.controller.AuthorizationController;
import com.onlib.core.controller.BookController;
import com.onlib.core.controller.ReviewController;
import com.onlib.core.controller.UserController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class CoreApplicationTests {

//	Почему-то не видит AuthorController (cannot resolve symbol AuthorController)
//	Но всё равно работает
	@Autowired
	private AuthorController authorController;

	@Autowired
	private AuthorizationController authorizationController;

	@Autowired
	private BookController bookController;

	@Autowired
	private ReviewController reviewController;

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
		assertNotNull("AuthorController is null", authorController);
		assertNotNull("AuthorizationController is null", authorizationController);
		assertNotNull("BookController is null", bookController);
		assertNotNull("ReviewController is null", reviewController);
		assertNotNull("UserController is null", userController);
	}

}
