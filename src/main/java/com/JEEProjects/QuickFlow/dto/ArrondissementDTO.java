package com.JEEProjects.QuickFlow.dto;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.JEEProjects.QuickFlow.models.COMMUNE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrondissementDTO {
    private String id;
    private String adresse;
    private COMMUNE commune;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    // Constructors, getters, and setters
}
