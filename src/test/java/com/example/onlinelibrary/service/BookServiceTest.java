package com.example.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.onlinelibrary.entity.Book;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.serviceImpl.BookServiceImpl;
//import com.example.onlinelibrary.serviceImpl.BookServiceImplTest;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@Mock
    private BookRepository bookRepository; // Mock the repository

    @InjectMocks
    private BookServiceImpl bookService; // Inject mocks into the service implementation

    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
//        book.setPublicationDate("2023-01-01");
    }

    @Test
    public void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookByIdFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Book foundBook = bookService.getBookById(999L);

        assertNull(foundBook);
        verify(bookRepository, times(1)).findById(999L);
    }

//    @Test
//    public void testUpdateBook() {
//        Book updatedBook = new Book();
//        updatedBook.setTitle("Updated Book");
//
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
//
//        Book result = bookService.updateBook(1L, updatedBook);
//
//        assertNotNull(result);
//        assertEquals("Updated Book", result.getTitle());
//        verify(bookRepository, times(1)).findById(1L);
//        verify(bookRepository, times(1)).save(updatedBook);
//    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testSortBooksByTitle() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.sortBooksByTitle();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testSortBooksByPublicationDate() {
        List<Book> books = new ArrayList<>();
        books .add(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.sortBooksByPublicationDate();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

//    @Test
//    public void testGenerateAuthorReport() {
//        List<String> report = new ArrayList<>();
//        report.add("Author: Test Author, Books: 1");
//        when(bookRepository.generateAuthorReport()).thenReturn(report);
//
//        List<String> result = bookService.generateAuthorReport();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Author: Test Author, Books: 1", result.get(0));
//        verify(bookRepository, times(1)).generateAuthorReport();
//    }
    
    

}