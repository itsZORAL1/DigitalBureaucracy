package com.JEEProjects.QuickFlow.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;
import com.JEEProjects.QuickFlow.service.ServicesService;

@Service
public class ServicesServiceImplementation implements ServicesService {
	private ServicesRepository SerRep;
	
    @Autowired
    public ServicesServiceImplementation(ServicesRepository SerRep) {
        this.SerRep = SerRep;
    }

  
 
	@Override
	public SERVICE addService(SERVICE service) {
		
		return SerRep.save(service);
	}

}
