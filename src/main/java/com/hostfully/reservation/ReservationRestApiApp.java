package com.hostfully.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hostfully.reservation"})
public class ReservationRestApiApp {
    public static void main(String[] args) {
        SpringApplication.run(ReservationRestApiApp.class, args);
    }

}
