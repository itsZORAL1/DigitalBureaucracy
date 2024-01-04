package com.JEEProjects.QuickFlow.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ARRONDISSEMENT")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ARRONDISSEMENT {
    @Id
    private String id; // nom arrondissement
    private String adresse;
    @DBRef
    private COMMUNE commune;
    @DBRef
    private List<FONCTIONNAIRE> fonctionnaires;
    @DBRef
    private List<CITOYEN> citoyens;
    @DBRef
    private ADMIN admin;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @Override
    public String toString() {
        return "ARRONDISSEMENT{" +
                "id='" + id + '\'' +
                ", adresse='" + adresse + '\'' +
                // Exclude fields that contribute to the cyclic reference
                // ", commune=" + commune +
                // ", fonctionnaires=" + fonctionnaires +
                // ", citoyens=" + citoyens +
                // ", admin=" + admin +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
