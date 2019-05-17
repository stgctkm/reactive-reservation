package com.example.domain;


import lombok.Data;

@Data
public class Reservation {

    private String id;

    private String reservationName;

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public Reservation(String id, String reservationName) {
        this.id = id;
        this.reservationName = reservationName;
    }

    public Reservation() {
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
