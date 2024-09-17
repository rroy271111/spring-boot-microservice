package com.laidbackprogrammer.url_shortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laidbackprogrammer.url_shortener.model.URLMapping;
import com.laidbackprogrammer.url_shortener.repository.URLMappingRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {URLShortenerService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class URLShortenerServiceTest {
    @MockBean
    private URLMappingRepository uRLMappingRepository;

    @Autowired
    private URLShortenerService uRLShortenerService;

    /**
     * Method under test: {@link URLShortenerService#shortenURL(String)}
     */
    @Test
    void testShortenURL() {
        // Arrange
        URLMapping urlMapping = new URLMapping();
        urlMapping.setId(1L);
        urlMapping.setLongUrl("https://example.org/example");
        urlMapping.setShortCode("https://example.org/example");
        Optional<URLMapping> ofResult = Optional.of(urlMapping);
        when(uRLMappingRepository.findByLongUrl(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        uRLShortenerService.shortenURL("https://example.org/example");

        // Assert
        verify(uRLMappingRepository).findByLongUrl(eq("https://example.org/example"));
    }

    /**
     * Method under test: {@link URLShortenerService#shortenURL(String)}
     */
    @Test
    void testShortenURL2() {
        // Arrange
        URLMapping urlMapping = mock(URLMapping.class);
        when(urlMapping.getShortCode()).thenReturn("https://example.org/example");
        doNothing().when(urlMapping).setId(Mockito.<Long>any());
        doNothing().when(urlMapping).setLongUrl(Mockito.<String>any());
        doNothing().when(urlMapping).setShortCode(Mockito.<String>any());
        urlMapping.setId(1L);
        urlMapping.setLongUrl("https://example.org/example");
        urlMapping.setShortCode("https://example.org/example");
        Optional<URLMapping> ofResult = Optional.of(urlMapping);
        when(uRLMappingRepository.findByLongUrl(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        uRLShortenerService.shortenURL("https://example.org/example");

        // Assert
        verify(urlMapping, atLeast(1)).getShortCode();
        verify(urlMapping).setId(eq(1L));
        verify(urlMapping).setLongUrl(eq("https://example.org/example"));
        verify(urlMapping).setShortCode(eq("https://example.org/example"));
        verify(uRLMappingRepository).findByLongUrl(eq("https://example.org/example"));
    }

    /**
     * Method under test: {@link URLShortenerService#shortenURL(String)}
     */
    @Test
    void testShortenURL3() {
        // Arrange
        URLMapping urlMapping = new URLMapping();
        urlMapping.setId(1L);
        urlMapping.setLongUrl("https://example.org/example");
        urlMapping.setShortCode("https://example.org/example");
        when(uRLMappingRepository.save(Mockito.<URLMapping>any())).thenReturn(urlMapping);
        Optional<URLMapping> emptyResult = Optional.empty();
        when(uRLMappingRepository.findByLongUrl(Mockito.<String>any())).thenReturn(emptyResult);
        Optional<URLMapping> emptyResult2 = Optional.empty();
        when(uRLMappingRepository.findByShortCode(Mockito.<String>any())).thenReturn(emptyResult2);

        // Act
        uRLShortenerService.shortenURL("https://example.org/example");

        // Assert
        verify(uRLMappingRepository).findByLongUrl(eq("https://example.org/example"));
        verify(uRLMappingRepository).findByShortCode(eq("XvDuT9"));
        verify(uRLMappingRepository).save(isA(URLMapping.class));
    }

    /**
     * Method under test: {@link URLShortenerService#getLongUrl(String)}
     */
    @Test
    void testGetLongUrl() {
        // Arrange
        URLMapping urlMapping = new URLMapping();
        urlMapping.setId(1L);
        urlMapping.setLongUrl("https://example.org/example");
        urlMapping.setShortCode("https://example.org/example");
        Optional<URLMapping> ofResult = Optional.of(urlMapping);
        when(uRLMappingRepository.findByShortCode(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        String actualLongUrl = uRLShortenerService.getLongUrl("https://example.org/example");

        // Assert
        verify(uRLMappingRepository).findByShortCode(eq("https://example.org/example"));
        assertEquals("https://example.org/example", actualLongUrl);
    }

    /**
     * Method under test: {@link URLShortenerService#getLongUrl(String)}
     */
    @Test
    void testGetLongUrl2() {
        // Arrange
        URLMapping urlMapping = mock(URLMapping.class);
        when(urlMapping.getLongUrl()).thenReturn("https://example.org/example");
        doNothing().when(urlMapping).setId(Mockito.<Long>any());
        doNothing().when(urlMapping).setLongUrl(Mockito.<String>any());
        doNothing().when(urlMapping).setShortCode(Mockito.<String>any());
        urlMapping.setId(1L);
        urlMapping.setLongUrl("https://example.org/example");
        urlMapping.setShortCode("https://example.org/example");
        Optional<URLMapping> ofResult = Optional.of(urlMapping);
        when(uRLMappingRepository.findByShortCode(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        String actualLongUrl = uRLShortenerService.getLongUrl("https://example.org/example");

        // Assert
        verify(urlMapping, atLeast(1)).getLongUrl();
        verify(urlMapping).setId(eq(1L));
        verify(urlMapping).setLongUrl(eq("https://example.org/example"));
        verify(urlMapping).setShortCode(eq("https://example.org/example"));
        verify(uRLMappingRepository).findByShortCode(eq("https://example.org/example"));
        assertEquals("https://example.org/example", actualLongUrl);
    }

    /**
     * Method under test: {@link URLShortenerService#getLongUrl(String)}
     */
    @Test
    void testGetLongUrl3() {
        // Arrange
        Optional<URLMapping> emptyResult = Optional.empty();
        when(uRLMappingRepository.findByShortCode(Mockito.<String>any())).thenReturn(emptyResult);

        // Act
        String actualLongUrl = uRLShortenerService.getLongUrl("https://example.org/example");

        // Assert
        verify(uRLMappingRepository).findByShortCode(eq("https://example.org/example"));
        assertNull(actualLongUrl);
    }
}
