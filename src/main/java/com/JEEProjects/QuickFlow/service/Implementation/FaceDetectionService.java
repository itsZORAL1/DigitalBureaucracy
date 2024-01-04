package com.JEEProjects.QuickFlow.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.JEEProjects.QuickFlow.models.CRACTERISTIQUE_FACIAL;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.DemandeRepository;
import com.JEEProjects.QuickFlow.repository.FaceDetectionRepository;
import com.JEEProjects.QuickFlow.repository.ServicesRepository;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FaceDetectionService {

    @Autowired
    private FaceDetectionRepository faceDetectionRepository;
    private CitoyenRepository CitRep;
    @Autowired
    public FaceDetectionService(CitoyenRepository CitRep,FaceDetectionRepository faceDetectionRepository) {
       this.CitRep=CitRep;
       this.faceDetectionRepository=faceDetectionRepository;
    }
    // Implement the logic to save face detection data
    public void saveFaceDetectionData( String faceDescriptor, String faceLandmarks,
                                      String imageLocation, String citoyenId) {
        // Create a new face detection object
		CRACTERISTIQUE_FACIAL faceDetection = new CRACTERISTIQUE_FACIAL();
		faceDetection.setFaceDescriptor(faceDescriptor);
		faceDetection.setFaceLandmarks(faceLandmarks);
		faceDetection.setImageLocation(imageLocation);
		CITOYEN cit= CitRep.findById(citoyenId)
		            .orElseThrow(() -> new RuntimeException("citoyen n'existe pas"));
		faceDetection.setCitoyen(cit);

		// Save to MongoDB
		faceDetectionRepository.save(faceDetection);
    }

    public CRACTERISTIQUE_FACIAL getByCitoyenId(String citoyenId) {
        // Your logic to fetch data from the repository based on citoyenId
        Optional<CRACTERISTIQUE_FACIAL> optionalFaceDetection = faceDetectionRepository.findById(citoyenId);

        // Return the fetched data, or null if not found
        return optionalFaceDetection.orElse(null);
    }
}
