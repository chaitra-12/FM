package com.flightmng.service;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;

import com.flightmng.exceptions.BookingException;
import com.flightmng.exceptions.ValidatePassengerException;
import com.flightmng.model.Booking;

public interface BookingService {

	public Booking createBooking(Booking newBooking) throws BookingException;

	public Booking updateBooking(Booking newBooking);

	public String deleteBooking(BigInteger bookingId);

	public Iterable<Booking> displayAllBooking();

	public ResponseEntity<?> findBookingById(BigInteger bookingId);
}
