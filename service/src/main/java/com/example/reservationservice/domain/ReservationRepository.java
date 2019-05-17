package com.example.reservationservice.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReservationRepository extends ReactiveMongoRepository<Reservation, String> {
}
