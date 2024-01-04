package com.JEEProjects.QuickFlow.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.JEEProjects.QuickFlow.models.COMMUNE;
import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.repository.CommuneRepositoty;
import com.JEEProjects.QuickFlow.repository.ArrondissementRepository;
import com.JEEProjects.QuickFlow.service.CommuneService;

@Service
public class CommuneServiceImplementation implements CommuneService {
    private CommuneRepositoty communeRepository;
    private ArrondissementRepository arrondissementRepository;

    @Autowired
    public CommuneServiceImplementation(
            CommuneRepositoty communeRepository,
            ArrondissementRepository arrondissementRepository) {
        this.communeRepository = communeRepository;
        this.arrondissementRepository = arrondissementRepository;
    }

    @Override
    public COMMUNE addCommune(COMMUNE commune) {
        // Save the Commune
        COMMUNE savedCommune = communeRepository.save(commune);

      
        if (savedCommune.getArrondissements() != null) {
            for (ARRONDISSEMENT arrondissement : savedCommune.getArrondissements()) {
                arrondissement.setCommune(savedCommune);
                arrondissementRepository.save(arrondissement);
            }
        }

        return savedCommune;
    }
}
