package com.JEEProjects.QuickFlow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.dto.UserDto;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.models.User;
@Service
public interface CitoyenService {
	
public CITOYEN addCitoyen(CITOYEN citoyen);
public List<CITOYEN> getAllCitoyen();
public boolean isEmailUnique(String email);
public boolean isIdUnique(String id);
public void DemandeInscription(CITOYEN citoyen);

}
