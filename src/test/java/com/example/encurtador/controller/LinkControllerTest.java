package com.example.encurtador.controller;

import com.bedrock.core.Context;
import com.example.encurtador.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LinkControllerTest {

    private LinkService linkService;
    private LinkController controller;
    private Context ctx;

    @BeforeEach
    void setUp() {
        linkService = mock(LinkService.class);
        controller = new LinkController(linkService);
        ctx = mock(Context.class);
    }

    @Test
    void shouldCreateLinkSuccessfully() {
        String originalUrl = "https://example.com";
        String generatedHash = "hash12";
        
        when(ctx.queryParam("url")).thenReturn(originalUrl);
        when(linkService.createShortLink(originalUrl)).thenReturn(generatedHash);

        controller.create(ctx);

        verify(ctx).created(contains(generatedHash));
    }

    @Test
    void shouldReturnMetricsSuccessfully() {
        String hash = "hash12";
        int clicks = 10;
        
        when(ctx.pathParam("hash")).thenReturn(hash);
        when(linkService.getLinkMetrics(hash)).thenReturn(clicks);

        controller.metrics(ctx);

        verify(ctx).ok(contains("\"clicks\": 10"));
    }
}
