package com.example.encurtador.controller;

import com.bedrock.web.BedrockController;
import com.bedrock.web.BedrockGet;
import com.bedrock.core.Context;
import com.example.encurtador.service.LinkService;
import java.util.Map;

@BedrockController
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    // Ajustado para criar as caixinhas de 'key' e 'url' magicamente no BedrockPlayground!
    @BedrockGet("/api/links/create/{key}/{url}")
    public void create(Context ctx) {
        String domainUrl = ctx.pathParam("url"); 
        if (domainUrl == null || domainUrl.isEmpty()) {
            ctx.badRequest("Missing 'url' path param");
            return;
        }
        
        // Evita quebra de rotas no framework por causa das barras do http://
        String originalUrl = domainUrl.startsWith("http") ? domainUrl : "https://" + domainUrl;
        
        String hash = linkService.createShortLink(originalUrl);
        ctx.created(Map.of(
            "hash", hash,
            "shortUrl", "http://localhost:8080/go/" + hash
        ));
    }

    @BedrockGet("/api/links/metrics/{key}/{hash}")
    public void metrics(Context ctx) {
        String hash = ctx.pathParam("hash");
        int clicks = linkService.getLinkMetrics(hash);
        ctx.ok(Map.of(
            "hash", hash,
            "clicks", clicks
        ));
    }
}
