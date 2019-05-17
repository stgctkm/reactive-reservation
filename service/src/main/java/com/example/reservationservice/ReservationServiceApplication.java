package com.example.reservationservice;

import com.example.reservationservice.domain.Reservation;
import com.example.reservationservice.domain.ReservationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    ReactiveUserDetailsService authenticate() {
        return new MapReactiveUserDetailsService(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
    }

    @Bean
    SecurityWebFilterChain config(ServerHttpSecurity serverHttpSecurity) {
        //@formatter:off
        return
            serverHttpSecurity
                .csrf().disable()
                .httpBasic()
            .and()
            .authorizeExchange()
                .pathMatchers("/reservations").authenticated()
                .anyExchange().permitAll()
            .and()
            .build();
        //@formatter:on
    }

    @Bean
    RouterFunction<?> routes(ReservationRepository reservationRepository) {
        return route(GET("/router/reservation"), request -> ServerResponse.ok().body(reservationRepository.findAll(), Reservation.class));
    }

    @Bean
    RouterFunction<?> nameRoutes(ReservationRepository reservationRepository) {
        return RouterFunctions.route(RequestPredicates.GET("/router/reservation-name"),
                new HandlerFunction<ServerResponse>() {
                    @Override
                    public Mono<ServerResponse> handle(ServerRequest request) {
                        return ServerResponse.ok().body(reservationRepository.findAll().map(Reservation::reservationName), String.class);
                    }
                });
    }


}

@Component
class Initializer implements ApplicationRunner {
    ReservationRepository reservationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        reservationRepository
                .deleteAll()
                .thenMany(Flux.just("Josh", "Martin", "Anna", "Andreas", "Jana", "Alok", "Anki", "Charlie")
//                        .map(name -> new Reservation(null, name))
                        .map(Reservation::new)
                        .flatMap(reservationRepository::save))
                .thenMany(reservationRepository.findAll())
                .subscribe(System.out::println)
        ;


    }

    Initializer(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}


