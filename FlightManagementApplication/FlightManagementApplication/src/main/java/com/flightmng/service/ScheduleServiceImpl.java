package com.flightmng.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightmng.dao.ScheduleDao;
import com.flightmng.exceptions.DateTimeException;
import com.flightmng.exceptions.RecordNotFoundException;
import com.flightmng.model.Schedule;
import com.flightmng.util.FlightConstants;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	ScheduleDao dao;
	
	@Override
	public Schedule addSchedule(Schedule schedule) throws DateTimeException {
		validateDateTime(schedule);
		return dao.save(schedule);
	}
	
	private boolean validateDateTime(Schedule schedule) throws DateTimeException {
		if (!schedule.getDeptDateTime().matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
			throw new DateTimeException(FlightConstants.DATETIME_FORMAT);
		}
		if (!schedule.getArrDateTime().matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
			throw new DateTimeException(FlightConstants.DATETIME_FORMAT);
		}
		return true;
	}
	
	@Override
	public Iterable<Schedule> viewAllSchedules() {
		return dao.findAll();
	}
	
	@Override
	public Schedule viewSchedule(BigInteger scheduleId){
		Optional<Schedule> findById = dao.findById(scheduleId);
		if (findById.isPresent()) {
			return findById.get();
		}
		else
			throw new RecordNotFoundException("No schedule exists");
	   }
	
	@Override
	public String removeSchedule(BigInteger scheduleid){
		Optional<Schedule> findById = dao.findById(scheduleid);
		if (findById.isPresent()) {
			dao.deleteById(scheduleid);
			return "Flight removed!!";
		} else
			throw new RecordNotFoundException("Schedule record not found");

	}
	
	@Override
	public Schedule modifySchedule(Schedule schedule) {
		Schedule updateSchedule = dao.findById(schedule.getScheduleId()).get();
		updateSchedule.setArrDateTime(schedule.getArrDateTime());
		updateSchedule.setDeptDateTime(schedule.getDeptDateTime());
		dao.save(updateSchedule);
		return schedule;
	}
}

