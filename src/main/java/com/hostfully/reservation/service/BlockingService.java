package com.hostfully.reservation.service;

import org.springframework.http.ResponseEntity;

import com.hostfully.reservation.dto.BlockingRequestDto;
import com.hostfully.reservation.dto.ResponseDTO;

public interface BlockingService {

	public ResponseEntity<ResponseDTO> blockReservation(BlockingRequestDto block);
	
	public ResponseEntity<ResponseDTO> updateBlock(String id, BlockingRequestDto block);
	
	public ResponseEntity<ResponseDTO> deleteBlock(String id);

	public ResponseEntity<ResponseDTO> getBlocks();
}
