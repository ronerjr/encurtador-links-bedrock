package com.example.encurtador.service;

import com.example.encurtador.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LinkServiceTest {

    private LinkRepository repository;
    private LinkService service;

    @BeforeEach
    void setUp() {
        repository = mock(LinkRepository.class);
        service = new LinkService(repository);
    }

    @Test
    void shouldCreateShortLink() {
        String originalUrl = "https://example.com";
        String hash = service.createShortLink(originalUrl);

        assertNotNull(hash);
        assertEquals(6, hash.length());
        
        verify(repository, times(1)).save(hash, originalUrl);
    }

    @Test
    void shouldReturnOriginalUrlAndIncrementMetrics() {
        String hash = "hash12";
        String originalUrl = "https://example.com";
        
        when(repository.findUrl(hash)).thenReturn(originalUrl);

        String result = service.getOriginalUrl(hash);

        assertEquals(originalUrl, result);
        verify(repository, times(1)).incrementMetric(hash);
    }

    @Test
    void shouldReturnNullWhenHashDoesNotExist() {
        String hash = "invalid";
        when(repository.findUrl(hash)).thenReturn(null);

        String result = service.getOriginalUrl(hash);

        assertNull(result);
        verify(repository, never()).incrementMetric(hash);
    }

    @Test
    void shouldReturnMetrics() {
        String hash = "hash12";
        when(repository.getMetric(hash)).thenReturn(5);

        int clicks = service.getLinkMetrics(hash);

        assertEquals(5, clicks);
    }
}
