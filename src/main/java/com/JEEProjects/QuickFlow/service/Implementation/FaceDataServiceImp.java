package com.JEEProjects.QuickFlow.service.Implementation;

import com.JEEProjects.QuickFlow.models.CRACTERISTIQUE_FACIAL;
import com.JEEProjects.QuickFlow.repository.FaceDetectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceDataServiceImp {

    private final FaceDetectionRepository faceDataRepository;

    @Autowired
    public FaceDataServiceImp(FaceDetectionRepository faceDataRepository) {
        this.faceDataRepository = faceDataRepository;
    }

    public CRACTERISTIQUE_FACIAL getFaceDataByCitoyenId(String id) {
        return faceDataRepository.findByCitoyenId(id);
    }
}
