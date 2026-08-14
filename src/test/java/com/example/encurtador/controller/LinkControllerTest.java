package com.example.encurtador.controller;

import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;
import com.example.encurtador.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LinkControllerTest {

    private LinkService linkService;
    private LinkController controller;
    private HttpRequest request;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        linkService = mock(LinkService.class);
        controller = new LinkController(linkService);
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
    }

    @Test
    void shouldCreateLinkSuccessfully() {
        String originalUrl = "https://example.com";
        String generatedHash = "hash12";
        
        when(request.getBodyAsString()).thenReturn(originalUrl);
        when(linkService.createShortLink(originalUrl)).thenReturn(generatedHash);

        controller.create(request, response);

        verify(response).setStatus(201);
        verify(response).setBody(contains(generatedHash));
    }

    @Test
    void shouldReturnMetricsSuccessfully() {
        String hash = "hash12";
        int clicks = 10;
        
        when(request.getPathParameter("hash")).thenReturn(hash);
        when(linkService.getLinkMetrics(hash)).thenReturn(clicks);

        controller.metrics(request, response);

        verify(response).setStatus(200);
        verify(response).setBody(contains("\"clicks\": 10"));
    }
}
