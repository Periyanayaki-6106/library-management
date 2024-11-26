//package com.example.onlinelibrary.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.example.onlinelibrary.entity.Author;
//import com.example.onlinelibrary.entity.Book;
//import com.example.onlinelibrary.entity.Publisher;
//
////@DataJpaTest
//public class BookRepoTest {
	
//	@Autowired
//    private BookRepository bookRepository;
//
//    private Book book;
//
//    @BeforeEach
//    public void setUp() {
//        Author author = new Author();
//        author.setName("Author Name");
//       
//
//        book = new Book();
//        book.setTitle("Test Book");
//        book.setAuthor(author); // Set the Author object
////        book.setPublisher("Publisher Name");
//    }
//
//    @Test
//    public void testSaveBook() {
//        Book savedBook = bookRepository.save(book);
//        assertThat(savedBook).isNotNull();
//        assertThat(savedBook.getId()).isGreaterThan(0);
//    }
//
//    @Test
//    public void testFindBookById() {
//        Book savedBook = bookRepository.save(book);
//        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
//        assertThat(foundBook).isPresent();
//        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
//    }

//    @Test
//    public void testUpdateBook() {
//        Book savedBook = bookRepository.save(book);
//        savedBook.setTitle("Updated Book Title");
//        Book updatedBook = bookRepository
//}
//}
