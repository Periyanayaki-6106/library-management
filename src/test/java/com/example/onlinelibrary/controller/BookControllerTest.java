package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.entity.Author;
import com.example.onlinelibrary.entity.Book;
import com.example.onlinelibrary.entity.Publisher;
import com.example.onlinelibrary.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testCreateBook() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());
        Book book = new Book(1L, "Sample Book", new Date(), author, publisher);
        
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Sample Book\", \"publishedDate\":\"2023-01-01\", \"author\":{\"id\":1}, \"publisher\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());
        Book book1 = new Book(1L, "Sample Book 1", new Date(), author, publisher);
        Book book2 = new Book(2L, "Sample Book 2", new Date(), author, publisher);
        
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetBookById() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());
        Book book = new Book(1L, "Sample Book", new Date(), author, publisher);
        
        when(bookService.getBookById(anyLong())).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateBook() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());
        Book existingBook = new Book(1L, "Sample Book", new Date(), author, publisher);
        Book updatedBook = new Book(1L, "Updated Book", new Date(), author, publisher);
        
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Book\", \"publishedDate\":\"2023-01-01\", \"author\":{\"id\":1}, \"publisher\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }
    @Test
    void testUpdateBookNotFound() throws Exception {
        // Mocking the scenario where the book is not found
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(null);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Book\", \"publishedDate\":\"2023-01-01\", \"author\":{\"id\":1}, \"publisher\":{\"id\":1}}"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDeleteBook() throws Exception {
        // Arrange: Prepare the ID of the book to be deleted
        Long bookId = 1L;

        // Act: Perform the DELETE request
        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNoContent());

        // Assert: Verify that the deleteBook method in the bookService was called with the correct ID
        verify(bookService, times(1)).deleteBook(bookId);
    }
    @Test
    void testSearchBooks() throws Exception {
        // Arrange: Prepare the search term and expected result
        String searchTerm = "Java";

        // Create Author and Publisher instances
        Author author1 = new Author(1L, "Joshua Bloch", new HashSet<>());
        Publisher publisher1 = new Publisher(1L, "Publisher A", new HashSet<>());

        Author author2 = new Author(2L, "Brian Goetz", new HashSet<>());
        Publisher publisher2 = new Publisher(2L, "Publisher B", new HashSet<>());

        // Create Book instances using the correct constructor
        List<Book> expectedBooks = Arrays.asList(
                new Book(1L, "Effective Java", new Date(System.currentTimeMillis()), author1, publisher1),
                new Book(2L, "Java Concurrency in Practice", new Date(System.currentTimeMillis()), author2, publisher2)
        );

        // Mock the service method to return the expected result
        when(bookService.searchBooks(searchTerm)).thenReturn(expectedBooks);

        // Act: Perform the GET request
        mockMvc.perform(get("/api/books/search")
                .param("searchTerm", searchTerm)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Effective Java"))
                .andExpect(jsonPath("$[1].title").value("Java Concurrency in Practice"));

        // Assert: Verify that the searchBooks method in the bookService was called with the correct search term
        verify(bookService, times(1)).searchBooks(searchTerm);
    }
    @Test
    void testSortBooksByTitle() throws Exception {
        // Arrange: Prepare the expected sorted list of books
        List<Book> sortedBooks = Arrays.asList(
                new Book(1L, "A Tale of Two Cities", new Date(System.currentTimeMillis()), new Author(1L, "Charles Dickens", new HashSet<>()), new Publisher(1L, " Publisher A", new HashSet<>())),
                new Book(2L, "Moby Dick", new Date(System.currentTimeMillis()), new Author(2L, "Herman Melville", new HashSet<>()), new Publisher(2L, "Publisher B", new HashSet<>())),
                new Book(3L, "The Great Gatsby", new Date(System.currentTimeMillis()), new Author(3L, "F. Scott Fitzgerald", new HashSet<>()), new Publisher(3L, "Publisher C", new HashSet<>())) 
        );

        // Mock the service method to return the expected sorted result
        when(bookService.sortBooksByTitle()).thenReturn(sortedBooks);

        // Act: Perform the GET request
        mockMvc.perform(get("/api/books/sort/title")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("A Tale of Two Cities"))
                .andExpect(jsonPath("$[1].title").value("Moby Dick"))
                .andExpect(jsonPath("$[2].title").value("The Great Gatsby"));

        // Assert: Verify that the sortBooksByTitle method in the bookService was called
        verify(bookService, times(1)).sortBooksByTitle();
    }
    
    @Test
    void testGenerateAuthorReport() throws Exception {
        // Arrange: Prepare the expected list of author reports
        List<String> authorReports = Arrays.asList(
                "Author: Charles Dickens, Books: 5",
                "Author: Herman Melville, Books: 3",
                "Author: F. Scott Fitzgerald, Books: 2"
        );

        // Mock the service method to return the expected report
        when(bookService.generateAuthorReport()).thenReturn(authorReports);

        // Act: Perform the GET request
        mockMvc.perform(get("/api/books/reports")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("Author: Charles Dickens, Books: 5"))
                .andExpect(jsonPath("$[1]").value("Author: Herman Melville, Books: 3"))
                .andExpect(jsonPath("$[2]").value("Author: F. Scott Fitzgerald, Books: 2"));

        // Assert: Verify that the generateAuthorReport method in the bookService was called
        verify(bookService, times(1)).generateAuthorReport();
    }
    
//    @Test
    public void testSortBooksByPublicationDate_NoBooks() throws Exception {
        // Mock the service call to return an empty list
        when(bookService.sortBooksByPublicationDate()).thenReturn(new ArrayList<>());

        // Act: Perform the GET request to the endpoint
        mockMvc.perform(get("/sort/published_date")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Assert: Check for a 200 OK response
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty()); // Expect an empty list

        // Verify that the service method was called
        verify(bookService, times(1)).sortBooksByPublicationDate();
    }
}