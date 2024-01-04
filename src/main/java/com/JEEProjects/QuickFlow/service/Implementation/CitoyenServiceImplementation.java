package com.JEEProjects.QuickFlow.service.Implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.DEMANDE;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.models.StatusDemande;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.DemandeRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;
import com.JEEProjects.QuickFlow.service.CitoyenService;

@Service
public class CitoyenServiceImplementation implements CitoyenService {
    private CitoyenRepository CitRep;
    private DemandeRepository DemandeRep;
    private ServicesRepository serv;

    @Autowired
    public CitoyenServiceImplementation(CitoyenRepository CitRep,DemandeRepository DemandeRep,ServicesRepository serv) {
        this.CitRep = CitRep;
        this.DemandeRep=DemandeRep;
        this.serv=serv;
    }
    @Override
    public boolean isIdUnique(String id) {
        return !CitRep.existsById(id);
    }
    @Override
    public List<CITOYEN> getAllCitoyen() {
        return this.CitRep.findAll();
    }

    @Override
    public CITOYEN addCitoyen(CITOYEN citoyen) {
        // Check if associated entities are not null
        if (citoyen.getDemandes() != null) {
            citoyen.getDemandes().forEach(d -> {
             
                d.setCitoyen(citoyen);
            });
        }

        if (citoyen.getReclmations() != null) {
            citoyen.getReclmations() .forEach(r -> {
               
                r.setCitoyen(citoyen);
            });
        }

       

        // Save the citoyen with cascaded changes
        return CitRep.save(citoyen);
    }
    @Override
    public boolean isEmailUnique(String email) {
        return !CitRep.existsByEmail(email);
    }
	@Override
	public void DemandeInscription(CITOYEN citoyen) {
		DEMANDE demande =new DEMANDE();
		 SERVICE ser = serv.findById("traiter les demandes d'inscription")
		            .orElseThrow(() -> new RuntimeException("Service not found with ID: traiter les demandes d'inscription"));
		demande.setCitoyen(citoyen);
		demande.setService(ser);
		demande.setStatus(StatusDemande.EN_ATTENTE);
		DemandeRep.save(demande);
		
	}

  
}
