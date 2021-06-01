package com.flightmng.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightmng.dao.BookingDao;
import com.flightmng.exceptions.BookingException;
import com.flightmng.exceptions.RecordAlreadyPresentException;
import com.flightmng.exceptions.RecordNotFoundException;
import com.flightmng.exceptions.ValidatePassengerException;
import com.flightmng.model.Booking;
import com.flightmng.model.Flight;
import com.flightmng.model.Passenger;
import com.flightmng.util.FlightConstants;

@Service
public class BookingServiceImpl implements BookingService {

	/*
	 * Creating DAO object
	 */
	@Autowired
	FlightService flightService;
	@Autowired
	BookingDao bookingDao;

	/*
	 * making new Booking
	 */
	@Override
	public Booking createBooking(Booking newBooking) throws BookingException, ValidatePassengerException{

		Optional<Booking> findBookingById = bookingDao.findById(newBooking.getBookingId());
		Flight flightdetails = flightService.viewFlight(newBooking.getFlight().getFlightNo());

			if (!findBookingById.isPresent()) {
				validateDate(newBooking);
				if(newBooking.getNoOfPassengers() <= flightdetails.getSeatCapacity()) {
					if(newBooking.getNoOfPassengers() == newBooking.getPassengerList().size()) {
						validatePassenger(newBooking);
						return bookingDao.save(newBooking);
					}else {
						throw new BookingException(FlightConstants.BOOKING_PASSENGER_ERROR);
					}
					
				}else {
					throw new BookingException(FlightConstants.BOOKING_ERROR);
				
				}
			} else
				throw new RecordAlreadyPresentException(
						"Booking with Booking Id: " + newBooking.getBookingId() + " already exists!!");
	
	}
	private boolean validateDate(Booking booking) throws BookingException {
		if (!booking.getBookingDate().matches("\\d{2}-\\d{2}-\\d{4}")) {
			throw new BookingException(FlightConstants.DATE_FORMAT);
		}
		return true;
	}
	
	private boolean validatePassenger(Booking booking) throws ValidatePassengerException {
		List<Passenger> list = booking.getPassengerList();
		for(Passenger pg: list) {
			if (!pg.getPassengerName().matches("[A-Za-z]+")) {
				throw new ValidatePassengerException(FlightConstants.PASSENGERNAME_CANNOT_BE_EMPTY);
			}
			String passengerUIN = Long.toString(pg.getPassengerUIN());
			if (!passengerUIN.matches("^[1-9][0-9]{11}")) {
				throw new ValidatePassengerException(FlightConstants.PASSENGERUIN_CANNOT_BE_EMPTY);
			}
		}
		return true;
	}
	

	/*
	 * update booking made
	 */
	@Override
	public Booking updateBooking(Booking changedBooking) {
		Optional<Booking> findBookingById = bookingDao.findById(changedBooking.getBookingId());
		if (findBookingById.isPresent()) {
			bookingDao.save(changedBooking);
		} else
			throw new RecordNotFoundException(
					"Booking with Booking Id: " + changedBooking.getBookingId() + " not exists!!");
		return changedBooking;
	}

	/*
	 * deleting the booking
	 */

	@Override
	public String deleteBooking(BigInteger bookingId) {

		Optional<Booking> findBookingById = bookingDao.findById(bookingId);
		if (findBookingById.isPresent()) {
			bookingDao.deleteById(bookingId);
			return "Booking Deleted!!";
		} else
			throw new RecordNotFoundException("Booking not found for the entered BookingID");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flightmng.service.BookingService#displayAllBooking() show all booking
	 */
	@Override
	public Iterable<Booking> displayAllBooking() {

		return bookingDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flightmng.service.BookingService#findBookingById(java.math.BigInteger)
	 * find booking by ID
	 */
	@Override
	public ResponseEntity<?> findBookingById(BigInteger bookingId) {
		Optional<Booking> findById = bookingDao.findById(bookingId);
		try {
			if (findById.isPresent()) {
				Booking findBooking = findById.get();
				return new ResponseEntity<Booking>(findBooking, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + bookingId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
