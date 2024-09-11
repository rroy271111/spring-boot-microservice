package com.laidbackprogrammer.url_shortener.controllers;

import com.laidbackprogrammer.url_shortener.service.URLShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLShortenerService urlShortenerService;

    @Test
    public void urlShortenerTest() throws Exception {
        // Mock the behavior of the service to shorten the URL
        given(urlShortenerService.shortenURL("https://www.openai.com/research/gpt-4")).willReturn("shortCode123");

        mockMvc.perform(post("/api/url/shorten")
                        .param("longUrl", "https://www.openai.com/research/gpt-4"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectToOriginalUrl() throws Exception {
        // Mock the service to return a valid long URL when the short code is looked up
        given(urlShortenerService.getLongUrl("shortCode123")).willReturn("https://www.openai.com/research/gpt-4");

        mockMvc.perform(get("/api/url/{shortCode}", "shortCode123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://www.openai.com/research/gpt-4"));
    }
}
