package com.JEEProjects.QuickFlow.service;

import java.time.LocalDate;

import com.JEEProjects.QuickFlow.models.CITOYEN;

public interface CINvalidation {
	boolean isCinValidForCity(String id, String city);
	boolean isDateAfterToday(LocalDate inputDate);
	public void processCitizen(String id) ;
	void updateUsernameById(String id, String username);
    void updatePasswordById(String id, String password);
	
	

}