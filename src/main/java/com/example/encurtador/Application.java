package com.example.encurtador;

import com.bedrock.core.BedrockApp;
import com.example.encurtador.controller.LinkController;
import com.example.encurtador.controller.RedirectController;
import com.example.encurtador.middleware.ApiKeyMiddleware;

public class Application {
    public static void main(String[] args) {
        BedrockApp app = BedrockApp.create(8080);
        
        app.before(new ApiKeyMiddleware());
        app.bindControllers(LinkController.class, RedirectController.class);
        app.start();
        
        System.out.println("Encurtador de Links com Bedrock rodando na porta 8080!");
    }
}
