package com.example.encurtador.service;

import com.bedrock.ioc.BedrockComponent;
import com.example.encurtador.repository.LinkRepository;
import java.util.UUID;

@BedrockComponent
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String createShortLink(String originalUrl) {
        String hash = UUID.randomUUID().toString().substring(0, 6);
        linkRepository.save(hash, originalUrl);
        return hash;
    }

    public String getOriginalUrl(String hash) {
        String url = linkRepository.findUrl(hash);
        if (url != null) {
            linkRepository.incrementMetric(hash);
        }
        return url;
    }

    public int getLinkMetrics(String hash) {
        return linkRepository.getMetric(hash);
    }
}
