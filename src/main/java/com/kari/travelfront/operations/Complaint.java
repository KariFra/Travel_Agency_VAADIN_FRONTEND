package com.kari.travelfront.operations;

import com.kari.travelfront.domain.Mail;
import com.kari.travelfront.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Complaint {

    private static final String SUBJECT ="Complaint";

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdminConfig adminConfig;


    public void sendEmail(String message){
            emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, message));
    }
}
