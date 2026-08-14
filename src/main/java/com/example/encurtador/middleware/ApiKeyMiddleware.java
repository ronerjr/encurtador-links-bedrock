package com.example.encurtador.middleware;

import bedrock.middleware.Middleware;
import bedrock.http.HttpRequest;
import bedrock.http.HttpResponse;

public class ApiKeyMiddleware implements Middleware {
    
    private static final String API_KEY = "chave-secreta-bedrock";

    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        // Checa o Header da requisição, por exemplo, protegendo a API de criação
        if (request.getPath().startsWith("/api/")) {
            String headerKey = request.getHeader("X-API-KEY");
            if (headerKey == null || !headerKey.equals(API_KEY)) {
                response.setStatus(401);
                response.setBody("Unauthorized - Invalid ou Missing API Key");
                return false; // Interrompe a execução
            }
        }
        return true; // Permite prosseguir para o controller
    }
}
