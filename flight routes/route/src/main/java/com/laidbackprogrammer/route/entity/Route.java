package com.laidbackprogrammer.route.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="source_airport_id")
    private Airport sourceAirport;

    @ManyToOne
    @JoinColumn( name = "destination_airport_id")
    private Airport destinationAirport;

    private double distance;
}
