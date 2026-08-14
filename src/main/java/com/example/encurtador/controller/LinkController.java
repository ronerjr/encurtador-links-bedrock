package com.example.encurtador.controller;

import bedrock.annotations.BedrockController;
import bedrock.annotations.BedrockPost;
import bedrock.annotations.BedrockGet;
import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;
import com.example.encurtador.service.LinkService;

@BedrockController
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @BedrockPost("/api/links")
    public void create(HttpRequest request, HttpResponse response) {
        // Exemplo simplificado: considerando que a URL seja enviada no corpo da requisição
        String originalUrl = request.getBodyAsString(); 
        String hash = linkService.createShortLink(originalUrl);
        
        response.setStatus(201);
        response.setBody("{\"hash\": \"" + hash + "\", \"shortUrl\": \"http://localhost:8080/go/" + hash + "\"}");
    }

    @BedrockGet("/api/links/{hash}/metrics")
    public void metrics(HttpRequest request, HttpResponse response) {
        String hash = request.getPathParameter("hash");
        int clicks = linkService.getLinkMetrics(hash);
        
        response.setStatus(200);
        response.setBody("{\"hash\": \"" + hash + "\", \"clicks\": " + clicks + "}");
    }
}
