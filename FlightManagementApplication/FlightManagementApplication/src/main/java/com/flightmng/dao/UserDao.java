package com.flightmng.dao;
import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.flightmng.model.Users;;

public interface UserDao extends CrudRepository<Users, BigInteger>{

}