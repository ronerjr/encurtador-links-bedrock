package com.example.encurtador.controller;

import bedrock.annotations.BedrockController;
import bedrock.annotations.BedrockGet;
import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;
import com.example.encurtador.service.LinkService;

@BedrockController
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @BedrockGet("/go/{hash}")
    public void redirect(HttpRequest request, HttpResponse response) {
        String hash = request.getPathParameter("hash");
        String originalUrl = linkService.getOriginalUrl(hash);

        if (originalUrl != null) {
            // Devolve Status 302 (Redirect) com o cabeçalho Location
            response.setStatus(302);
            response.setHeader("Location", originalUrl);
        } else {
            response.setStatus(404);
            response.setBody("Link não encontrado");
        }
    }
}
