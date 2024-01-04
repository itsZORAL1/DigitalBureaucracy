package com.JEEProjects.QuickFlow.models;

import java.time.LocalDate;
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
@Document(collection = "CITOYEN")
public class CITOYEN {
	@Id
	private String id;//cin
	private LocalDate dateExpirationCarte;
	private String nom;
	private String prenom;
	private String numTele;
	private LocalDate dateNaissance;
	private String adresse;
	private String sexe;
	private String email;
	private String copieCinLegalise;
	private String username;
	private String password;
	private byte[] photoProfile;
	private String code;
	@DBRef
	private ARRONDISSEMENT arrondissement;

	@DBRef
	private List<RECLAMATION> reclmations;
	@DBRef
	private List<DEMANDE> demandes;
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;


}