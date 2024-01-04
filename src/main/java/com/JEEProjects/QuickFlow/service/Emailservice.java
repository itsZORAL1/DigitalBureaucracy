package com.JEEProjects.QuickFlow.service;

public interface Emailservice {
    public void sendSimpleMessage(String to, String subject, String text);
    public void sendSimpleMessageWithAttachment(String to, String subject, String text, byte[] pdfAttachment);

}