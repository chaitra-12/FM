package com.flightmng.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flightmng.model.Airport;

@Repository
public interface AirportDao extends CrudRepository<Airport, String> {

}
