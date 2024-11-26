package com.example.onlinelibrary.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorTest {
	
	private Author author;
	
	@BeforeEach
    public void setUp() {
        author = new Author();
    }

    @Test
    public void testGettersAndSetters() {
        // Test ID
        author.setId(1L);
        assertEquals(1L, author.getId());

        // Test Name
        author.setName("J.K. Rowling");
        assertEquals("J.K. Rowling", author.getName());

        // Test Books
        Set<Book> books = new HashSet<>();
        Book book1 = new Book(); // Assuming Book class has a default constructor
        book1.setTitle("Harry Potter and the Philosopher's Stone");
        books.add(book1);

        author.setBooks(books);
        assertEquals(1, author.getBooks().size());
        assertEquals("Harry Potter and the Philosopher's Stone", author.getBooks().iterator().next().getTitle());
    }
    
    @Test
    public void testConstructor() {
        Set<Book> books = new HashSet<>();
        Book book1 = new Book(); // Assuming Book class has a default constructor
        book1.setTitle("Harry Potter and the Chamber of Secrets");
        books.add(book1);

        Author authorWithParams = new Author(1L, "J.K. Rowling", books);
        assertEquals(1L, authorWithParams.getId());
        assertEquals("J.K. Rowling", authorWithParams.getName());
        assertEquals(1, authorWithParams.getBooks().size());
        assertEquals("Harry Potter and the Chamber of Secrets", authorWithParams.getBooks().iterator().next().getTitle());
    }


}
