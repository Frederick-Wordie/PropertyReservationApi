package com.hostfully.reservation.serviceImpl;

import javax.transaction.Transactional;

import com.hostfully.reservation.dto.BlockingRequestDto;
import com.hostfully.reservation.dto.ResponseDTO;
import com.hostfully.reservation.entity.Block;
import com.hostfully.reservation.repository.BlockRepository;
import com.hostfully.reservation.repository.BookingRepository;
import com.hostfully.reservation.repository.PropertyRepository;
import com.hostfully.reservation.service.BlockingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.hostfully.reservation.Utils.AppUtil.*;

/**
 * this class contains all the business logic for the various crude operations for the Block entity
 * Methods in this method are used to block a property from reservations, update a block, view blocks and
 * deletions of the blocks when th need be.
 * @author Frederick Wordie
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BlockingServiceImpl implements BlockingService{
	private final BlockRepository repository;
	private final PropertyRepository propertyRepository;
	private final BookingRepository bookingRepository;

	/**
	 * This method is used to add a new block to a property which in turns prevents the property
	 * from being given out to a client.
	 * @param block payload for creating the Block object
	 *
	 * @return {@link ResponseEntity}<{@link ResponseDTO}>
	 */
	@Override
	public ResponseEntity<ResponseDTO> blockReservation(BlockingRequestDto block) {
		ResponseDTO responseDTO;

		try{
		/**
		 * Checking if the property exists
		 */


		if(!isNotNullOrEmpty(block.getStartDate())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Specify blocking start date");
		}
		if(!isNotNullOrEmpty(block.getEndDate())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Specify blocking end date");
		}
		if(!isNotNullOrEmpty(block.getPropertyId())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Select a property");
		}
		if(!propertyRepository.existsById(block.getPropertyId())){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Property does not exists");
		}
		if(!isNotNullOrEmpty(block.getReason())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Specify the reason why the property is being block");
		}

		Block b = setBlockDetails(block, new Block());
		b = repository.save(b);

		responseDTO = getResponseDTO("success", HttpStatus.CREATED, b);
	} catch (ResponseStatusException r){
		r.printStackTrace();
		responseDTO = getResponseDTO(r.getReason(), r.getStatus());
	} catch (Exception e){
		e.printStackTrace();
		responseDTO = getResponseDTO("Error saving block", HttpStatus.INTERNAL_SERVER_ERROR);
	}

		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
	}

	/**
	 * This method is used to set the characterics of a requestDto
	 * @param requestDto
	 * @param block
	 * @return {@link Block}
	 * @return {@link ResponseEntity}<{@link ResponseDTO}>
	 */
	private  Block setBlockDetails(BlockingRequestDto requestDto, Block block) {
		OffsetDateTime start = null;
		OffsetDateTime end = null;

		if (isNotNullOrEmpty(requestDto.getStartDate())) {
			start = getOffSetDateTime(requestDto.getStartDate());
			block.setStartDate(start);
		}

		if (isNotNullOrEmpty(requestDto.getEndDate())) {
			end = getOffSetDateTime(requestDto.getEndDate());
			block.setEndDate(end);
		}


		/**
		 * Ensuring start date is before end date
		 */
		if(isNotNullOrEmpty(start) && isNotNullOrEmpty(end) && start.isAfter(end)){
			throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Start date cannot be after end date");
		}

		if (isNotNullOrEmpty(requestDto.getPropertyId())) {
			block.setPropertyId(requestDto.getPropertyId());
		}

		if (isNotNullOrEmpty(requestDto.getReason())) {
			block.setReason(requestDto.getReason());
		}
		
		if(validatePropertyBlockingOverlapsABooking(start,end,requestDto.getPropertyId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property already booked on the specified period");
		}

		return block;
	}


	/**
	 * This method is used to update a block
	 * @param id block id
	 * @param requestDTO block update payload
	 * @author Frederick Wordie
	 * @return {@link ResponseEntity}<{@link ResponseDTO}>
	 */
	@Override
	public ResponseEntity<ResponseDTO> updateBlock(String id, BlockingRequestDto requestDTO) {
		ResponseDTO responseDTO;

		try{

		Optional<Block> blockOptional = repository.findById(id);
		if(!blockOptional.isPresent()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Block with id  %s Not found",id));
		}
		/**
		 * Binding the request payload to the already existing block to be updated
		 */
		Block block = setBlockDetails(requestDTO, blockOptional.get());


		block = repository.save(block);

		responseDTO = getResponseDTO("success", HttpStatus.CREATED, block);
	} catch (ResponseStatusException r){
		r.printStackTrace();
		responseDTO = getResponseDTO(r.getReason(), r.getStatus());
	} catch (Exception e){
		e.printStackTrace();
		responseDTO = getResponseDTO("Error updating block", HttpStatus.INTERNAL_SERVER_ERROR);
	}

		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
}

	/**
	 * This method is used to delete a block
	 * @param id block id
	 * @return {@link ResponseEntity}<{@link ResponseDTO}>
	 */
	@Override
	public ResponseEntity<ResponseDTO> deleteBlock(String id) {
		ResponseDTO responseDTO;

		try {
			if(!repository.existsById(id)){
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Block does not exists");
			} else{
				repository.deleteById(id);
			}
			responseDTO = getResponseDTO("success", HttpStatus.OK);
	} catch (ResponseStatusException r){
		r.printStackTrace();
		responseDTO = getResponseDTO(r.getReason(), r.getStatus());
	} catch (Exception e){
		e.printStackTrace();
		responseDTO = getResponseDTO("Error updating block", HttpStatus.INTERNAL_SERVER_ERROR);
	}

		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
}


	/**
	 * This method is used to fetch all the blocks that are saved.
	 * @return {@link ResponseEntity}<{@link ResponseDTO}>
	 */
	@Override
	public ResponseEntity<ResponseDTO> getBlocks() {
		List<Block> blocks = repository.findAll();
		ResponseDTO responseDTO = getResponseDTO("success", HttpStatus.OK, blocks);
		return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
	}
	
	/**
	 * This method checks to see if the blocking start and end dates do not overlap
	 * by either a start or end date of another booking
	 * @param start stat date of the blocking
	 * @param end end date of the blocking
	 * @return {@link Boolean}
	 */
	protected Boolean validatePropertyBlockingOverlapsABooking(OffsetDateTime start, OffsetDateTime end, String propertyId) {
		Boolean startBooking = bookingRepository.doesOverlap(start, propertyId);
		Boolean endBooking = bookingRepository.doesOverlap(end, propertyId);

		return startBooking || endBooking;
	}

}
