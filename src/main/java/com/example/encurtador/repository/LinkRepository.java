package com.example.encurtador.repository;

import bedrock.annotations.BedrockComponent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@BedrockComponent
public class LinkRepository {
    // ConcurrentHashMap para manter tudo na memória (Zero Dependências)
    private final Map<String, String> links = new ConcurrentHashMap<>();
    private final Map<String, Integer> metrics = new ConcurrentHashMap<>();

    public void save(String hash, String url) {
        links.put(hash, url);
        metrics.put(hash, 0); // Inicializa a contagem de acessos
    }

    public String findUrl(String hash) {
        return links.get(hash);
    }

    public void incrementMetric(String hash) {
        metrics.computeIfPresent(hash, (k, v) -> v + 1);
    }

    public int getMetric(String hash) {
        return metrics.getOrDefault(hash, 0);
    }
}
