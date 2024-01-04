package com.JEEProjects.QuickFlow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;
import com.JEEProjects.QuickFlow.service.CitoyenService;
import com.JEEProjects.QuickFlow.service.ServicesService;

@Controller
@RestController
@RequestMapping("/api/services")
public class ServicesController {
	private ServicesService services;
	@Autowired
	ServicesRepository servicerepository;

    @Autowired
    public ServicesController (ServicesService services) {
    	this. services= services;
    }
    
    @GetMapping("/findservice/{id}")
	public ResponseEntity<?> getServiceById(@PathVariable String id) {
	    try {
	        // Vérifier si l'ID est valide
	        if (id == null || id.isEmpty()) {
	            return ResponseEntity.badRequest().body("Invalid input value for ID");
	        }

	        // Rechercher le service par son ID
	        Optional<SERVICE> serviceOptional = servicerepository.findById(id);

	        if (serviceOptional.isPresent()) {
	            // Retourner le service trouvé
	            SERVICE service = serviceOptional.get();
	            return ResponseEntity.ok(service);
	        } else {
	            // Si aucun service n'est trouvé avec l'ID donné
	            return ResponseEntity.notFound().build();
	        }

	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error retrieving Service: " + ex.getMessage());
	    }
	}
    
    @GetMapping("/findallservicescount")
	public ResponseEntity<?> findallservicescount() {
	    try {
	        // Assuming findAll returns a List<SERVICE> object
	    	 List<SERVICE> services = servicerepository.findAll();

	         if (services != null && !services.isEmpty()) {
	             // Extract only the IDs from the services
	             List<String> serviceIds = services.stream()
	                 .map(SERVICE::getId)
	                 .collect(Collectors.toList());

	             // Return the list of service IDs along with the number of services
	             Map<String, Object> response = new HashMap<>();
	             response.put("numberOfServices", services.size());
	             response.put("serviceIds", serviceIds);

	             return ResponseEntity.ok(response);
	        } else {
	            // If no services found
	            return ResponseEntity.notFound().build();
	        }

	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error retrieving services: " + ex.getMessage());
	    }
	}
    @GetMapping("/findallservices")
	public ResponseEntity<?> findallservices() {
	    try {
	    	
	        // Assuming findAll returns a List<SERVICE> object
	        List<SERVICE> services = servicerepository.findAll();
	        
	        if (services != null && !services.isEmpty()) {
	            // Extract only the IDs from the services
	            List<String> serviceIds = services.stream()
	                .map(SERVICE::getId)
	                .collect(Collectors.toList());

	            System.out.println(serviceIds);
	            return ResponseEntity.ok(serviceIds);
	        } else {
	            // If no services found
	            return ResponseEntity.notFound().build();
	        }

	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error retrieving services: " + ex.getMessage());
	    }
	}
    
@PostMapping("/add")
public ResponseEntity<?> addService(@RequestBody @Validated SERVICE service, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
    SERVICE ser=services.addService(service);
    return new ResponseEntity<>(service, HttpStatus.CREATED);
}
}
