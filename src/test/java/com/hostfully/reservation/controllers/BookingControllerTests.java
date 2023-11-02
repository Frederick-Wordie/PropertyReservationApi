package com.hostfully.reservation.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hostfully.reservation.ReservationRestApiApp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "web-client.log-level=debug",
		"logging.level.reactor.netty.http.client=DEBUG" }, classes = ReservationRestApiApp.class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@PropertySource("classpath:test.properties")
@EnableConfigurationProperties
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingControllerTests {

	
}
