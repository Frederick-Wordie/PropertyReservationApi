package com.hostfully.reservation.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BlockingRequestUpdateDto {

    private String id;
	
	@NotBlank(message = "Property Id is mandatory")
	private String propertyId;
    
    @NotBlank(message = "Start date is mandatory")
    private String startDate;

    @NotBlank(message = "End date is mandatory")
    private String endDate;
    
    @NotBlank(message = "Reason is mandatory")
    private String reason;
    
    @NotBlank(message = "State is mandatory")
    private String state;
}
