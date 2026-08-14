package com.example.encurtador.controller;

import com.bedrock.core.Context;
import com.example.encurtador.service.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RedirectControllerTest {

    private LinkService linkService;
    private RedirectController controller;
    private Context ctx;

    @BeforeEach
    void setUp() {
        linkService = mock(LinkService.class);
        controller = new RedirectController(linkService);
        ctx = mock(Context.class);
    }

    @Test
    void shouldRedirectWhenLinkExists() {
        String hash = "hash12";
        String originalUrl = "https://example.com";
        
        when(ctx.pathParam("hash")).thenReturn(hash);
        when(linkService.getOriginalUrl(hash)).thenReturn(originalUrl);

        controller.redirect(ctx);

        verify(ctx).html(contains("meta http-equiv=\"refresh\""));
    }

    @Test
    void shouldReturn404WhenLinkDoesNotExist() {
        String hash = "invalid";
        
        when(ctx.pathParam("hash")).thenReturn(hash);
        when(linkService.getOriginalUrl(hash)).thenReturn(null);

        controller.redirect(ctx);

        verify(ctx).notFound("Link não encontrado");
    }
}
