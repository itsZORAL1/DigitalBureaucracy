package com.JEEProjects.QuickFlow.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserDto {

	@Id
	private String id;
	 private String nom;
	 private String prenom;
	 private String email;
	 private String role;
	 private String hashedPassword;
	 private Date dateNaissance;
	 private String nationalite;
	 private String adresse;
	 private String sexe;
	 private String profilePictureLocation;


	}


