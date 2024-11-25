package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.entity.Publisher;
import com.example.onlinelibrary.service.PublisherService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController).build();
    }

    @Test
    void testCreatePublisher() throws Exception {
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());

        when(publisherService.createPublisher(any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(post("/api/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Sample Publisher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Publisher"));
    }

    @Test
    void testGetAllPublishers() throws Exception {
        Publisher publisher1 = new Publisher(1L, "Sample Publisher 1", new HashSet<>());
        Publisher publisher2 = new Publisher(2L, "Sample Publisher 2", new HashSet<>());

        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisher1, publisher2));

        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetPublisherById() throws Exception {
        Publisher publisher = new Publisher(1L, "Sample Publisher", new HashSet<>());

        when(publisherService.getPublisherById(anyLong())).thenReturn(publisher);

        mockMvc.perform(get("/api/publishers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Publisher"));
    }

    @Test
    void testGetPublisherByIdNotFound() throws Exception {
        when(publisherService.getPublisherById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/publishers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePublisher() throws Exception {
        Publisher existingPublisher = new Publisher(1L, "Sample Publisher", new HashSet<>());
        Publisher updatedPublisher = new Publisher(1L, "Updated Publisher", new HashSet<>());

        when(publisherService.updatePublisher(anyLong(), any(Publisher.class))).thenReturn(updatedPublisher);

        mockMvc.perform(put("/api/publishers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Publisher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Publisher"));
    }

    @Test
    void testUpdatePublisherNotFound() throws Exception {
        when(publisherService.updatePublisher(anyLong(), any(Publisher.class))).thenReturn(null);

        mockMvc.perform(put("/api/publishers/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Publisher\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePublisher() throws Exception {
        doNothing().when(publisherService).deletePublisher(anyLong());

        mockMvc.perform(delete("/api/publishers/1"))
                .andExpect(status().isNoContent());
    }
}