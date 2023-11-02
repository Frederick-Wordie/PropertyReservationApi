package com.hostfully.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostfully.reservation.dto.BookingRequestDto;
import com.hostfully.reservation.dto.ResponseDTO;
import com.hostfully.reservation.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservationapi/v1")
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@Operation(summary = "Add Booking", description = "This is used to book a property by a client")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Successful created"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@PostMapping(value = "/booking")
	public ResponseEntity<ResponseDTO> createBooking(@RequestBody  BookingRequestDto booking) {
		var res = bookingService.addBooking(booking);
		return res;
	}

	@Operation(summary = "Update booking", description = "This updates a booking request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful created"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@PatchMapping(value = "/booking/{id}")
	public ResponseEntity<ResponseDTO> updateBooking(@PathVariable String id,@RequestBody BookingRequestDto booking){
		var res = bookingService.updateBooking(id,booking);
		return res;
	}


	@Operation(summary = "Delete booking", description = "This deletes a specific booking request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful created"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@DeleteMapping(value = "/booking/{id}")
	public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable String id) {
		var res = bookingService.deleteBooking(id);
		return res;
	}


	@Operation(summary = "Get bookings", description = "This updates a booking request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful created"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@GetMapping(value = "/bookings")
	public ResponseEntity<?> getBookings(){
		var response = bookingService.getBookings();
		return response;
	}

	//todo to be removed
	@Operation(summary = "Update Properties", description = "To get property id for testing")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful created"),
	})
	@GetMapping(value = "/properties")
	public ResponseEntity<?> getProperties(){
		return ResponseEntity.ok(bookingService.getProperties());
	}

}
