package com.example.reservationservice.preserntaion;

import com.example.reservationservice.domain.Reservation;
import com.example.reservationservice.domain.ReservationRepository;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
class ReservationRestController {
    ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    Publisher<Reservation> reservationPublisher() {
        return reservationRepository
                .deleteAll()
                .thenMany(Flux.just("Josh", "Martin", "Anna", "Andreas", "Jana", "Alok", "Anki", "Charlie")
                        .map(Reservation::new)
                        .flatMap(reservationRepository::save))
                .thenMany(reservationRepository.findAll());
//        return reservationRepository.findAll();
    }

    @GetMapping("/reservationNames")
    Publisher<String> reservationNamePublisher() {
        return reservationRepository.findAll().map(Reservation::reservationName);
    }

    ReservationRestController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
