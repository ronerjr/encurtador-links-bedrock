package com.example.encurtador.controller;

import com.bedrock.web.BedrockController;
import com.bedrock.web.BedrockGet;
import com.bedrock.core.Context;
import com.example.encurtador.service.LinkService;

@BedrockController
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @BedrockGet("/go/{hash}")
    public void redirect(Context ctx) {
        String hash = ctx.pathParam("hash");
        String originalUrl = linkService.getOriginalUrl(hash);

        if (originalUrl != null) {
            // Como o Bedrock Context ainda não tem um ctx.redirect explícito,
            // podemos mandar um HTML de redirecionamento para funcionar no navegador
            ctx.html("<meta http-equiv=\"refresh\" content=\"0; url=" + originalUrl + "\" />");
        } else {
            ctx.notFound("Link não encontrado");
        }
    }
}
