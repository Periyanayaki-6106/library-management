package com.example.onlinelibrary.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {
	
	private Book book;
    private Author author;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        // Initialize Author
        author = new Author();
        author.setId(1L); // Assuming there's an ID field
        author.setName("Author Name"); // Assuming there's a name field

        // Initialize Publisher
        publisher = new Publisher();
        publisher.setId(1L); // Assuming there's an ID field
        publisher.setName("Publisher Name"); // Assuming there's a name field

        // Initialize Book object
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book Title");
        book.setpublishedDate(new Date(0)); // Correct method name
        book.setAuthor(author);
        book.setPublisher(publisher);
    }

    @Test
    public void testBookCreation() {
        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Sample Book Title", book.getTitle());
        assertNotNull(book.getpublishedDate()); // Correct method name
        assertEquals(author, book.getAuthor());
        assertEquals(publisher, book.getPublisher());
    }

    @Test
    public void testSettersAndGetters() {
        book.setId(2L);
        book.setTitle("Another Book Title");
        Date newDate = new Date(0);
        book.setpublishedDate(newDate); // Correct method name
        book.setAuthor(new Author()); // Setting a new Author
        book.setPublisher(new Publisher()); // Setting a new Publisher

        assertEquals(2L, book.getId());
        assertEquals("Another Book Title", book.getTitle());
        assertEquals(newDate, book.getpublishedDate()); // Correct method name
        assertNotNull(book.getAuthor());
        assertNotNull(book.getPublisher());
    }

    @Test
    public void testParameterizedConstructor() {
        Book parameterizedBook = new Book(1L, "Parameterized Book Title", new Date(0), author, publisher);
        assertEquals(1L, parameterizedBook.getId());
        assertEquals("Parameterized Book Title", parameterizedBook.getTitle());
        assertNotNull(parameterizedBook.getpublishedDate()); // Correct method name
        assertEquals(author, parameterizedBook.getAuthor());
        assertEquals(publisher, parameterizedBook.getPublisher());
    }
	
	


}
