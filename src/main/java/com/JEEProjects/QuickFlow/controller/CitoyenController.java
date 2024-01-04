package com.JEEProjects.QuickFlow.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.JEEProjects.QuickFlow.dto.UserDto;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.FONCTIONNAIRE;
import com.JEEProjects.QuickFlow.models.ADMIN;
import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.User;
import com.JEEProjects.QuickFlow.repository.AdminRepository;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.FonctionnaireRepository;
import com.JEEProjects.QuickFlow.service.ArrondissmentService;
import com.JEEProjects.QuickFlow.service.CINvalidation;
import com.JEEProjects.QuickFlow.service.CitoyenService;
import com.JEEProjects.QuickFlow.service.Implementation.FileStorageService;

import org.springframework.validation.Validator;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Controller
@RestController
@RequestMapping("/api/citoyens")
@CrossOrigin(origins = "http://localhost:3000")
public class CitoyenController {
	
private CitoyenService citservice;
private  ArrondissmentService arronservice;
private static final Logger log = LoggerFactory.getLogger(CitoyenController.class);

@Autowired
CitoyenRepository citoyenrepository;
@Autowired
private FileStorageService fileStorageService;

@Autowired
FonctionnaireRepository fonctionnairerepository;

@Autowired
AdminRepository adminrepository;

@Autowired
CINvalidation citoyenservice;


