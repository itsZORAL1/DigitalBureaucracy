package com.JEEProjects.QuickFlow.service;

import java.util.Optional;

import com.JEEProjects.QuickFlow.models.SERVICE;


public interface Fonctionnaireserv {
	
	Optional<SERVICE>  findbyidfonctionnaire(String idfon);

}