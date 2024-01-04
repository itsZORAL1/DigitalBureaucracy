package com.JEEProjects.QuickFlow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;


@Service
public interface ArrondissmentService {
	
	public List<ARRONDISSEMENT> getAllArrondissement();
	public ARRONDISSEMENT getArrondissementById(String arrondissementId);
}
