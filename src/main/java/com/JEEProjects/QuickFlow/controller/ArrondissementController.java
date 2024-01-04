package com.JEEProjects.QuickFlow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JEEProjects.QuickFlow.dto.ArrondissementDTO;
import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.repository.ArrondissementRepository;
import com.JEEProjects.QuickFlow.service.ArrondissmentService;

@Controller
@RestController
@RequestMapping("/api/arrondissements")
public class ArrondissementController {
    private final ArrondissmentService arronservice;
    private static final Logger logger = LoggerFactory.getLogger(ArrondissementController.class);
	  @Autowired
	  public  ArrondissementRepository arrondissmentrepository;

    @Autowired
    public ArrondissementController(ArrondissmentService arronservice) {
        this.arronservice = arronservice;
    }

    @GetMapping("/findallarrondissements")
    public ResponseEntity<?> findall() {
        try {
            
        	List<ARRONDISSEMENT> allarrondissement = arrondissmentrepository.findAll();

            if (allarrondissement != null && !allarrondissement.isEmpty()) {
            	  int numberOfArrondissements = allarrondissement.size();
            	 List<String> arrondissementsIds = allarrondissement.stream()
     	                .map(ARRONDISSEMENT::getId)
     	                .collect(Collectors.toList());
            	 // Create a response object containing the count and IDs
                 Map<String, Object> response = new HashMap<>();
                 response.put("numberOfArrondissements", numberOfArrondissements);
                 response.put("arrondissementsIds", arrondissementsIds);

                 return ResponseEntity.ok(response);
            } else {
                // If no reclamation found with the provided 
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving all the reclamations: " + ex.getMessage());
        }
    
    
}
    @GetMapping("/list")
    public ResponseEntity<List<ArrondissementDTO>> getAllArrondissements() {
        List<ARRONDISSEMENT> arrondissements = arronservice.getAllArrondissement();

        // Log the arrondissements to the console
        logger.info("Arrondissements: {}", arrondissements);

        // Convert entities to DTOs
        List<ArrondissementDTO> arrondissementDTOs = convertToDTOs(arrondissements);

        // Return DTOs instead of entities
        return new ResponseEntity<>(arrondissementDTOs, HttpStatus.OK);
    }

    private List<ArrondissementDTO> convertToDTOs(List<ARRONDISSEMENT> arrondissements) {
        // Implement the conversion logic based on your requirements
        // For simplicity, you can use a stream and map the entities to DTOs
        return arrondissements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ArrondissementDTO convertToDTO(ARRONDISSEMENT arrondissement) {
        // Implement the conversion logic based on your requirements
        // For simplicity, you can use a constructor or model mapper
        return new ArrondissementDTO(
                arrondissement.getId(),
                arrondissement.getAdresse(),
                arrondissement.getCommune(),
                arrondissement.getCreatedOn(),
                arrondissement.getUpdatedOn()
                // Add other fields as needed
        );
    }

}
