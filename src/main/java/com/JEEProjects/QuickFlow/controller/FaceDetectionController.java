package com.JEEProjects.QuickFlow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.JEEProjects.QuickFlow.models.CRACTERISTIQUE_FACIAL;
import com.JEEProjects.QuickFlow.service.Implementation.FaceDetectionService;

@RestController
@RequestMapping("/api/face-detection")
public class FaceDetectionController {

    @Autowired
    private FaceDetectionService faceDetectionService;

    @PostMapping("/save")
    public ResponseEntity<String> saveFaceDetectionData(
            @RequestParam("faceDescriptor") String faceDescriptor,
            @RequestParam("faceLandmarks") String faceLandmarks,
            @RequestParam("imageLocation") String imageLocation,
            @RequestParam("citoyenId") String citoyenId) {
        faceDetectionService.saveFaceDetectionData(faceDescriptor, faceLandmarks, imageLocation, citoyenId);
        return ResponseEntity.ok("Face detection data saved successfully.");
    }

    @GetMapping("/id")
    public ResponseEntity<CRACTERISTIQUE_FACIAL> getByCitoyenId(@RequestParam String citoyenId) {
        
        try {
            CRACTERISTIQUE_FACIAL faceDetectionData = faceDetectionService.getByCitoyenId(citoyenId);

            if (faceDetectionData != null) {
                return ResponseEntity.ok(faceDetectionData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
