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
- Necessary validation is incorporated within the API that prevents illogical operations from occurring such as making an overlapping reservation to a property.



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

#### Request

```http
POST /api/endpoint-1 
 ```

### update-existing-booking

Updates an existing booking

#### Request

```http
PATCH /api/endpoint-2
```

### get-all-existing-bookings

Get all existing bookings

#### Request

```http
GET /api/endpoint-3
 ```
 
### delete-booking

Delete booking by its ID

#### Request

```http
DELETE /api/endpoint-4
 ```
 
 
## Blocking Endpoints
 
### create-block

Create a blocking object

#### Request

```http
POST /api/endpoint-5 
 ```

### update-existing-blocking

Updates an existing block

#### Request

```http
PATCH /api/endpoint-6
```

### get-all-existing-blockings

Get all existing blockings

#### Request

```http
GET /api/endpoint-7
 ```
 
### delete-block

Delete blocking by its ID

#### Request

```http
DELETE /api/endpoint-8
 ```



