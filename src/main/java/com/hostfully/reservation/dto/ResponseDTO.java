package com.hostfully.reservation.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Data
public class ResponseDTO {
    private Object data;
    private OffsetDateTime date;

    private String message;

    private HttpStatus status;
}
