package com.laidbackprogrammer.url_shortener.service;

import com.laidbackprogrammer.url_shortener.model.URLMapping;
import com.laidbackprogrammer.url_shortener.repository.URLMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class URLShortenerService {

    private static final Logger logger = LoggerFactory.getLogger(URLShortenerService.class);

    @Autowired
    private URLMappingRepository urlMappingRepository;

    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final Random random = new Random();

    @Transactional
    public String shortenURL(String longUrl) {
        logger.info("Received request to shorten URL: {}", longUrl);

        Optional<URLMapping> existingMapping = urlMappingRepository.findByLongUrl(longUrl);
        if (existingMapping.isPresent()) {
            logger.info("URL already exists. Returning existing short code: {}", existingMapping.get().getShortCode());
            return existingMapping.get().getShortCode();
        }

        String shortCode;
        do {
            shortCode = generateShortCode();
            logger.debug("Generated short code: {}", shortCode);
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());

        URLMapping urlMapping = URLMapping.builder()
                .longUrl(longUrl)
                .shortCode(shortCode)
                .build();

        urlMappingRepository.save(urlMapping);
        logger.info("Saved new short code {} for URL: {}", shortCode, longUrl);

        return shortCode;
    }

    public String getLongUrl(String shortCode) {
        logger.info("Received request to retrieve long URL for short code: {}", shortCode);

        return urlMappingRepository.findByShortCode(shortCode)
                .map(urlMapping -> {
                    logger.info("Found long URL for short code {}: {}", shortCode, urlMapping.getLongUrl());
                    return urlMapping.getLongUrl();
                })
                .orElseGet(() -> {
                    logger.warn("No long URL found for short code: {}", shortCode);
                    return null;
                });
    }

    private String generateShortCode() {
        StringBuilder code = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            code.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return code.toString();
    }
}
