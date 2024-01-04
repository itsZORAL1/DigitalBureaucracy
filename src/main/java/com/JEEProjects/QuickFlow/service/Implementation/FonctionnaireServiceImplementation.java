package com.JEEProjects.QuickFlow.service.Implementation;


import com.JEEProjects.QuickFlow.models.FONCTIONNAIRE;
import com.JEEProjects.QuickFlow.repository.FonctionnaireRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;
import com.JEEProjects.QuickFlow.service.Fonctionnaireserv;
import com.JEEProjects.QuickFlow.models.SERVICE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FonctionnaireServiceImplementation  implements Fonctionnaireserv {

    private final FonctionnaireRepository fonctionnaireRepository;
    private final ServicesRepository servicerepository;

    @Autowired
    public FonctionnaireServiceImplementation(FonctionnaireRepository fonctionnaireRepository,ServicesRepository servicerepository) {
        this.fonctionnaireRepository = fonctionnaireRepository;
        this.servicerepository=servicerepository;
    }

    public FONCTIONNAIRE saveFonctionnaire(FONCTIONNAIRE fonctionnaire) {
        return fonctionnaireRepository.save(fonctionnaire);
    }


    @Override
	 public Optional<SERVICE> findbyidfonctionnaire(String idFonctionnaire) {
		 
		 
	        Optional<FONCTIONNAIRE> FONCTIONNAIRE = fonctionnaireRepository.findById(idFonctionnaire);

	        // Récupérez le service associé au fonctionnaire
           return servicerepository.findById(FONCTIONNAIRE.get().getService().getId()).map(service -> {
               // Faites ici tout traitement supplémentaire avant de retourner le service
               return service;
           });
       
   }


    
}
