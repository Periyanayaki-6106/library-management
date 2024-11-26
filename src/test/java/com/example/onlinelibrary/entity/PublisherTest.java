package com.example.onlinelibrary.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublisherTest {
	
	private Publisher publisher;
    private Long id;
    private String name;
    private Set<Book> books;

    @BeforeEach
    public void setUp() {
        id = 1L;
        name = "Test Publisher";
        books = new HashSet<>();
        publisher = new Publisher(id, name, books);
    }

    @Test
    public void testGetId() {
        assertEquals(id, publisher.getId());
    }

    @Test
    public void testSetId() {
        Long newId = 2L;
        publisher.setId(newId);
        assertEquals(newId, publisher.getId());
    }

    @Test
    public void testGetName() {
        assertEquals(name, publisher.getName());
    }

    @Test
    public void testSetName() {
        String newName = "New Publisher";
        publisher.setName(newName);
        assertEquals(newName, publisher.getName());
    }

    @Test
    public void testGetBooks() {
        assertEquals(books, publisher.getBooks());
    }

    @Test
    public void testSetBooks() {
        Set<Book> newBooks = new HashSet<>();
        publisher.setBooks(newBooks);
        assertEquals(newBooks, publisher.getBooks());
    }

    @Test
    public void testConstructorWithParameters() {
        Publisher newPublisher = new Publisher(3L, "Another Publisher", new HashSet<>());
        assertEquals(3L, newPublisher.getId());
        assertEquals("Another Publisher", newPublisher.getName());
        assertEquals(0, newPublisher.getBooks().size());
    }

    @Test
    public void testDefaultConstructor() {
        Publisher defaultPublisher = new Publisher();
        assertEquals(null, defaultPublisher.getId());
        assertEquals(null, defaultPublisher.getName());
        assertEquals(0, defaultPublisher.getBooks().size());
    }

}
