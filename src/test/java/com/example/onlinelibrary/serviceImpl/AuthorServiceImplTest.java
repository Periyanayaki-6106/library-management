package com.example.onlinelibrary.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.onlinelibrary.entity.Author;
import com.example.onlinelibrary.repository.AuthorRepository;

class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author();
        author.setName("John Doe");

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author createdAuthor = authorService.createAuthor(author);

        assertNotNull(createdAuthor);
        assertEquals("John Doe", createdAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testGetAllAuthors() {
        Author author1 = new Author();
        author1.setName("Author One");

        Author author2 = new Author();
        author2.setName("Author Two");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.getAllAuthors();

        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        author.setName("John Doe");

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.getAuthorById(1L);

        assertNotNull(foundAuthor);
        assertEquals("John Doe", foundAuthor.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAuthor() {
        Author existingAuthor = new Author();
        existingAuthor.setName("Old Name");

        Author updatedAuthor = new Author();
        updatedAuthor.setName("New Name");

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        Author result = authorService.updateAuthor(1L, updatedAuthor);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(existingAuthor);
    }
    @Test
    void testUpdateAuthor_NotFound() {
        // Arrange: Mock the behavior of the repository to return an empty Optional
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act: Call the updateAuthor method
        Author result = authorService.updateAuthor(1L, new Author());

        // Assert: Verify that the result is null
        assertNull(result);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, never()).save(any(Author.class)); // Ensure save was never called
    }

    @Test
    void testDeleteAuthor() {
        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}