package com.JEEProjects.QuickFlow.controller;




import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.DEMANDE;
import com.JEEProjects.QuickFlow.models.RECLAMATION;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.models.StatusDemande;
import com.JEEProjects.QuickFlow.models.StatusReclmation;
import com.JEEProjects.QuickFlow.repository.AdminRepository;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.DemandeRepository;
import com.JEEProjects.QuickFlow.repository.FonctionnaireRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;
import com.JEEProjects.QuickFlow.service.CINvalidation;

@Controller
@RestController
@RequestMapping("/api/demande")
public class DemandeController {
	
	
	 private static final Logger log = LoggerFactory.getLogger(DemandeController.class);
	 
	 
	  @Autowired
	   DemandeRepository demanderepository;
	  @Autowired
	  CitoyenRepository citoyenrepository;

	  @Autowired
	  FonctionnaireRepository fonctionnairerepository;

	  @Autowired
	  AdminRepository adminrepository;

	  @Autowired
	  CINvalidation citoyenservice;
	  @Autowired
	  ServicesRepository servicRepo;
	  
	  
	    @GetMapping("/findalldemandes")
	    public ResponseEntity<?> findall() {
	        try {
	            
	        	List<DEMANDE> alldemandes = demanderepository.findAll();
	        	 if (alldemandes != null) {
	        	int numberOfDemandes = alldemandes.size();
	            Map<String, Object> response = new HashMap<>();
	            response.put("numberOfDemandes", numberOfDemandes);
	            response.put("demandes", alldemandes);
	            return ResponseEntity.ok(response);
	           
	        } else {
	            // If no demands found
	            return ResponseEntity.ok(Collections.emptyMap());
	        }
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error retrieving all the demands: " + ex.getMessage());
	    }
	
	    }
	  @PostMapping("/adddemande")
	  public ResponseEntity<?> addreclamation(@RequestBody Map<String, String> requestBody) {

	      try {
	    	 
	    	  String serviceId = requestBody.get("serviceId");
	    	  String citoyenId = requestBody.get("citoyenId");
	    	  DEMANDE demande = new DEMANDE();

	    	  // Assuming that 'servicRepo' and 'citoyenrepository' are your repository instances

	    	  Optional<SERVICE> serviceOptional = servicRepo.findById(serviceId);
	    	  Optional<CITOYEN> citoyenOptional = citoyenrepository.findById(citoyenId);

	    	  if (serviceOptional.isPresent() && citoyenOptional.isPresent()) {
	    	      // Both service and citoyen are present, set them in your DEMANDE entity
	    	      SERVICE service = serviceOptional.get();
	    	      CITOYEN citoyen = citoyenOptional.get();

	    	      demande.setStatus(StatusDemande.APPROUVEE);
	    	      demande.setService(service);
	    	      demande.setCitoyen(citoyen);

	    	      // Perform additional operations if needed

	    	      // Save the demande entity to the database
	    	      demanderepository.save(demande);
	    	  } else {
	    	      // Handle the case where either service or citoyen is not found
	    	      // You might want to throw an exception, log an error, or handle it in a way suitable for your application
	    	  }

	          // Retourner une réponse indiquant que la réclamation a été ajoutée avec succès
	          return ResponseEntity.ok("demande added successfully!");

	      } catch (Exception ex) {
	          log.error(ex.getMessage());
	          return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }

	  private long generateRandomId() {
	      // Logique pour générer un identifiant aléatoire
	      Random random = new Random();
	      return random.nextLong();
	  }

}