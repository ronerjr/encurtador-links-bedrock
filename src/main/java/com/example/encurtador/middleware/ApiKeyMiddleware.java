package com.example.encurtador.middleware;

import com.bedrock.core.Middleware;
import com.bedrock.core.Context;

public class ApiKeyMiddleware implements Middleware {
    
    private static final String API_KEY = "chave-secreta-bedrock";

    @Override
    public void handle(Context ctx) throws Exception {
        if (ctx.path().startsWith("/api/")) {
            // Validando via Query Param "key" já que Context não tem getHeader
            String key = ctx.queryParam("key");
            if (key == null || !key.equals(API_KEY)) {
                ctx.badRequest("Unauthorized - Invalid API Key in 'key' param");
                throw new Exception("Unauthorized API Access");
            }
        }
    }
}
