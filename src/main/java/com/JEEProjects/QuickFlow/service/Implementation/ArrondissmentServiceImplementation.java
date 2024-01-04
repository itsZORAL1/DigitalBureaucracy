package com.JEEProjects.QuickFlow.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.repository.ArrondissementRepository;
import com.JEEProjects.QuickFlow.service.ArrondissmentService;

@Service
public class ArrondissmentServiceImplementation  implements ArrondissmentService{
	 private ArrondissementRepository ArrRep;

	    @Autowired
	    public ArrondissmentServiceImplementation(ArrondissementRepository ArrRep) {
	        this.ArrRep= ArrRep;
	    }
	@Override
	public List<ARRONDISSEMENT> getAllArrondissement() {
		
		return this.ArrRep.findAll();
	}
@Override
public ARRONDISSEMENT getArrondissementById(String arrondissementId) {
	        return ArrRep.findById(arrondissementId).orElse(null);
	    }
}
