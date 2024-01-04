package com.JEEProjects.QuickFlow.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "DOCUMENT")
public class DOCUMENT {
	@Id
	private String _id; 
	private String emplacement;
	@DBRef
	private CITOYEN citoyen;
	private  String signaturefonc="cachet1.png";
	

	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;

}