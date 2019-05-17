package com.example.reservationclient;

import com.example.domain.Reservation;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}

	@Bean
	WebClient webClient() {
		return WebClient.builder()
				.filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
				.build();
	}

	@Bean
	RouterFunction<?> route(WebClient webClient) {
		return RouterFunctions.route(RequestPredicates.GET("/names"), new HandlerFunction<ServerResponse>() {
			@Override
			public Mono<ServerResponse> handle(ServerRequest request) {
				Flux<String> namePublisher = webClient
						.get()
						.uri("http://localhost:8080/reservations")
						.retrieve()
						.bodyToFlux(Reservation.class)
						.map(Reservation::reservationName);
				return ServerResponse.ok().body(namePublisher, String.class);
			}
		});
	}

	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(spec ->
						spec.path("/guides")
								.uri("http://spring.io:80/guides"))
				.build()
				;
	}
}
