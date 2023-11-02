package com.hostfully.reservation.constants;

public final class MessageConstants {

	public MessageConstants() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final String NOT_FOUND="Cannot find Resource";
	public static final String BAD_REQUEST="Bad Request";
	public static final String UPDATE_BOOKING_LOG = "Message Received :: Update Booking :: {}";
	public static final String UPDATED_BOOKING= "Booking updated :::: {}";
	public static final String ERROR_LOG= "Error :::: {}";
	public static final String ADD_BOOKING_LOG="Message Received :: CREATE BOOKING :: {}"; 
	public static final String BOOKING_SAVE_LOG = "Booking saved with ID :::: {}";
	public static final String DELETE_BOOKING_LOG= "Message Received :::: DELETE BOOKING :::: {}";
	public static final String DELETED_BOOKING= "Booking deleted ::::";
	public static final String PROPERTY_NOT_FOUND = "Property with ID: %s not found";
	public static final String BOOKING_NOT_FOUND = "Booking with ID: %s not found";
	public static final String PROPERTY_NOT_AVAILABLE = "Invalid Property: A booking can not be made as the user specified property is not available";
	
}
