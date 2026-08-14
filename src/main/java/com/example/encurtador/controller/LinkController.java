package com.example.encurtador.controller;

import com.bedrock.web.BedrockController;
import com.bedrock.web.BedrockGet;
import com.bedrock.core.Context;
import com.example.encurtador.service.LinkService;

@BedrockController
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    // Utilizando BedrockGet e queryParam já que BedrockPost ainda não foi implementado no core
    @BedrockGet("/api/links/create")
    public void create(Context ctx) {
        String originalUrl = ctx.queryParam("url"); 
        if (originalUrl == null || originalUrl.isEmpty()) {
            ctx.badRequest("{\"error\": \"Missing 'url' query param\"}");
            return;
        }
        
        String hash = linkService.createShortLink(originalUrl);
        ctx.created("{\"hash\": \"" + hash + "\", \"shortUrl\": \"http://localhost:8080/go/" + hash + "\"}");
    }

    @BedrockGet("/api/links/{hash}/metrics")
    public void metrics(Context ctx) {
        String hash = ctx.pathParam("hash");
        int clicks = linkService.getLinkMetrics(hash);
        ctx.ok("{\"hash\": \"" + hash + "\", \"clicks\": " + clicks + "}");
    }
}
