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

import com.hostfully.reservation.dto.BlockingRequestDto;
import com.hostfully.reservation.dto.ResponseDTO;
import com.hostfully.reservation.service.BlockingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservationapi/v1")
@RequiredArgsConstructor
public class BlockingController {

	private final BlockingService blockingService;

	@Operation(summary = "Add Block", description = "This prevents a property from being booked")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Successful created"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@PostMapping(value = "/block")
	public ResponseEntity<ResponseDTO> blockReservation(@RequestBody  BlockingRequestDto block) {
		var res = blockingService.blockReservation(block);
		return res;
	}

	@Operation(summary = "Update Block", description = "Updates a specific block request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@PatchMapping(value = "/block/{id}")
	public ResponseEntity<ResponseDTO> updateBlock(@PathVariable String id,@RequestBody BlockingRequestDto blocking){
		var res = blockingService.updateBlock(id, blocking);
		return res;
	}


	@Operation(summary = "Delete Block", description = "Deletes a specific block request")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@DeleteMapping(value = "/block/{id}")
	public ResponseEntity<ResponseDTO> deleteBlock(@PathVariable String id) {
		var res = blockingService.deleteBlock(id);
		return res;
	}



	@Operation(summary = "Get Blocks", description = "Gets all blocks")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "404", description = "Any parameter is wrong"),
			@ApiResponse(responseCode = "500", description = "Any other failure")
	})
	@GetMapping(value = "/blocks")
	public ResponseEntity<ResponseDTO> getBlcoks(){
		var res = blockingService.getBlocks();
		return res;
	}
	
}
