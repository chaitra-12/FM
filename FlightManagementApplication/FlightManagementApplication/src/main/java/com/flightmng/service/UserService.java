package com.flightmng.service;

import java.math.BigInteger;

import org.springframework.http.ResponseEntity;

import com.flightmng.exceptions.ValidateUserException;
import com.flightmng.model.Users;

public interface UserService {

	public Users createUser(Users newUser) throws ValidateUserException;

	public Users updateUser(Users newUser);

	public String deleteUser(BigInteger UserId);

	public Iterable<Users> displayAllUser();

	public ResponseEntity<?> findUserById(BigInteger userId);
}