    @Autowired
    public CitoyenController(CitoyenService citservice,ArrondissmentService arronservice) {
    	this.citservice= citservice;
    	this.arronservice=arronservice;
    }
    @GetMapping("/findallcitoyens")
    public ResponseEntity<?> findAllCitoyens() {
        try {
            List<CITOYEN> allCitoyens = citoyenrepository.findAll();

            if (allCitoyens != null && !allCitoyens.isEmpty()) {
                // Return the list of citizen objects along with the count
                Map<String, Object> response = new HashMap<>();
                response.put("numberOfCitoyens", allCitoyens.size());
                response.put("citoyens", allCitoyens);

                return ResponseEntity.ok(response);
            } else {
                // If no citizens found
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving citizens: " + ex.getMessage());
        }
    }
    @GetMapping("/getCitoyens/{idfonctio}")
    public ResponseEntity<?> getCitoyens(@PathVariable("idfonctio") String idfonctio) {
        try {
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
                    if (citoyen.getUsername() == null && citoyen.getPassword() == null) {
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
    @GetMapping("/findcitoyen/{id}")
    public ResponseEntity<?> getCitoyenById(@PathVariable String id) {
        try {
            // Vérifier si l'ID est valide
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid input value for ID");
            }

            // Rechercher le citoyen par son ID
            Optional<CITOYEN> citoyenOptional = citoyenrepository.findById(id);

            if (citoyenOptional.isPresent()) {
                // Retourner le citoyen trouvé
                CITOYEN citoyen = citoyenOptional.get();
                return ResponseEntity.ok(citoyen);
            } else {
                // Si aucun citoyen n'est trouvé avec l'ID donné
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving Citoyen: " + ex.getMessage());
        }
    }
    @PostMapping("/validercitoyen/{cin}")
    public ResponseEntity<?> validateCitoyen(@PathVariable("cin") String cin) {
    	
        try {
            if (!citoyenrepository.existsById(cin)) {
                return ResponseEntity.badRequest().body("Error: Citoyen not found with cin " + cin);
            }

            citoyenservice.processCitizen(cin);

            return ResponseEntity.ok("Citoyen validated successfully!");

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/findbypassword/{password}")
    public ResponseEntity<?> findCitoyenByPassword(@PathVariable("password") String password) {
        try {
            // Assuming findByPassword returns a Citoyen object
        	List citoyen = (List) citoyenrepository.findByPassword(password);

            if (citoyen != null) {
                // Return the retrieved Citoyen
                return ResponseEntity.ok(citoyen);
            } else {
                // If no Citoyen found with the provided password
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving Citoyen: " + ex.getMessage());
        }
    
    
}
    @GetMapping("/verifiercode/{idcitoyen}/{code}")
    public ResponseEntity<?> verifiercode(@PathVariable("idcitoyen") String idcitoyen, @PathVariable("code") String code) {
        try {
        	System.out.println(idcitoyen);
            Optional<CITOYEN> citoyenOptional = citoyenrepository.findById(idcitoyen);

            if (citoyenOptional.isPresent()) {
                CITOYEN citoyen = citoyenOptional.get();
            	
                
                // Comparer le code fourni avec le code du citoyen
                if (code.equals(citoyen.getCode())) {
                	
                    return ResponseEntity.ok().body("Code valide pour le citoyen avec l'ID : " + idcitoyen);
                } else {
              
                    return ResponseEntity.badRequest().body("Code invalide pour le citoyen avec l'ID : " + idcitoyen);
                }
            } else {
                // Si aucun citoyen trouvé avec l'ID fourni
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erreur lors de la vérification du code : " + ex.getMessage());
        }
    }
    @DeleteMapping("/deletecitoyen/{cin}")
    public ResponseEntity<?> deleteCitoyen(@PathVariable("cin") String cin) {

        try {
            if (!citoyenrepository.existsById(cin)) {
                return ResponseEntity.badRequest().body("Error: Citoyen not found with cin " + cin);
            }

            citoyenrepository.deleteById(cin);

            return ResponseEntity.ok("Citoyen deleted successfully!");

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateinfo/{id}")
    public ResponseEntity<?> updateCitoyen(@PathVariable("id") String id,
    		@RequestBody Map<String, String> requestBody) {
        try {
        	  String username = requestBody.get("username");
              String password = requestBody.get("password");

        	  System.out.println("Received CIN: " + id);
        	  System.out.println("Received username: " + username);
            if (!citoyenrepository.existsById(id)) {
            	System.out.println("test1");
                return ResponseEntity.badRequest().body("Error: Citoyen not found with id " + id);
            }
           
            citoyenservice.updateUsernameById(id, username);
            citoyenservice.updatePasswordById(id, password);
            System.out.println("test2");
            return ResponseEntity.ok("Citoyen updated successfully!");

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
        	String id = loginRequest.get("cin");
        	String username = loginRequest.get("username");
        	String password = loginRequest.get("password");


            // Check for null or empty values
            if (id == null || username == null || password == null || username.isEmpty() || password.isEmpty()) {
            	return ResponseEntity.badRequest().body(Map.of("error", "Invalid input values"));

            }

            // Check if Citoyen exists with the given id
            Optional<CITOYEN> citoyenOptional = citoyenrepository.findById(id);
            if (citoyenOptional.isPresent()) {
                CITOYEN citoyen = citoyenOptional.get();
                if (citoyen.getUsername().equals(username) && citoyen.getPassword().equals(password)) {
                    // Citoyen login successful
                	return ResponseEntity.ok(Map.of("message", "Citoyen login successful!"));
                }
            }

            // Check if Fonctionnaire exists with the given id
            Optional<FONCTIONNAIRE> fonctionnaireOptional = fonctionnairerepository.findById(id);
            if (fonctionnaireOptional.isPresent()) {
                FONCTIONNAIRE fonctionnaire = fonctionnaireOptional.get();
                if (fonctionnaire.getUsername().equals(username) && fonctionnaire.getPassword().equals(password)) {
                    // Fonctionnaire login successful
                	return ResponseEntity.ok(Map.of("message", "Fonctionnaire login successful!"));
                }
            }

            // Check if Admin exists with the given id
            // Add similar logic for Admin
            Optional<ADMIN> adminOptional = adminrepository.findById(id);
            if (adminOptional.isPresent()) {
                ADMIN admin = adminOptional.get();
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    // Admin login successful
                    return ResponseEntity.ok("Admin login successful!");
                }
            }

           
        	return ResponseEntity.ok(Map.of("message", "JUST NOTHING"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity<>("{\"error\": \"Internal server error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
@GetMapping("/citoyens")   
public String Allusers(Model model) {

model.addAttribute("users", this.citservice.getAllCitoyen());

return "users-list";
}
@GetMapping("/isEmailUnique")
public ResponseEntity<Boolean> isEmailUnique(@RequestParam String email) {
    boolean isUnique = citservice.isEmailUnique(email);
    return ResponseEntity.ok(isUnique);
}


@PostMapping("/save")

public ResponseEntity<String> saveCitoyen(
        @RequestParam String id,
        @RequestParam LocalDate dateExpirationCarte,
        @RequestParam String nom,
        @RequestParam String prenom,
        @RequestParam String numTele,
        @RequestParam LocalDate dateNaissance,
        @RequestParam String adresse,
        @RequestParam String sexe,
        @RequestParam String email,
        @RequestParam("copieCinLégalisé") MultipartFile copieCinLégalisé,
        @RequestParam("photoProfile") MultipartFile photoProfile,
        @RequestParam String arrondissementId) throws IOException {
	   String fileName = fileStorageService.storeFile(copieCinLégalisé);

       // Create the emplacement path
       String emplacementPath =  fileName;

    // Perform validation as before...
    //if (citservice.isIdUnique(id)) {
        // If the id is not unique, return a response indicating the issue
     //   return new ResponseEntity<>("ID already exists. Please choose a different ID.", HttpStatus.BAD_REQUEST);
  //  }

    // If validation passed, proceed to save the citoyen
    ARRONDISSEMENT arrondissement = arronservice.getArrondissementById(arrondissementId);
    

    // Create a CITOYEN object with the provided fields
    CITOYEN citoyen = CITOYEN.builder()
            .id(id)
            .dateExpirationCarte(dateExpirationCarte)
            .nom(nom)
            .prenom(prenom)
            .numTele(numTele)
            .dateNaissance(dateNaissance)
            .adresse(adresse)
            .sexe(sexe)
            .email(email)
            .copieCinLegalise(emplacementPath )
            .photoProfile(photoProfile.getBytes())
            .arrondissement(arrondissement)
            .build();

    // Save the citoyen
    citservice.addCitoyen(citoyen);
    citservice.DemandeInscription(citoyen);
    
    

    // Return a response indicating success
    return new ResponseEntity<>("Citoyen saved successfully", HttpStatus.OK);
}


}
