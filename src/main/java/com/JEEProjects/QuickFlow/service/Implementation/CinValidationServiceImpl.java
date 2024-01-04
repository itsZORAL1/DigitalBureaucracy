package com.JEEProjects.QuickFlow.service.Implementation;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.DEMANDE;
import com.JEEProjects.QuickFlow.models.StatusDemande;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.DemandeRepository;
import com.JEEProjects.QuickFlow.repository.FaceDetectionRepository;
import com.JEEProjects.QuickFlow.service.CINvalidation;
import com.JEEProjects.QuickFlow.service.Emailservice;
import java.util.List;


@Service

public class CinValidationServiceImpl  implements  CINvalidation{
	
	@Autowired
	CitoyenRepository citoyenrepository ;
	
	@Autowired
	Emailservice emailService ;
	
	@Autowired
	FaceDetectionRepository  caracterepository ;
	
	@Autowired
	DemandeRepository demanderepository ;
	
	  @Override
	    public boolean isCinValidForCity(String id, String city) {
	        if (!Character.isLetter(id.charAt(0))) {
	            return false;
	        }

	        String cityCode = getCinCityCode(id);
	        String[] expectedCityCodes = getCityCodes(city);

	        for (String expectedCode : expectedCityCodes) {
	            if (cityCode.startsWith(expectedCode)) {
	                return true;
	            }
	        }

	        return false;
	    }

	    private String getCinCityCode(String id) {
	        // Supposons que le code de la ville est dans les caractères 2 à 3 du CIN
	        return id.substring(0, 3);
	    }

	    private String[] getCityCodes(String city) {
	        // Associer chaque ville à ses préfixes CIN correspondants
	        switch (city.toLowerCase()) {
	            case "casablanca":
	                return new String[]{"B", "BA", "BB", "BE", "BH", "BJ", "BK", "BL", "BM", "BF", "BV", "BW","b","ba","bb","be","bh","bj","bk","bl","bm","bf","bv","bw"};
	            case "rabat":
	                return new String[]{"A", "AA", "AC", "AJ", "a", "aa", "ac", "aj"};
	            case "meknès":
	                return new String[]{"D", "DN","d","dn",};
	            case "marrakech":
	                return new String[]{"E","EE","e","ee"};
	            case "kenitra":
	                return new String[]{"FF","ff","G","g"};
	            default:
	                return new String[]{};
	        }
	    }
	    
