package com.JEEProjects.QuickFlow.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.COMMUNE;
import com.JEEProjects.QuickFlow.repository.CommuneRepositoty;
import com.JEEProjects.QuickFlow.service.ArrondissmentService;
import com.JEEProjects.QuickFlow.service.CitoyenService;
import com.JEEProjects.QuickFlow.service.CommuneService;

@Controller
@RestController
@RequestMapping("/api/communes")
public class CommuneController {
	private CommuneService comservice;
	private CommuneRepositoty Communerepository;

    @Autowired
    public CommuneController (CommuneService comservice,CommuneRepositoty Communerepository) {
    	this.comservice= comservice;
    	this.Communerepository= Communerepository;
    }
   
    @GetMapping("/findallcommune")
    public ResponseEntity<?> findall() {
        try {
            
        	List<COMMUNE> allcommune = Communerepository.findAll();

            if (allcommune != null) {
               
                return ResponseEntity.ok(allcommune);
            } else {
                // If no reclamation found with the provided 
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving all the reclamations: " + ex.getMessage());
        }
    
    
}
    @GetMapping("/communePopulationStats")
    public ResponseEntity<?> getCommunePopulationStats() {
        try {
            List<COMMUNE> allCommunes = Communerepository.findAll();

            if (allCommunes != null) {
                // Create a list to store population stats for each commune
                List<Map<String, Object>> communeStats = new ArrayList<>();

                for (COMMUNE commune : allCommunes) {
                    Map<String, Object> communeStat = new HashMap<>();
                    communeStat.put("communeName", commune.getVille()); // Assuming 'ville' is the commune name
                    communeStat.put("population", commune.getPopulation());
                    communeStats.add(communeStat);
                }

                return ResponseEntity.ok(communeStats);
            } else {
                // If no communes found
                return ResponseEntity.ok(Collections.emptyList());
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving commune population stats: " + ex.getMessage());
        }
    }


    
/*@GetMapping("/communes")   
public String Allcommunes(Model model) {

model.addAttribute("users", this.comservice.getAllCitoyen());

return "users-list";
}*/
@PostMapping("/add")
public ResponseEntity<?> addCommune(@RequestBody @Validated COMMUNE commune, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    COMMUNE com=comservice.addCommune(commune);
    return new ResponseEntity<>(com, HttpStatus.CREATED);
}


}
