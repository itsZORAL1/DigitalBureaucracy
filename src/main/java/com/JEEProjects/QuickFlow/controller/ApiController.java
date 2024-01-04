package com.JEEProjects.QuickFlow.controller;


import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.service.Implementation.EmailServiceImpli;
import com.JEEProjects.QuickFlow.service.Implementation.FaceDataServiceImp;
import com.lowagie.text.DocumentException;
import com.JEEProjects.QuickFlow.models.CRACTERISTIQUE_FACIAL;
import com.JEEProjects.QuickFlow.models.FONCTIONNAIRE;
import com.JEEProjects.QuickFlow.repository.CitoyenRepository;
import com.JEEProjects.QuickFlow.repository.FonctionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private FonctionnaireRepository fonctionnaireRepository;
    @Autowired
    private FaceDataServiceImp faceDataServiceImp;
    @Autowired
    private EmailServiceImpli mail;
    @GetMapping("/generateHtmlToPdf")
    public ResponseEntity<byte[]> generateHtmlToPdf(
            @RequestParam String imageName1,
            @RequestParam String imageName2,@RequestParam String email) {

        // Construct the full paths to the images
    	String imagePath1 = "file:///D:/ADMINICLUCKFRONT/public/images/" + imageName1;
    	String imagePath2 = "file:///D:/ADMINICLUCKFRONT/public/images/" + imageName2;
    	
    


        // Create an XHTML string with the images positioned and sized
        String xhtmlContent = String.format(
                "<html><body><div style=\"position: relative;\">" +
                "<img src=\"%s\" style=\"position: absolute;  transform: translate(-50%%, -50%%); top: -00px; left: 30px;width: 700px; height: 900px;\"/>" +
                "<img src=\"%s\" style=\"position: absolute; top: 700px; left: 510px; width: 150px; height: 150px;\"/>" +
                "</div></body></html>",
                imagePath2, imagePath1);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, true); // 'true' indicates that the output is a PDF

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            // Save the PDF to a specific location
            String pdfFilePath = "D:/IMAGE_TEST/generated_file.pdf";
            try (FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
                fos.write(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (log, throw, etc.)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            mail.sendSimpleMessageWithAttachment( email,email, "pdf", pdfBytes);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=generated_file.pdf")
                    .body(pdfBytes);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/generate3HtmlToPdf")
    public ResponseEntity<byte[]> generateHtmlToPdf(
            @RequestParam String imageName1,
            @RequestParam String imageName2,@RequestParam String imageName3,@RequestParam String email) {

        // Construct the full paths to the images
    	String imagePath1 = "file:///D:/ADMINICLUCKFRONT/public/images/" + imageName1;
    	String imagePath2 = "file:///D:/ADMINICLUCKFRONT/public/images/" + imageName2;
    	String imagePath3 = "file:///D:/ADMINICLUCKFRONT/public/images/" + imageName3;
    	
    	System.out.println(imageName1+ " "+imageName2+" "+imageName3);


        // Create an XHTML string with the images positioned and sized
        String xhtmlContent = String.format(
                "<html><body><div style=\"position: relative;\">" +
                "<img src=\"%s\" style=\"position: absolute;  transform: translate(-50%%, -50%%); top: -00px; left: 30px;width: 700px; height: 900px;\"/>" +
                "<img src=\"%s\" style=\"position: absolute; top: 700px; left: 510px; width: 150px; height: 150px;\"/>" +
                "<img src=\"%s\" style=\"position: absolute; top: 0px; left: 0px; width: 90px; height: 90px;\"/>" +
                "</div></body></html>",
                imagePath2, imagePath1,imagePath3);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, true); // 'true' indicates that the output is a PDF

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            // Save the PDF to a specific location
            String pdfFilePath = "D:/IMAGE_TEST/generated_file.pdf";
            try (FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
                fos.write(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (log, throw, etc.)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            mail.sendSimpleMessageWithAttachment( email,email, "pdf", pdfBytes);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "inline; filename=generated_file.pdf")
                    .body(pdfBytes);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getCitoyenData")
    public CITOYEN getCitoyenData(@RequestParam String id) {
        Optional<CITOYEN> citoyenOptional = citoyenRepository.findById(id);
        return citoyenOptional.orElse(null);
    }

    @GetMapping("/getFonctionnaireData")
    public FONCTIONNAIRE getFonctionnaireData(@RequestParam String id) {
        Optional<FONCTIONNAIRE> fonctionnaireOptional = fonctionnaireRepository.findById(id);
        return fonctionnaireOptional.orElse(null);
    }
    @GetMapping("/getFaceData")
    public ResponseEntity<CRACTERISTIQUE_FACIAL> getFaceData(@RequestParam String id) {
        try {
            // Assuming you have a service method to get CRACTERISTIQUE_FACIAL by CITOYEN ID
            CRACTERISTIQUE_FACIAL faceData = faceDataServiceImp.getFaceDataByCitoyenId(id);

            if (faceData != null) {
                return ResponseEntity.ok(faceData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log the error)
            return ResponseEntity.badRequest().build();
        }
    }

}
