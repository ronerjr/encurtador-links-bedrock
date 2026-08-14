package com.example.encurtador.repository;

import com.bedrock.ioc.BedrockComponent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@BedrockComponent
public class LinkRepository {
    private final Map<String, String> links = new ConcurrentHashMap<>();
    private final Map<String, Integer> metrics = new ConcurrentHashMap<>();

    public void save(String hash, String url) {
        links.put(hash, url);
        metrics.put(hash, 0);
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
