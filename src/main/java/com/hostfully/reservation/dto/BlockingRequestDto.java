package com.hostfully.reservation.dto;


import lombok.Data;

@Data
public class BlockingRequestDto {
    private String propertyId;
    
    private String startDate;

    private String endDate;
    
    private String reason;

}
