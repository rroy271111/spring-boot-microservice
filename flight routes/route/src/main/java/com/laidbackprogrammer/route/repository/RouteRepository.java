package com.laidbackprogrammer.route.repository;

import com.laidbackprogrammer.route.entity.Airport;
import com.laidbackprogrammer.route.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Optional<Route> findBySourceAirportAndDestinationAirport(Airport source, Airport destination);
}
