package com.example.encurtador;

import bedrock.core.BedrockApp;
import com.example.encurtador.middleware.ApiKeyMiddleware;

public class Application {
    public static void main(String[] args) {
        BedrockApp app = new BedrockApp();
        
        // Registra o middleware de segurança globalmente
        app.before(new ApiKeyMiddleware());
        
        // Inicia a aplicação na porta 8080
        app.start(8080);
        
        System.out.println("Encurtador de Links com Bedrock rodando na porta 8080!");
    }
}
