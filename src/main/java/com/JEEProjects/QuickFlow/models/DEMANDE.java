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
@Document(collection = "DEMANDE")
public class DEMANDE {
	@Id
	private String _id; 
	@DBRef
	private SERVICE service;
	@DBRef
	private CITOYEN citoyen;
	private StatusDemande status;
	private String motifrejet;

	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;

}
