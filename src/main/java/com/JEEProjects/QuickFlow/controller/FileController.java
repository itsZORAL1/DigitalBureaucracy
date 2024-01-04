package com.JEEProjects.QuickFlow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.DOCUMENT;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.documentRepository;
import com.JEEProjects.QuickFlow.service.Implementation.FileStorageService;

import java.io.IOException;
import java.util.Optional;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CitoyenRepository rep;
    @Autowired
    private documentRepository docy;
    @GetMapping("/alldocuments")
    public ResponseEntity<List<DOCUMENT>> getAllDocuments() {
        try {
            // Fetch all documents from the repository
            List<DOCUMENT> documents = docy.findAll();
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("cin") String cin
    ) {
        try {
            // Store the file in the specified location
         

            // Access the cin parameter here and use it as needed
            System.out.println("cin: " + cin);
            DOCUMENT doc= new DOCUMENT();
            Optional<CITOYEN> cit= rep.findById(cin);
            if (cit.isPresent()) {
            	   String fileName = fileStorageService.storeFile(file);

                   String emplacementPath =  fileName;
                CITOYEN citoyen = cit.get();
                doc.setCitoyen(citoyen);
                doc.setEmplacement(emplacementPath);
                // Set other properties of DOCUMENT as needed

                // Save the document entity to the database
                // Assuming you have a repository for DOCUMENT entities
                docy.save(doc);
            } 
            // Update the emplacement field in the DOCUMENT entity
            // (You need to inject a service to handle database operations)
            // DOCUUUUUUUUUUUUUMENT
         
            

            return ResponseEntity.ok("File uploaded successfully. File ID: " );
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file.");
        }
    }
}
