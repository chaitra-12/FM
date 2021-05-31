package com.flightmng.service;

import java.math.BigInteger;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightmng.dao.UserDao;
import com.flightmng.exceptions.RecordAlreadyPresentException;
import com.flightmng.exceptions.RecordNotFoundException;
import com.flightmng.exceptions.ValidateUserException;
import com.flightmng.model.Users;
import com.flightmng.util.FlightConstants;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public Users createUser(Users newUser) throws ValidateUserException {
		validateUser(newUser);
		return userDao.save(newUser);
	}
	private boolean validateUser(Users users) throws ValidateUserException {
		if (!users.getUserName().matches("[A-Za-z]+")) {
			throw new ValidateUserException(FlightConstants.USERNAME_CANNOT_BE_EMPTY);
		}
		if (!users.getUserPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
			throw new ValidateUserException(FlightConstants.PASSWORD_CANNOT_BE_EMPTY);
		}
		String userPhoneNumber = Long.toString(users.getUserPhone());
		
		if (!userPhoneNumber.matches("^[6-9][0-9]{9}")) {
			throw new ValidateUserException(FlightConstants.MOBILENUMBER_CANNOT_BE_EMPTY);
		}
		if (!users.getUserEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
			throw new ValidateUserException(FlightConstants.EMAIL_CANNOT_BE_EMPTY);
		}
		return true;
	}

	@Override
	public Users updateUser(Users updateUser) {
		Optional<Users> findUserById = userDao.findById(updateUser.getUserId());
		if (findUserById.isPresent()) {
			userDao.save(updateUser);
		} else
			throw new RecordNotFoundException(
					"User with Id: " + updateUser.getUserId() + " not exists!!");
		return updateUser;
	}

	@Override
	public String deleteUser(BigInteger UserId) {
		Optional<Users> findBookingById = userDao.findById(UserId);
		if (findBookingById.isPresent()) {
			userDao.deleteById(UserId);
			return "User Deleted!!";
		} else
			throw new RecordNotFoundException("User not found for the entered UserID");
	}

	@Override
	public Iterable<Users> displayAllUser() {
		return userDao.findAll();
	}

	@Override
	public ResponseEntity<?> findUserById(BigInteger userId) {
		Optional<Users> findById = userDao.findById(userId);
		try {
			if (findById.isPresent()) {
				Users findUser = findById.get();
				return new ResponseEntity<Users>(findUser, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + userId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}