package com.example.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.onlinelibrary.entity.Author;
import com.example.onlinelibrary.repository.AuthorRepository;
import com.example.onlinelibrary.serviceImpl.AuthorServiceImpl;

public class AuthorServiceTest {
	@Mock
    private AuthorRepository authorRepository; // Mock the repository

    @InjectMocks
    private AuthorServiceImpl authorService; // Inject mocks into the service implementation

    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
    }

    @Test
    public void testCreateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author createdAuthor = authorService.createAuthor(author);

        assertNotNull(createdAuthor);
        assertEquals("Test Author", createdAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Author", result.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorByIdFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        Author foundAuthor = authorService.getAuthorById(1L);

        assertNotNull(foundAuthor);
        assertEquals("Test Author", foundAuthor.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        Author foundAuthor = authorService.getAuthorById(999L);

        assertNull(foundAuthor);
        verify(authorRepository, times(1)).findById(999L);
    }

//    @Test
//    public void testUpdateAuthor() {
//        Author updatedAuthor = new Author();
//        updatedAuthor.setName("Updated Author");
//
//        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
//        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);
//
//        Author result = authorService.updateAuthor(1L, updatedAuthor);
//
//        assertNotNull(result);
//        assertEquals("Updated Author", result.getName());
//        verify(authorRepository, times(1)).findById(1L);
//        verify(authorRepository, times(1)).save(updatedAuthor);
//    }

    @Test
    public void testDeleteAuthor() {
        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

}
