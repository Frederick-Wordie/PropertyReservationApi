package com.hostfully.reservation.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hostfully.reservation.dto.BookingRequestDto;
import com.hostfully.reservation.dto.ResponseDTO;
import com.hostfully.reservation.entity.Property;

public interface BookingService {

	public ResponseEntity<ResponseDTO> addBooking(BookingRequestDto bookingRequestDto);

	public ResponseEntity<ResponseDTO> deleteBooking(String id);

	public ResponseEntity<ResponseDTO> updateBooking(String id,BookingRequestDto bookingRequestUpdateDto);

	ResponseEntity<ResponseDTO> getBookings();

	public boolean propertyIsExistent(String id);

    List<Property> getProperties();
}
