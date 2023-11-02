package com.hostfully.reservation.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hostfully.reservation.dto.BookingRequestDto;
import com.hostfully.reservation.dto.BookingState;
import com.hostfully.reservation.dto.ResponseDTO;
import com.hostfully.reservation.entity.Booking;
import com.hostfully.reservation.entity.Property;
import com.hostfully.reservation.repository.BlockRepository;
import com.hostfully.reservation.repository.BookingRepository;
import com.hostfully.reservation.repository.PropertyRepository;
import com.hostfully.reservation.service.BookingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.hostfully.reservation.Utils.AppUtil.*;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final BookingRepository bookingRepository;

	private final PropertyRepository propertyRepository;

	private final BlockRepository blockRepository;

	@Override
	public ResponseEntity<ResponseDTO> addBooking(BookingRequestDto bookingRequestDto) {

		ResponseDTO responseDTO;
		try{

			// check edge cases
			if(!isNotNullOrZero(bookingRequestDto.getGuests()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of Guest(s) cannot be less than 1");
			if(!isNotNullOrEmpty(bookingRequestDto.getStartDate()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be null");
			if(!isNotNullOrEmpty(bookingRequestDto.getEndDate()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date cannot be null");
			if(!isNotNullOrEmpty(bookingRequestDto.getPropertyId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property id cannot be null");



			var propertyId = bookingRequestDto.getPropertyId();

			if(!propertyIsExistent(propertyId))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property does not exists");

			Booking booking = new Booking();

			booking = setBookingDetails(bookingRequestDto, booking, true);
			booking = bookingRepository.save(booking);

			responseDTO = getResponseDTO("success", HttpStatus.CREATED, booking);
		} catch (ResponseStatusException r){
			r.printStackTrace();
			responseDTO = getResponseDTO(r.getReason(), r.getStatus());
		} catch (Exception e){
			e.printStackTrace();
			responseDTO = getResponseDTO("Error saving booking", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());

	}

	/**
	 * This method is used to set the booking object
	 * @param bookingRequestDto
	 * @return
	 */
	private   Booking setBookingDetails(BookingRequestDto bookingRequestDto, Booking booking, Boolean isNew) {
		OffsetDateTime start = null;
		OffsetDateTime end = null;
		/**
		 * Setting status for when it is a new booking
		 */
		if(isNew)  {
			booking.setState(BookingState.BOOKED.toString());
		} else{
			if(isNotNullOrEmpty(bookingRequestDto.getState()) && !bookingRequestDto.getState().equals(BookingState.REBOOKED)){
				/**
				 * When there already exists a cancelled booking and needs rebooking
				 */
				if((bookingRequestDto.getState() == BookingState.BOOKED ) && (booking.getState().toString() == BookingState.CANCELED.toString())){
					booking.setState(BookingState.REBOOKED.toString());
					/**
					 * When The need arises to change the state of booking and
					 */
				} else {
					booking.setState(bookingRequestDto.getState().toString());
				}
			}
		}

		/**
		 * Checking if start date exists
		 */

		if(isNotNullOrEmpty(bookingRequestDto.getStartDate())){
			start = getOffSetDateTime(bookingRequestDto.getStartDate());
			booking.setStartDate(start);
		}

		/**
		 * Checking if end date exists
		 */

		if(isNotNullOrEmpty(bookingRequestDto.getEndDate())){
			end = getOffSetDateTime(bookingRequestDto.getEndDate());
			booking.setEndDate(end);
		}

		/**
		 * Ensuring start date is before end date
		 */
		if(isNotNullOrEmpty(start) && isNotNullOrEmpty(end) && start.isAfter(end)){
			throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Start date cannot be after end date");
		}

		if(isNotNullOrZero(bookingRequestDto.getGuests()))
			booking.setGuests(bookingRequestDto.getGuests());
		
		/**
		 * Checking the
		 */
		if(isNotNullOrEmpty(bookingRequestDto.getPropertyId()))
			booking.setPropertyId(bookingRequestDto.getPropertyId());

		if(isNew)  {
			/**
			 * Checking if booking overlaps with another one
			 */
				if (validatePropertyBookingOverlapsAnother(start, end, bookingRequestDto.getPropertyId()))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking overlaps (an)other booking(s)");
		}else {
			/**
			 * Checking if booking overlaps with another one in an update
			 */
				if (validatePropertyBookingOverlapsAnotherWhenUpdating(start, end, bookingRequestDto.getPropertyId(),booking.getId()))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking overlaps (an)other booking(s)");
		}
		

			/**
			 * Checking if booking overlaps to a blocking
			 */
			if (validatePropertyBookingOverlapsBlocking(start, end, bookingRequestDto.getPropertyId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking overlaps blocking(s)");


		return booking;
	}

	/**
	 * This method checks to see if the booking's start and end dates do not overlap
	 * by either a start or end date of another booking
	 * @param start stat date of the booking
	 * @param end end date of the booking
	 * @return {@link Boolean}
	 */
	protected Boolean validatePropertyBookingOverlapsAnother(OffsetDateTime start, OffsetDateTime end, String propertyId) {
		Boolean startBooking = bookingRepository.doesOverlap(start, propertyId);
		Boolean endBooking = bookingRepository.doesOverlap(end, propertyId);

		return startBooking || endBooking;
	}
	
	/**
	 * This method checks to see if the booking's start and end dates do not overlap when updating
	 * by either a start or end date of another booking
	 * @param start stat date of the booking
	 * @param end end date of the booking
	 * @return {@link Boolean}
	 */
	private Boolean validatePropertyBookingOverlapsAnotherWhenUpdating(OffsetDateTime start, OffsetDateTime end, String propertyId,String bookingId) {
		Boolean startBooking = bookingRepository.doesOverlapWhenUpdating(start, propertyId,bookingId);
		Boolean endBooking = bookingRepository.doesOverlapWhenUpdating(end, propertyId,bookingId);

		return startBooking || endBooking;
	}


	/**
	 * This method checks to see if the booking's start and end dates do not overlap
	 * by either a start or end date of a blocking
	 * @param start stat date of the booking
	 * @param end end date of the booking
	 * @return {@link Boolean}
	 */
	private Boolean validatePropertyBookingOverlapsBlocking(OffsetDateTime start, OffsetDateTime end, String propertyId) {
		Boolean startBooking = blockRepository.doesOverlap(start, propertyId);
		Boolean endBooking = blockRepository.doesOverlap(end, propertyId);

		return startBooking || endBooking;
	}


	/**
	 * Deleting a booking by id
	 * @param id
	 */
	@Override
	public ResponseEntity<ResponseDTO> deleteBooking(String id) {
		ResponseDTO responseDTO;
		try{
			if(!bookingRepository.existsById(id))
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Booking with id %s does not exists",id));

			bookingRepository.deleteById(id);
			responseDTO = getResponseDTO("success", HttpStatus.OK);

		}catch (ResponseStatusException r){
			r.printStackTrace();
			responseDTO = getResponseDTO(r.getReason(), r.getStatus());

		}catch (Exception e){
			e.printStackTrace();
			responseDTO = getResponseDTO("Error deleting booking", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
	}

	@Override
	public ResponseEntity<ResponseDTO> updateBooking(String id, BookingRequestDto bookingRequestUpdateDto) {
		ResponseDTO responseDTO ;
		try{
			Optional<Booking> optional = bookingRepository.findById(id);
			if(!optional.isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");

			Booking booking = optional.get();

			booking = setBookingDetails(bookingRequestUpdateDto, booking, false);

			booking = bookingRepository.save(booking);

			responseDTO = getResponseDTO("success", HttpStatus.OK, booking);

	}catch (ResponseStatusException r){
		r.printStackTrace();
		responseDTO = getResponseDTO(r.getReason(), r.getStatus());

	}catch (Exception e){
		e.printStackTrace();
		responseDTO = getResponseDTO("Error updating booking", HttpStatus.INTERNAL_SERVER_ERROR);
	}
		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
}

	@Override
	public boolean propertyIsExistent(String id) {
		/**
		 * Checking if the property exists
		 */
		if(!propertyRepository.existsById(id)){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Property does not exists");
		}
		return true;
	}

	@Override
	public ResponseEntity<ResponseDTO> getBookings() {
		List<Booking> bookings = bookingRepository.findAll();

		return new ResponseEntity<>(getResponseDTO("success", HttpStatus.OK, bookings), HttpStatus.OK);
	}

	//todo to be removed
	@Override
	public List<Property> getProperties() {
		return propertyRepository.findAll();
	}


}
