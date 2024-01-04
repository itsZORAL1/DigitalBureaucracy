package com.JEEProjects.QuickFlow.service;

import org.springframework.stereotype.Service;
import com.JEEProjects.QuickFlow.models.COMMUNE;

@Service
public interface CommuneService {
	public COMMUNE addCommune(COMMUNE commune);
	
}
