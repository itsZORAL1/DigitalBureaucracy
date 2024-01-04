package com.JEEProjects.QuickFlow.models;

import java.time.LocalDateTime;
import java.util.List;

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
@Document(collection = "FONCTIONNAIRE")
public class FONCTIONNAIRE {
	@Id
	private String id;//cin
	private String nom;
	private String prenom;
	private String numTele;
	private String email;
	private String username;
	private String password;
	private String photoProfile;
	private String signature;
	private String légalisé;
	@DBRef
	private ARRONDISSEMENT arrondissement;
	@DBRef
	private SERVICE service;
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;


}
