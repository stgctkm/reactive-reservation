package com.example.reservationservice.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Document
public class Reservation {

    @Id
    private String id;
    private String reservationName;

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

//    public Reservation(String id, String reservationName) {
//        this.id = id;
//        this.reservationName = reservationName;
//    }
//
//    public Reservation() {
//    }


    public String getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public String reservationName() {
        return reservationName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }

}
