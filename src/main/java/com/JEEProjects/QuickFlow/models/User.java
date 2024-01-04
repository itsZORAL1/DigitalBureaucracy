package com.JEEProjects.QuickFlow.models;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@Document(collection = "users")
public class User {
@Id
private String id;
 private String nom;
 private String prenom;
 private String email;
 private String role;
 private String Password;
 private String hashedPassword;
 private Date dateNaissance;
 private String nationalite;
 private String adresse;
 private String sexe;
 private String profilePictureLocation;
 @CreationTimestamp
 private LocalDateTime createdOn;
 @UpdateTimestamp
 private LocalDateTime updatedOn;

 
}
