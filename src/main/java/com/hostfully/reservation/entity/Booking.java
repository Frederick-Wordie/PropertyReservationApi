package com.hostfully.reservation.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking extends AuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "property_id", nullable = false)
    private String propertyId;

    @Column(name = "start_date", nullable = false)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime endDate;

    @Column(nullable = false)
    private Integer guests;

    @Column
    private String state;

}
