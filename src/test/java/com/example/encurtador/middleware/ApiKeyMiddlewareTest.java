package com.example.encurtador.middleware;

import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiKeyMiddlewareTest {

    private ApiKeyMiddleware middleware;
    private HttpRequest request;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        middleware = new ApiKeyMiddleware();
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
    }

    @Test
    void shouldBlockRequestToApiWithInvalidKey() {
        when(request.getPath()).thenReturn("/api/links");
        when(request.getHeader("X-API-KEY")).thenReturn("invalid-key");

        boolean result = middleware.handle(request, response);

        assertFalse(result);
        verify(response).setStatus(401);
        verify(response).setBody(contains("Unauthorized"));
    }

    @Test
    void shouldBlockRequestToApiWithMissingKey() {
        when(request.getPath()).thenReturn("/api/links");
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        boolean result = middleware.handle(request, response);

        assertFalse(result);
        verify(response).setStatus(401);
    }

    @Test
    void shouldAllowRequestToApiWithValidKey() {
        when(request.getPath()).thenReturn("/api/links");
        when(request.getHeader("X-API-KEY")).thenReturn("chave-secreta-bedrock");

        boolean result = middleware.handle(request, response);

        assertTrue(result);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    void shouldAllowRequestToPublicRouteWithoutKey() {
        when(request.getPath()).thenReturn("/go/hash12");

        boolean result = middleware.handle(request, response);

        assertTrue(result);
        verify(response, never()).setStatus(anyInt());
    }
}
