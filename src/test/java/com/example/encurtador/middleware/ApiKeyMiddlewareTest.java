package com.example.encurtador.middleware;

import com.bedrock.core.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiKeyMiddlewareTest {

    private ApiKeyMiddleware middleware;
    private Context ctx;

    @BeforeEach
    void setUp() {
        middleware = new ApiKeyMiddleware();
        ctx = mock(Context.class);
    }

    @Test
    void shouldBlockRequestToApiWithInvalidKey() {
        when(ctx.path()).thenReturn("/api/links");
        when(ctx.queryParam("key")).thenReturn("invalid-key");

        assertThrows(Exception.class, () -> middleware.handle(ctx));
        verify(ctx).badRequest(contains("Unauthorized"));
    }

    @Test
    void shouldBlockRequestToApiWithMissingKey() {
        when(ctx.path()).thenReturn("/api/links");
        when(ctx.queryParam("key")).thenReturn(null);

        assertThrows(Exception.class, () -> middleware.handle(ctx));
        verify(ctx).badRequest(contains("Unauthorized"));
    }

    @Test
    void shouldAllowRequestToApiWithValidKey() throws Exception {
        when(ctx.path()).thenReturn("/api/links");
        when(ctx.queryParam("key")).thenReturn("chave-secreta-bedrock");

        assertDoesNotThrow(() -> middleware.handle(ctx));
        verify(ctx, never()).badRequest(anyString());
    }

    @Test
    void shouldAllowRequestToPublicRouteWithoutKey() throws Exception {
        when(ctx.path()).thenReturn("/go/hash12");

        assertDoesNotThrow(() -> middleware.handle(ctx));
        verify(ctx, never()).badRequest(anyString());
    }
}
