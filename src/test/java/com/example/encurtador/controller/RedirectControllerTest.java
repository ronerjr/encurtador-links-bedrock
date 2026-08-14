package com.example.encurtador.controller;

import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;
import com.example.encurtador.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RedirectControllerTest {

    private LinkService linkService;
    private RedirectController controller;
    private HttpRequest request;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        linkService = mock(LinkService.class);
        controller = new RedirectController(linkService);
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
    }

    @Test
    void shouldRedirectWhenLinkExists() {
        String hash = "hash12";
        String originalUrl = "https://example.com";
        
        when(request.getPathParameter("hash")).thenReturn(hash);
        when(linkService.getOriginalUrl(hash)).thenReturn(originalUrl);

        controller.redirect(request, response);

        verify(response).setStatus(302);
        verify(response).setHeader("Location", originalUrl);
    }

    @Test
    void shouldReturn404WhenLinkDoesNotExist() {
        String hash = "invalid";
        
        when(request.getPathParameter("hash")).thenReturn(hash);
        when(linkService.getOriginalUrl(hash)).thenReturn(null);

        controller.redirect(request, response);

        verify(response).setStatus(404);
        verify(response).setBody("Link não encontrado");
    }
}
