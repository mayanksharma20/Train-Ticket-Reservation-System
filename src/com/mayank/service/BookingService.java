package com.mayank.service;

import java.util.List;

import com.mayank.beans.HistoryBean;
import com.mayank.beans.TrainException;

public interface BookingService {

	public List<HistoryBean> getAllBookingsByCustomerId(String customerEmailId) throws TrainException;

	public HistoryBean createHistory(HistoryBean bookingDetails) throws TrainException;

}
