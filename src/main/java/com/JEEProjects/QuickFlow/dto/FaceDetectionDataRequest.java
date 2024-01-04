package com.JEEProjects.QuickFlow.dto;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.COMMUNE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceDetectionDataRequest {
    private List<Float> faceDescriptor;
    private List<Point> faceLandmarks;
    private String imageLocation;
    private CITOYEN citoyen;
    private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
    
}

