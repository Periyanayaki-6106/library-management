package com.example.onlinelibrary.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.onlinelibrary.entity.Author;
import com.example.onlinelibrary.entity.Book;
import com.example.onlinelibrary.entity.Publisher;
import com.example.onlinelibrary.repository.BookRepository;

class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    void testCreateBook() {
        Author author = new Author();
        author.setName("Author Name");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Book book = new Book();
        book.setTitle("Book Title");
        book.setpublishedDate(new Date());
        book.setAuthor(author);
        book.setPublisher(publisher);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);

        assertNotNull(createdBook);
        assertEquals("Book Title", createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetAllBooks() {
        Author author = new Author();
        author.setName("Author Name");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setpublishedDate(new Date());
        book1.setAuthor(author);
        book1.setPublisher(publisher);

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setpublishedDate(new Date());
        book2.setAuthor(author);
        book2.setPublisher(publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Author author = new Author();
        author.setName("Author Name");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Book book = new Book();
        book.setTitle("Book Title");
        book.setpublishedDate(new Date());
        book.setAuthor(author);
        book.setPublisher(publisher);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertNotNull(foundBook);
        assertEquals("Book Title", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBook() {
        Author author = new Author();
        author.setName("Old Author");

        Publisher publisher = new Publisher();
        publisher.setName("Old Publisher");

        Book existingBook = new Book();
        existingBook.setTitle("Old Title");
        existingBook.setpublishedDate(new Date());
        existingBook.setAuthor(author);
        existingBook.setPublisher(publisher);

        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("New Title");
        updatedBookDetails.setpublishedDate(new Date());
        updatedBookDetails.setAuthor(new Author());
        updatedBookDetails.setPublisher(new Publisher());

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        Book updatedBook = bookService.updateBook(1L, updatedBookDetails);

        assertNotNull(updatedBook);
        assertEquals("New Title", updatedBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }
    @Test
    void testUpdateBook_NotFound() {
        // Arrange: Mock the behavior of the repository to return an empty Optional
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act: Call the updateBook method
        Book updatedBook = bookService.updateBook(1L, new Book());

        // Assert: Verify that the result is null
        assertNull(updatedBook);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class)); // Ensure save was never called
    }
    

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchBooksByTitle() {
        Author author = new Author(1L, "Author Name", null);
        Publisher publisher = new Publisher(1L, "Publisher Name", null);

        Book book1 = new Book(1L, "Java Programming", new Date(), author, publisher);
        Book book2 = new Book(2L, "Python Programming", new Date(), author, publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> foundBooks = bookService.searchBooks("Java");

        assertEquals(1, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testSearchBooksByAuthorName() {
        Author author = new Author(1L, "Author Name", null);
        Publisher publisher = new Publisher(1L, "Publisher Name", null);

        Book book1 = new Book(1L, "Java Programming", new Date(), author, publisher);
        Book book2 = new Book(2L, "Python Programming", new Date(), author, publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> foundBooks = bookService.searchBooks("Author Name");

        assertEquals(2, foundBooks.size()); // Both books should be found
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testSearchBooksByPublisherName() {
        Author author = new Author(1L, "Author Name", null);
        Publisher publisher = new Publisher(1L, "Publisher Name", null);

        Book book1 = new Book(1L, "Java Programming", new Date(), author, publisher);
        Book book2 = new Book(2L, "Python Programming", new Date(), author, publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> foundBooks = bookService.searchBooks("Publisher Name");

        assertEquals(2, foundBooks.size()); // Both books should be found
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testSearchBooksCaseInsensitivity() {
        Author author = new Author(1L, "Author Name", null);
        Publisher publisher = new Publisher(1L, "Publisher Name", null);

        Book book1 = new Book(1L, "Java Programming", new Date(), author, publisher);
        Book book2 = new Book(2L, "Python Programming", new Date(), author, publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Search with different casing
        List<Book> foundBooks = bookService.searchBooks("JAVA");

        assertEquals(1, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testSearchBooksNoMatches() {
        Author author = new Author(1L, "Author Name", null);
        Publisher publisher = new Publisher(1L, "Publisher Name", null);

        Book book1 = new Book(1L, "Java Programming", new Date(), author, publisher);
        Book book2 = new Book(2L, "Python Programming", new Date(), author, publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> foundBooks = bookService.searchBooks("C++");

        assertEquals(0, foundBooks.size()); // No books should be found
        verify(bookRepository, times(1)).findAll();
    }


    @Test
    void testSortBooksByTitle() {
        Author author = new Author();
        author.setName("Author Name");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Book book1 = new Book();
        book1.setTitle("B Book");
        book1.setpublishedDate(new Date());
        book1.setAuthor(author);
        book1.setPublisher(publisher);

        Book book2 = new Book();
        book2.setTitle("A Book");
        book2.setpublishedDate(new Date());
        book2.setAuthor(author);
        book2.setPublisher(publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> sortedBooks = bookService.sortBooksByTitle();

        assertEquals("A Book", sortedBooks.get(0).getTitle());
        assertEquals("B Book", sortedBooks.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testSortBooksByPublicationDate() {
        Author author = new Author();
        author.setName("Author Name");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setpublishedDate(new Date(System.currentTimeMillis() - 10000)); // 10 seconds ago
        book1.setAuthor(author);
        book1.setPublisher(publisher);

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setpublishedDate(new Date()); // now
        book2.setAuthor(author);
        book2.setPublisher(publisher);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> sortedBooks = bookService.sortBooksByPublicationDate();

        assertEquals("Book One", sortedBooks.get(0).getTitle());
        assertEquals("Book Two", sortedBooks.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGenerateAuthorReport() {
        Author author1 = new Author();
        author1.setName("Author One");

        Author author2 = new Author();
        author2.setName("Author Two");

        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setpublishedDate(new Date());
        book1.setAuthor(author1);
        book1.setPublisher(new Publisher());

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setpublishedDate(new Date());
        book2.setAuthor(author1);
        book2.setPublisher(new Publisher());

        Book book3 = new Book();
        book3.setTitle("Book Three");
        book3.setpublishedDate(new Date());
        book3.setAuthor(author2);
        book3.setPublisher(new Publisher());

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2, book3));

        List<String> report = bookService.generateAuthorReport();

        assertEquals(2, report.size());
        assertTrue(report.contains("Author One: 2 books"));
        assertTrue(report.contains("Author Two: 1 books"));
        verify(bookRepository, times(1)).findAll();
    }
}