//package com.example.onlinelibrary.repository;
//
//import com.example.onlinelibrary.entity.Author;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class AuthorRepoTest {
//
//    @Autowired
//    private AuthorRepository authorRepository;
//
//    private Author author;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize an Author object before each test
//        author = new Author();
//        author.setName("Sample Author");
//    }
//
//    @Test
//    @Rollback(false) // Change to true if you want the database to roll back after the test
//    void testSaveAuthor() {
//        Author savedAuthor = authorRepository.save(author);
//        assertNotNull(savedAuthor);
//        assertNotNull(savedAuthor.getId());
//        assertEquals("Sample Author", savedAuthor.getName());
//    }
//
//    @Test
//    void testFindAuthorById() {
//        Author savedAuthor = authorRepository.save(author);
//        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
//
//        assertTrue(foundAuthor.isPresent());
//        assertEquals("Sample Author", foundAuthor.get().getName());
//    }
//
//    @Test
//    void testFindAuthorByIdNotFound() {
//        Optional<Author> foundAuthor = authorRepository.findById(999L);
//        assertFalse(foundAuthor.isPresent());
//    }
//
//    @Test
//    @Rollback(false) // Change to true if you want the database to roll back after the test
//    void testDeleteAuthor() {
//        Author savedAuthor = authorRepository.save(author);
//        authorRepository.deleteById(savedAuthor.getId());
//
//        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());
//        assertFalse(foundAuthor.isPresent());
//    }
//
//    @Test
//    void testUpdateAuthor() {
//        Author savedAuthor = authorRepository.save(author);
//        savedAuthor.setName("Updated Author");
//
//        Author updatedAuthor = authorRepository.save(savedAuthor);
//        assertEquals("Updated Author", updatedAuthor.getName());
//    }
//}