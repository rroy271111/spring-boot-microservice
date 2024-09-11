package com.laidbackprogrammer.url_shortener.repository;

import com.laidbackprogrammer.url_shortener.model.URLMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLMappingRepository extends JpaRepository<URLMapping, Long> {

    Optional<URLMapping> findByShortCode(String shortCode);

    Optional<URLMapping> findByLongUrl(String longUrl);
}
