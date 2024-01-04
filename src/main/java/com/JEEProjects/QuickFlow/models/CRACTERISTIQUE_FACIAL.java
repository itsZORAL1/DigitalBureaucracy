package com.JEEProjects.QuickFlow.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 import java.awt.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "CRACTERISTIQUE_FACIAL")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CRACTERISTIQUE_FACIAL {
	 @Id
	 private String id;
	 private String faceDescriptor;
	 private String faceLandmarks;
	 private String imageLocation;
	 @DBRef
	 private CITOYEN citoyen;
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;
}
