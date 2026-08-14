package com.example.encurtador.service;

import bedrock.annotations.BedrockComponent;
import com.example.encurtador.repository.LinkRepository;
import java.util.UUID;

@BedrockComponent
public class LinkService {
    private final LinkRepository linkRepository;

    // Recebe o LinkRepository via injeção de construtor
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String createShortLink(String originalUrl) {
        // Gera um hash aleatório de 6 caracteres
        String hash = UUID.randomUUID().toString().substring(0, 6);
        linkRepository.save(hash, originalUrl);
        return hash;
    }

    public String getOriginalUrl(String hash) {
        String url = linkRepository.findUrl(hash);
        if (url != null) {
            // Incrementa a métrica de acessos
            linkRepository.incrementMetric(hash);
        }
        return url;
    }

    public int getLinkMetrics(String hash) {
        return linkRepository.getMetric(hash);
    }
}
