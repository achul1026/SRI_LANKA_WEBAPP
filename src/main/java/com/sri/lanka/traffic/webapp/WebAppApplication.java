package com.sri.lanka.traffic.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application-prod.yml",ignoreResourceNotFound = true)
public class WebAppApplication {


    public static void main(String[] args) {
        //SpringApplication.run(WebAppApplication.class, args);
        SpringApplication app = new SpringApplication(WebAppApplication.class);
        // Set default to 'prod' if no other profile is set
        /*String[] activeProfiles = System.getProperty("spring.profiles.active", "prod").split(",");
        app.setAdditionalProfiles(activeProfiles);*/
        app.run(args);
        
    } 
}
