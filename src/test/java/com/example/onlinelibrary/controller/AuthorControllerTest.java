package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.entity.Author;
import com.example.onlinelibrary.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testCreateAuthor() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\"}")) // Only send the name since ID is auto-generated
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAllAuthors() throws Exception {
        Author author1 = new Author(1L, "John Doe", new HashSet<>());
        Author author2 = new Author(2L, "Jane Doe", new HashSet<>());
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author1, author2));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAuthorById() throws Exception {
        Author author = new Author(1L, "John Doe", new HashSet<>());
        when(authorService.getAuthorById(anyLong())).thenReturn(author);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAuthorByIdNotFound() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/authors/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAuthor() throws Exception {
        Author existingAuthor = new Author(1L, "John Doe", new HashSet<>());
        Author updatedAuthor = new Author(1L, "John Smith", new HashSet<>());
        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Smith\"}")) // Only send the updated name
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"));
    }

    @Test
    void testUpdateAuthorNotFound() throws Exception {
        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(null);

        mockMvc.perform(put("/api/authors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Smith\"}")) // Only send the updated name
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(anyLong());

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }
}