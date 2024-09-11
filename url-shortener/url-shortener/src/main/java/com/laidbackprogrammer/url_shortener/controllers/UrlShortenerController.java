package com.laidbackprogrammer.url_shortener.controllers;

import com.laidbackprogrammer.url_shortener.service.URLShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

    @Autowired
    private URLShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String longUrl) {
        logger.info("Received request to shorten URL: {}", longUrl);
        String shortCode = urlShortenerService.shortenURL(longUrl);
        logger.info("Shortened URL to code: {}", shortCode);
        return shortCode;
    }

    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse httpServletResponse) throws IOException {
        logger.info("Received request to redirect short code: {}", shortCode);
        String longUrl = urlShortenerService.getLongUrl(shortCode);
        if (longUrl != null) {
            logger.info("Redirecting to original URL: {}", longUrl);
            httpServletResponse.sendRedirect(longUrl);
        } else {
            logger.warn("Short code not found: {}", shortCode);
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found");
        }
    }
}
