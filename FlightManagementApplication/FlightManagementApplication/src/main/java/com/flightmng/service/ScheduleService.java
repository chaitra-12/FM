package com.flightmng.service;

import java.math.BigInteger;

import com.flightmng.model.Schedule;


public interface ScheduleService {
	public Schedule addSchedule(Schedule schedule);

	public Schedule modifySchedule(Schedule schedule);

	public String removeSchedule(BigInteger id);

	public Iterable<Schedule> viewAllSchedules();

	public Schedule viewSchedule(BigInteger id);

}