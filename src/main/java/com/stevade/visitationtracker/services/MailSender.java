package com.stevade.visitationtracker.services;

import javax.mail.MessagingException;

public interface MailSender {
    void sendEmail(String email, String subject, String text) throws MessagingException;
}
