package com.hostfully.reservation.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingRequestUpdateDto {

	private String id;
	
	@NotBlank(message = "Property Id is mandatory")
    private String propertyId;

	@NotBlank(message = "StartDate is mandatory")
    private String startDate;

	@NotBlank(message = "EndDate is mandatory")
    private String endDate;

    @Min(1)
    @NotNull(message = "Guest is mandatory")
    private Integer guests;
}
