package com.example.encurtador.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkRepositoryTest {
    
    private LinkRepository repository;

    @BeforeEach
    void setUp() {
        repository = new LinkRepository();
    }

    @Test
    void shouldSaveAndFindUrl() {
        repository.save("hash123", "https://example.com");
        
        String url = repository.findUrl("hash123");
        assertEquals("https://example.com", url);
    }

    @Test
    void shouldInitializeMetricsOnSave() {
        repository.save("hash123", "https://example.com");
        
        int metrics = repository.getMetric("hash123");
        assertEquals(0, metrics);
    }

    @Test
    void shouldIncrementMetrics() {
        repository.save("hash123", "https://example.com");
        repository.incrementMetric("hash123");
        repository.incrementMetric("hash123");
        
        int metrics = repository.getMetric("hash123");
        assertEquals(2, metrics);
    }

    @Test
    void shouldReturnNullForNonExistentHash() {
        assertNull(repository.findUrl("invalid"));
    }
}
