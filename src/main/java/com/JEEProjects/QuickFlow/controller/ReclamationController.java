package com.JEEProjects.QuickFlow.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JEEProjects.QuickFlow.models.RECLAMATION;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.models.StatusReclmation;
import com.JEEProjects.QuickFlow.repository.ReclamationRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;



@Controller
@RestController
@RequestMapping("/api/reclamation")

public class ReclamationController {
	
	 private static final Logger log = LoggerFactory.getLogger(ReclamationController.class);
	 
	 
	 
	  
	  @Autowired
	   ReclamationRepository reclamationrepository;
	  
	  @Autowired
	   ServicesRepository servicerepository;
	  @GetMapping("/countByStatus")
	    public Map<String, Long> getCountByStatus() {
	        Map<String, Long> countByStatus = new HashMap<>();

	        for (StatusReclmation status : StatusReclmation.values()) {
	            long count = reclamationrepository.countByStatus(status);
	            countByStatus.put(status.name(), count);
	        }

	        return countByStatus;
	    }
	  @GetMapping("/findallreclamations")
	  public ResponseEntity<?> findAllReclamations() {
	      try {
	          List<RECLAMATION> allReclamations = reclamationrepository.findAll();

	          if (allReclamations != null && !allReclamations.isEmpty()) {
	              // Return the list of reclamation objects along with the count
	              Map<String, Object> response = new HashMap<>();
	              response.put("numberOfReclamations", allReclamations.size());
	              response.put("reclamations", allReclamations);

	              return ResponseEntity.ok(response);
	          } else {
	              // If no reclamations found
	              return ResponseEntity.notFound().build();
	          }

	      } catch (Exception ex) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                               .body("Error retrieving reclamations: " + ex.getMessage());
	      }
	  }
	  @PutMapping("/rejeterreclamation/{idReclamation}")
	  public ResponseEntity<?> rejeterReclamation(@PathVariable("idReclamation") String idReclamation) {
	      try {
	          // Recherche de la réclamation par ID
	          Optional<RECLAMATION> optionalReclamation = reclamationrepository.findById(idReclamation);

	          if (optionalReclamation.isPresent()) {
	              RECLAMATION reclamation = optionalReclamation.get();

	              // Mettre à jour le statut de la réclamation à "REJETEE"
	              reclamation.setStatus(StatusReclmation.REJETEE);

	              // Enregistrer la réclamation mise à jour dans la base de données
	              reclamationrepository.save(reclamation);

	              // Retourner une réponse indiquant que la réclamation a été rejetée avec succès
	              return ResponseEntity.ok("Réclamation rejetée avec succès.");
	          } else {
	              // La réclamation avec l'ID spécifié n'a pas été trouvée
	              return ResponseEntity.notFound().build();
	          }
	      } catch (Exception ex) {
	          log.error(ex.getMessage());
	          return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }
	  @PutMapping("/accepterreclamation/{idReclamation}")
	  public ResponseEntity<?> accepterReclamation(@PathVariable("idReclamation") String idReclamation) {
	      try {
	          // Recherche de la réclamation par ID
	          Optional<RECLAMATION> optionalReclamation = reclamationrepository.findById(idReclamation);
	         
	          System.out.println("lamiaa");
	          if (optionalReclamation.isPresent()) {
	        	  System.out.println("benejma");
	              RECLAMATION reclamation = optionalReclamation.get();

	              // Mettre à jour le statut de la réclamation à "EN_COURS_DE_TRAITEMENT"
	              reclamation.setStatus(StatusReclmation.EN_COURS_DE_TRAITEMENT);
	             

	              // Enregistrer la réclamation mise à jour dans la base de données
	              reclamationrepository.save(reclamation);

	              // Retourner une réponse indiquant que la réclamation a été acceptée avec succès
	              return ResponseEntity.ok("Réclamation acceptée avec succès.");
	          } else {
	              // La réclamation avec l'ID spécifié n'a pas été trouvée
	              return ResponseEntity.notFound().build();
	          }
	      } catch (Exception ex) {
	          log.error(ex.getMessage());
	          return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }

	  @GetMapping("/getreclamation/{idservice}")
	  public ResponseEntity<?> getReclamationsEnAttente(@PathVariable("idservice") String idservice) {
	      try {
	          // Recherche du service par ID
	          Optional<SERVICE> optionalService = servicerepository.findById(idservice);

	          if (optionalService.isPresent()) {
	              SERVICE service = optionalService.get();

	              // Appel de la méthode findByService
	              List<RECLAMATION> reclamations = reclamationrepository.findByService(service);

	              // Filtrer les réclamations avec un statut en attente
	              List<RECLAMATION> reclamationsEnAttente = new ArrayList<>();
	              for (RECLAMATION reclamation : reclamations) {
	                  if (StatusReclmation.EN_ATTENTE.equals(reclamation.getStatus())) {
	                      reclamationsEnAttente.add(reclamation);
	                  }
	              }

	              // Retourner une réponse indiquant que les réclamations en attente ont été récupérées avec succès
	              return ResponseEntity.ok(reclamationsEnAttente);
	          } else {
	              // Le service avec l'ID spécifié n'a pas été trouvé
	              return ResponseEntity.notFound().build();
	          }
	      } catch (Exception ex) {
	          log.error(ex.getMessage());
	          return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }

	  
	  @PostMapping("/addreclamation")
	  public ResponseEntity<?> addreclamation(@RequestBody RECLAMATION reclamation) {

	      try {
	          // Génération d'un identifiant aléatoire en tant que chaîne
	          String randomId = generateRandomId();

	          // Attribution de l'identifiant à la réclamation
	          reclamation.setId(randomId);

	          reclamation.setStatus(StatusReclmation.EN_ATTENTE);
	          reclamation.setDatereclamation(LocalDate.now());
	          // Appel de la méthode save
	          reclamationrepository.save(reclamation);

	          // Retourner une réponse indiquant que la réclamation a été ajoutée avec succès
	          return ResponseEntity.ok("Reclamation added successfully!");

	      } catch (Exception ex) {
	          log.error(ex.getMessage());
	          return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }

	  private String generateRandomId() {
	      // Génération d'un identifiant UUID (Universally Unique Identifier)
	      UUID uuid = UUID.randomUUID();

	      // Convertir UUID en chaîne et supprimer les tirets
	      String randomId = uuid.toString().replace("-", "");

	      return randomId;
	  }
}