	    @Override
	    public  boolean isDateAfterToday(LocalDate inputDate) {
	        // Obtenir la date d'aujourd'hui
	        LocalDate today = LocalDate.now();

	        // Vérifier si la date en entrée est supérieure à la date d'aujourd'hui
	        return inputDate.isAfter(today);
	    }

	    
	    public void processCitizen(String id) {
	        Optional<CITOYEN> citizenOptional = citoyenrepository.findById(id);

	        // Vérifier si le CIN est trouvé
	        if (citizenOptional.isPresent()) {
	            CITOYEN citizen = citizenOptional.get();

	            // Vérifier si le CIN est valide pour la ville et si la date est après aujourd'hui
	            if (isCinValidForCity(citizen.getId(), citizen.getArrondissement().getCommune().getVille()) &&
	                isDateAfterToday(citizen.getDateExpirationCarte())) {

	                // Générer un nom d'utilisateur et un mot de passe
	                String username = citizen.getNom();
	                String password = generatePassword();
	                String confidentialCode = generateConfidentialCode();

	                // Stocker dans la table du citoyen
	                citizen.setUsername(username);
	                citizen.setPassword(password);
	                citizen.setCode(confidentialCode);

	                // Vérifier si la liste de demandes n'est pas null avant de l'itérer
	                List<DEMANDE> demandes = demanderepository.findByCitoyen(citizen);
	                if (demandes != null) {
	                	
	                    for (DEMANDE demande : demandes) {
	                        // Assuming you have a method to get the service ID from a demand obje
	                    	System.out.println("LAMIAA");

	                        // Replace the condition inside the if statement with your specific logic
	                        if (demande.getService().getId().equals("traiter les demandes d'inscription")) {
	                        	System.out.println("sou");
	                        	
	                            demande.setStatus(StatusDemande.APPROUVEE);
	                            demanderepository.save(demande);

	                        }
	                    }
	                }

	                // Enregistrer les modifications dans la base de données
	                citoyenrepository.save(citizen);

	                String emailBody = "Cher Citoyen,\n\n" +
	                        "Nous sommes ravis de vous informer que votre compte a été créé avec succès.\n" +
	                        "Ci-dessous, vous trouverez vos informations d'authentification :\n\n" +
	                        "- <b>Nom d'utilisateur :</b> " + username + "\n" +
	                        "- <b>Mot de passe :</b> " + password + "\n" +
	                        "- <b>Code confidentiel :</b> " + confidentialCode + "\n\n" +
	                        "Veuillez conserver précieusement ces informations. Elles seront nécessaires pour vous connecter à notre application.\n\n" +
	                        "Cordialement,\n";

	                // Envoyer le courriel
	                emailService.sendSimpleMessage(citizen.getEmail(), " Informations d'authentification", emailBody);

	                // Continuer avec le reste du traitement
	                // ...
	            } else {
	                // Le CIN n'est pas valide pour la ville ou la date n'est pas après aujourd'hui
	                // Supprimer le citoyen de la base de données
	                citoyenrepository.deleteById(citizen.getId());
	                caracterepository.deleteByCitoyen(citizen);

	                // Vérifier si la liste de demandes n'est pas null avant de l'itérer
	                if (citizen.getDemandes() != null) {
	                    List<DEMANDE> demandes = demanderepository.findByCitoyen(citizen);
	                    if (demandes != null) {
	                        for (DEMANDE demande : demandes) {
	                            // Assuming you have a method to get the service ID from a demand obje

	                            // Replace the condition inside the if statement with your specific logic
	                            if (demande.getService().getId().equals("traiter les demandes d'inscription")) {
	                                demande.setStatus(StatusDemande.REJETEE);
	                                demande.setMotifrejet("INFORMATIONS INVALIDE");;
	                                
	                                demanderepository.save(demande);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	    
	    public static String generatePassword() {
	        final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	        final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	        final String DIGIT = "0123456789";
	        final String CHAR_ALPHANUMERIC = CHAR_LOWER + CHAR_UPPER + DIGIT;
	        final int PASSWORD_LENGTH = 8; // You can adjust the password length here

	        Random random = new Random();
	        StringBuilder password = new StringBuilder();

	        for (int i = 0; i < PASSWORD_LENGTH; i++) {
	            int characterSetSize = CHAR_ALPHANUMERIC.length();
	            int randomIndex = random.nextInt(characterSetSize);
	            char randomChar = CHAR_ALPHANUMERIC.charAt(randomIndex);
	            password.append(randomChar);
	        }

	        return password.toString();
	    }
	    public void updateUsernameById(String id, String username) {
	    	System.out.println(username+ "yes1");
	        if (username != null && !username.isEmpty()) {
	            citoyenrepository.findById(id).ifPresent(citoyen -> {
	                citoyen.setUsername(username);
	                citoyenrepository.save(citoyen);
	            });
	        } else {
	            // Handle the case where newUsername is empty or null
	            // You can throw an exception, log a message, or take other appropriate actions
	            // For example, you might throw an IllegalArgumentException:
	            throw new IllegalArgumentException("New username cannot be empty or null");
	        }
	    }
	    private String generateConfidentialCode() {
	        // Logique de génération du code confidentiel (vous pouvez utiliser une bibliothèque ou une logique personnalisée)
	        // Exemple simple : génération d'une chaîne aléatoire de 6 chiffres
	        Random random = new Random();
	        int code = 100000 + random.nextInt(900000);
	        return String.valueOf(code);
	    }

	    public void updatePasswordById(String id, String password) {
	        if (password != null && !password.isEmpty()) {
	            citoyenrepository.findById(id).ifPresent(citoyen -> {
	                citoyen.setPassword(password);
	                citoyenrepository.save(citoyen);
	            });
	        } else {
	            // Handle the case where newPassword is empty or null
	            // You can throw an exception, log a message, or take other appropriate actions
	            // For example, you might throw an IllegalArgumentException:
	            throw new IllegalArgumentException("New password cannot be empty or null");
	        }
	    }


	
	

}