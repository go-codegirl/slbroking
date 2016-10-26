package com.telosws.broking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vaadin.spring.events.annotation.EnableVaadinEventBus;

/**
 * 
 * @author Harish Kalepu
 *
 */

@SpringBootApplication
@EnableAutoConfiguration
@EnableVaadinEventBus
@Configuration
@ComponentScan
@EnableScheduling
public class TeloswsBrokingApplication {
	
    public static void main(String[] args) {

    	final Logger logger = LoggerFactory.getLogger(TeloswsBrokingApplication.class);

        logger.debug("logback is now  {   }");


        SpringApplication.run(TeloswsBrokingApplication.class, args);
    }
}