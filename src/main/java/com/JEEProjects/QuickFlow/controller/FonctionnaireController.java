package com.JEEProjects.QuickFlow.controller;

import com.JEEProjects.QuickFlow.models.FONCTIONNAIRE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.JEEProjects.QuickFlow.repository.ArrondissementRepository;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.FonctionnaireRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository; // Import the ServicesRepository
import com.JEEProjects.QuickFlow.service.Implementation.FonctionnaireServiceImplementation;
import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.SERVICE; // Import the SERVICE class

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/fonctionnaires")
public class FonctionnaireController {

    private final FonctionnaireServiceImplementation fonctionnaireService;
    private final ArrondissementRepository arrondissementRepository;
    private final ServicesRepository servicesRepository; // Inject the ServicesRepository
    @Autowired
    public FonctionnaireRepository fonctionnairerepository;
    @Autowired
    public CitoyenRepository  citoyenrepository;
    @Autowired
    public FonctionnaireController(
            FonctionnaireServiceImplementation fonctionnaireService,
            ArrondissementRepository arrondissementRepository,
            ServicesRepository servicesRepository
    ) {
        this.fonctionnaireService = fonctionnaireService;
        this.arrondissementRepository = arrondissementRepository;
        this.servicesRepository = servicesRepository;
    }
    @GetMapping("/findallfonctionnaires")
    public ResponseEntity<?> findall() {
        try {
            List<FONCTIONNAIRE> allfonct = fonctionnairerepository.findAll();

            if (allfonct != null) {
                // Return the retrieved Fonctionnaires along with the count
                Map<String, Object> response = new HashMap<>();
                response.put("numberOfFonctionnaires", allfonct.size());
                response.put("fonctionnaireData", allfonct);
                return ResponseEntity.ok(response);
            } else {
                // If no Fonctionnaire found
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving all the fonctionnaires: " + ex.getMessage());
        }
    }

    @GetMapping("/getCitoyensforcrud/{idfonctio}")
    public ResponseEntity<?> getCitoyenCRUD(@PathVariable("idfonctio") String idfonctio) {
        try {
        	System.out.println("HIIIIIIIIFONC");
            // Assuming findByPassword returns a Fonctionnaire object
            Optional<FONCTIONNAIRE> fonctionnaireOptional = fonctionnairerepository.findById(idfonctio);

            if (fonctionnaireOptional.isPresent()) {
                FONCTIONNAIRE fonctionnaire = fonctionnaireOptional.get();
                ARRONDISSEMENT arrondissement = fonctionnaire.getArrondissement();

                // Récupérer la liste des citoyens de l'arrondissement
                List<CITOYEN> citoyens = citoyenrepository.findByArrondissement(arrondissement);

                List<CITOYEN> citoyensSansAuth = new ArrayList<>();

                // Parcourir les citoyens et récupérer ceux avec username ou password égaux à null
                for (CITOYEN citoyen : citoyens) {
                    if (citoyen.getUsername() != null && citoyen.getPassword() != null) {
                        citoyensSansAuth.add(citoyen);
                    }
                }

                if (!citoyensSansAuth.isEmpty()) {
                    // Vous avez la liste des citoyens correspondants
                    return ResponseEntity.ok().body(citoyensSansAuth);
                } else {
                    // Aucun citoyen trouvé avec les critères spécifiés
                    return ResponseEntity.notFound().build();
                }
            } else {
                // Aucun fonctionnaire trouvé avec l'ID fourni
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erreur lors de la récupération des citoyens : " + ex.getMessage());
        }
    }
    @GetMapping("/findservicebyfonc/{idfonctio}")
	public ResponseEntity<?> getServiceIdByfonctio(@PathVariable String idfonctio) {
	    try {
	        // Vérifier si l'ID est valide
	        if (idfonctio == null || idfonctio.isEmpty()) {
	            return ResponseEntity.badRequest().body("Invalid input value for ID");
	        }

	        // Rechercher le service par son ID
	        Optional<SERVICE> serviceOptional = fonctionnaireService.findbyidfonctionnaire(idfonctio);

	        if (serviceOptional.isPresent()) {
	            // Récupérer l'ID du service trouvé
	            SERVICE service = serviceOptional.get();
	            String serviceId = service.getId(); // Supposons que l'ID soit une chaîne de caractères
                  System.out.println(serviceId);
	         
	            return ResponseEntity.ok(serviceId);
	        } else {
	            // Si aucun service n'est trouvé avec l'ID donné
	            return ResponseEntity.notFound().build();
	        }

	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error retrieving Service ID: " + ex.getMessage());
	    }
	}
    @GetMapping("/findfonctibyser/{idservice}")
	public ResponseEntity<?> getfonctiIdByservice(@PathVariable String idservice) {
	    try {
	        // Vérifier si l'ID est valide
	        if (idservice == null || idservice.isEmpty()) {
	            return ResponseEntity.badRequest().body("Invalid input value for ID");
	        }

	        // Rechercher le service par son ID
	        Optional<SERVICE> serviceOptional = servicesRepository.findById(idservice);

	        if (serviceOptional.isPresent()) {
	            // Récupérer le service trouvé
	            SERVICE service = serviceOptional.get();

	            // Récupérer les fonctionnaires associés à ce service
	           List<FONCTIONNAIRE> fonctionnaires = fonctionnairerepository.findByService(service);
	           String fonctionnaireid="";
	           if(!fonctionnaires.isEmpty()&&fonctionnaires!=null) {
            	   fonctionnaireid= fonctionnaires.get(0).getId();
               }

	            // Retourner les ID des fonctionnaires
	           return ResponseEntity.ok(Collections.singletonMap("fonctionnaireid", fonctionnaireid));

	        } else {
	            // Si aucun service n'est trouvé avec l'ID donné
	            return ResponseEntity.notFound().build();
	        }

	    } catch (Exception ex) {
	    	 ex.printStackTrace(); // Log the exception details
	    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	    	            .body("Error retrieving Service ID: " + ex.getMessage());
	    }
	}
    @PostMapping
    public FONCTIONNAIRE addFonctionnaire(@RequestBody FONCTIONNAIRE fonctionnaire) {
        String imagePath = "/images/sign1.png";
        String timbre="/images/cachet1.png";

        fonctionnaire.setSignature(imagePath);
        fonctionnaire.setLégalisé(timbre);

        Optional<ARRONDISSEMENT> optionalArrondissement = arrondissementRepository.findById("Aïn Sebaâ");

        ARRONDISSEMENT arrondissement = optionalArrondissement.orElseThrow(() ->
                new RuntimeException("ARRONDISSEMENT not found for ID: Aïn Chock"));

        // Find existing SERVICE by ID
        Optional<SERVICE> optionalService = servicesRepository.findById("delivrer des certificats de residence");

        SERVICE service = optionalService.orElseThrow(() ->
                new RuntimeException("SERVICE not found for ID: traiter les demandes d'inscription"));

    
        fonctionnaire.setArrondissement(arrondissement);
        fonctionnaire.setService(service);
       
        // Set other properties as needed

        System.out.println("ARRONDISSEMENT before saving: " + fonctionnaire.getArrondissement());

        return fonctionnaireService.saveFonctionnaire(fonctionnaire);
    }
 
}
