package com.kari.travelfront.operations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AdminConfig {


    @Value("${admin.mail}")
    private String adminMail;


}
