package com.flightmng.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Passenger {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private BigInteger pnrNumber;
	
	@Column
	private String passengerName;
	
	@Column
	private int passengerAge;
	
	@Column(unique=true)
	@Size(min = 12, max = 12)
	private String passengerUIN;
	
	@Column
	private Double luggage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookingId")
	@JsonIgnoreProperties
	private Booking booking;

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public BigInteger getPnrNumber() {
		return pnrNumber;
	}

	public void setPnrNumber(BigInteger pnrNumber) {
		this.pnrNumber = pnrNumber;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int getPassengerAge() {
		return passengerAge;
	}

	public void setPassengerAge(int passengerAge) {
		this.passengerAge = passengerAge;
	}

	public String getPassengerUIN() {
		return passengerUIN;
	}

	public void setPassengerUIN(String passengerUIN) {
		this.passengerUIN = passengerUIN;
	}

	public Double getLuggage() {
		return luggage;
	}

	public void setLuggage(Double luggage) {
		this.luggage = luggage;
	}

}
