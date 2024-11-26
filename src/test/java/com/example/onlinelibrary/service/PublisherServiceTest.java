package com.example.onlinelibrary.service;

import com.example.onlinelibrary.entity.Publisher;
import com.example.onlinelibrary.repository.PublisherRepository;
import com.example.onlinelibrary.serviceImpl.PublisherServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService; // Assuming this is the implementation class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePublisher() {
        Publisher publisher = new Publisher(null, "Sample Publisher", null);
        Publisher savedPublisher = new Publisher(1L, "Sample Publisher", null);

        when(publisherRepository.save(any(Publisher.class))).thenReturn(savedPublisher);

        Publisher createdPublisher = publisherService.createPublisher(publisher);

        assertNotNull(createdPublisher);
        assertEquals("Sample Publisher", createdPublisher.getName());
        assertEquals(1L, createdPublisher.getId());
    }

    @Test
    void testGetAllPublishers() {
        Publisher publisher1 = new Publisher(1L, "Publisher 1", null);
        Publisher publisher2 = new Publisher(2L, "Publisher 2", null);

        when(publisherRepository.findAll()).thenReturn(Arrays.asList(publisher1, publisher2));

        List<Publisher> publishers = publisherService.getAllPublishers();

        assertNotNull(publishers);
        assertEquals(2, publishers.size());
    }

    @Test
    void testGetPublisherById() {
        Publisher publisher = new Publisher(1L, "Sample Publisher", null);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));

        Publisher foundPublisher = publisherService.getPublisherById(1L);

        assertNotNull(foundPublisher);
        assertEquals("Sample Publisher", foundPublisher.getName());
    }

    @Test
    void testGetPublisherByIdNotFound() {
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        Publisher foundPublisher = publisherService.getPublisherById(999L);

        assertNull(foundPublisher);
    }

    @Test
    void testUpdatePublisher() {
        Publisher existingPublisher = new Publisher(1L, "Old Publisher", null);
        Publisher updatedPublisher = new Publisher(1L, "Updated Publisher", null);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(existingPublisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(updatedPublisher);

        Publisher result = publisherService.updatePublisher(1L, updatedPublisher);

        assertNotNull(result);
        assertEquals("Updated Publisher", result.getName());
    }

    @Test
    void testUpdatePublisherNotFound() {
        Publisher updatedPublisher = new Publisher(1L, "Updated Publisher", null);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        Publisher result = publisherService.updatePublisher(999L, updatedPublisher);

        assertNull(result);
    }

    @Test
    void testDeletePublisher() {
        doNothing().when(publisherRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> publisherService.deletePublisher(1L));
        verify(publisherRepository, times(1)).deleteById(1L);
    }
}