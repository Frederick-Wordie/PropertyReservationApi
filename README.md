# PropertyReservationApi
Simple project to manage property reservations

# API Documentation

This document provides documentation for the reservation service api. You can use this documentation to understand the available endpoints and how to use them.

## Getting Started

- This project is a Property Reservation `RESTful API` designed to **reserve properties by booking or can also be blocked from use by the property owner**
- A booking is when a guest selects a start and end date and submits a reservation on a property.
- A block is when the property owner or manager selects a range of days during which no guest can make a booking
- The user can make a booking, update a booking or delete a book.
- Property owners can also block a property, update or delete it
- The application is pre-loaded with sample property objects which can be seen using the get property endpoint. The uuid of the properties can be used in the booking and blocking request
- Necessary validation is incorporated within the API that prevents illogical operations from occurring such as making an overlapping reservation to a property.


1. **Endpoints for Property**:
   
| Method | Endpoint | Description | Valid API Calls |
| ------ | --- | ----------- | ------------------------- |
| GET | /reservationapi/v1/properties | Get all existing properties | [Get all existing properties](#get-all-existing-properties) |



1. **Endpoints for Booking**:
   
| Method | Endpoint | Description | Valid API Calls |
| ------ | --- | ----------- | ------------------------- |
| POST | /reservationapi/v1/booking | Create a booking object | [Create booking](#create-booking) |
| PATCH | /reservationapi/v1/booking/{bookingId} | Updates an existing booking | [Update existing Booking](#update-existing-booking) |
| GET | /reservationapi/v1/bookings | Get all existing bookings | [Get all existing bookings](#get-all-existing-bookings) |
| DELETE | /reservationapi/v1/booking/{bookingId} | Delete booking by its ID | [Delete-booking](#delete-booking) |


2. **Endpoints for Blocking**:
   
| Method | Endpoint | Description | Valid API Calls |
| ------ | --- | ----------- | ------------------------- |
| POST | /reservationapi/v1/block | Create a blocking object | [Create blocking](#create-blocking) |
| PATCH | /reservationapi/v1/block/{blockingId} | Updates an existing blocking | [Update existing Blocks](#update-existing-blocking) |
| GET | /reservationapi/v1/blocks | Get all existing blockings | [Get all existing blockings](#get-all-existing-blockings) |
| DELETE | /reservationapi/v1/block/{blockingId} | Delete blocking by its ID | [Delete-block](#delete-block) |



## Booking Endpoints

### create-booking

Create a booking object

#### Booking Request Body

- Request Body:
   ```json
      {    
       "guests": integer: (Must be one or more),
       "propertyId": "string: (exiting uuid of a property)",
       "startDate": "yyyy-MM-dd",
	    "endDate": "yyyy-MM-dd",
       "state": "String: (BOOKED, CANCELED)"
      }
   ```

### update-existing-booking

Updates an existing booking. Dates must be in the specified formats. State is set to canceled when a user wants to cancel a booking.
Bookings are not allowed to overlap

#### Request Body

- Request Body:
   ```json
      {    
       "guests": integer: (Must be one or more),
       "propertyId": "string: (exiting uuid of a property)",
       "startDate": "yyyy-MM-dd",
	    "endDate": "yyyy-MM-dd",
       "state": "String: (BOOKED, CANCELED)"
      }
   ```



### get-all-existing-bookings

Get all existing bookings

#### Request

```http
No parameters are needed
 ```
 
### delete-booking

Delete booking by its ID

#### Request

```http
string: (uuid of booking in path) 
 ```
 
 
## Blocking Endpoints
 
### create-block

Create a blocking object

#### Request

- Request Body:
   ```json
       {      
        "propertyId": "string: (uuid of property)",
        "reason": "string",
        "startDate": "yyyy-MM-dd"
		"endDate": "yyyy-MM-dd",
      }
   ```
 


### update-existing-blocking

Updates an existing block

#### Request

- Request Body:
   ```json
       {      
        "propertyId": "string: (uuid of property)",
        "reason": "string",
        "startDate": "yyyy-MM-dd"
		"endDate": "yyyy-MM-dd",
      }
   ```

### get-all-existing-blockings

Get all existing blockings

#### Request

```http
No parameters are needed
 ```
 
### delete-block

Delete blocking by its ID

#### Request

```http
string: (uuid of block in path) 
 ```
 
 # Tech Stack

- API Creation:
  - Java 11
  - H2 Database
  - SpringBoot
  - JPA
  - Lombok

- User Input Testing:
  - Swagger UI
  - Postman



