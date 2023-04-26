package com.example.readingisgood;

import com.example.readingisgood.model.domain.Book;
import com.example.readingisgood.model.domain.User;
import com.example.readingisgood.repository.BookRepository;
import com.example.readingisgood.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ReadingIsGoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingIsGoodApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(BookRepository bookRepository, UserRepository userRepository) {
		return args -> {
			bookRepository.save(new Book("Metro 2033","Dmitry Glukhovsky", LocalDate.of(2015,1,1),7,100));
			bookRepository.save(new Book("Sherlock Holmes","Arthur Conan Doyle", LocalDate.of(2017,1,1),5,45));
			bookRepository.save(new Book("White Fang","Jack London", LocalDate.of(2013,1,1),23,36));
			bookRepository.save(new Book("Harry Potter and the Goblet of Fire","J. K. Rowling", LocalDate.of(2005,1,1),35,23));
			bookRepository.save(new Book("Robinson Crusoe","Daniel Defoe", LocalDate.of(2007,1,1),4,38));
			bookRepository.save(new Book("Great Expectations","Charles Dickens", LocalDate.of(2009,1,1),13,26));
			bookRepository.save(new Book("The Time Machine","Paula Hawkins", LocalDate.of(2019,1,1),17,19));
			bookRepository.save(new Book("Fahrenheit 451","Ray Bradbury", LocalDate.of(2022,1,1),10,58));
			bookRepository.save(new Book("Heidi","Johanna Spyri", LocalDate.of(2021,1,1),8,88));
			bookRepository.save(new Book("Lord of the Files","William Golding", LocalDate.of(2018,1,1),3,76));
			userRepository.save(new User("admin","$2a$12$FSyIuQ/Ptegk5O4ZDT1DxeUzIiPd/Xv8wUu75nZ/HBCknL90PDR12",false));
		};
	}
}
