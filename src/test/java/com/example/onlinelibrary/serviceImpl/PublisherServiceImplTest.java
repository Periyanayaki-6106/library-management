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

import com.example.onlinelibrary.entity.Publisher;
import com.example.onlinelibrary.repository.PublisherRepository;

class PublisherServiceImplTest {

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher createdPublisher = publisherService.createPublisher(publisher);

        assertNotNull(createdPublisher);
        assertEquals("Publisher Name", createdPublisher.getName());
        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void testGetAllPublishers() {
        Publisher publisher1 = new Publisher();
        publisher1.setName("Publisher One");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Publisher Two");

        when(publisherRepository.findAll()).thenReturn(Arrays.asList(publisher1, publisher2));

        List<Publisher> publishers = publisherService.getAllPublishers();

        assertEquals(2, publishers.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void testGetPublisherById() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher Name");

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));

        Publisher foundPublisher = publisherService.getPublisherById(1L);

        assertNotNull(foundPublisher);
        assertEquals("Publisher Name", foundPublisher.getName());
        verify(publisherRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePublisher() {
        Publisher existingPublisher = new Publisher();
        existingPublisher.setName("Old Publisher");

        Publisher updatedPublisherDetails = new Publisher();
        updatedPublisherDetails.setName("New Publisher");

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(existingPublisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(existingPublisher);

        Publisher updatedPublisher = publisherService.updatePublisher(1L, updatedPublisherDetails);

        assertNotNull(updatedPublisher);
        assertEquals("New Publisher", updatedPublisher.getName());
        verify(publisherRepository, times(1)).findById(1L);
        verify(publisherRepository, times(1)).save(existingPublisher);
    }

    @Test
    void testDeletePublisher() {
        doNothing().when(publisherRepository).deleteById(anyLong());

        publisherService.deletePublisher(1L);

        verify(publisherRepository, times(1)).deleteById(1L);
    }
}