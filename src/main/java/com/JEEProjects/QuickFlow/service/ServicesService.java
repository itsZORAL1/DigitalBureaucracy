package com.JEEProjects.QuickFlow.service;

import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.COMMUNE;
import com.JEEProjects.QuickFlow.models.SERVICE;

@Service
public interface ServicesService {
	public SERVICE addService(SERVICE service);
}
