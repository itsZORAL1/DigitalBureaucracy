package com.JEEProjects.QuickFlow.models;

import java.sql.Date;
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
@Document(collection = "COMMUNE")
public class COMMUNE {
	@Id
	private String id; //nom commune
	 public COMMUNE(String id) {
	        this.id = id;
	    }
	private String ville;
	private String pays;
	private String population;
	@DBRef
	private List<ARRONDISSEMENT> arrondissements;
    
	 @CreationTimestamp
	 private LocalDateTime createdOn;
	 @UpdateTimestamp
	 private LocalDateTime updatedOn;
	 

}
