package com.flightmng.dao;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flightmng.model.Booking;

@Repository
public interface BookingDao extends CrudRepository<Booking, BigInteger> {

}
