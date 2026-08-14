package com.example.encurtador.middleware;

import com.bedrock.core.Middleware;
import com.bedrock.core.Context;

public class ApiKeyMiddleware implements Middleware {
    
    private static final String API_KEY = "chave-secreta-bedrock";

    @Override
    public void handle(Context ctx) throws Exception {
        if (ctx.path().startsWith("/api/")) {
            String key = ctx.queryParam("key");
            
            // Suporte para o BedrockPlayground ler a chave direto da URL
            if (key == null || key.isEmpty()) {
                key = ctx.pathParam("key");
            }

            if (key == null || !key.equals(API_KEY)) {
                ctx.badRequest("Unauthorized - Invalid API Key");
                return;
            }
        }
    }
}
