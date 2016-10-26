package com.telosws.broking.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.telosws.broking.util.GenericResponse;
import com.telosws.broking.util.GetProperties;

/**
 * 
 * @author Harish Kalepu
 * 
 */

// This class for deleting logged in user after certain time if they are not
// active
@Component
public class OnlineUsersJob {

	final Logger logger = LoggerFactory.getLogger(OnlineUsersJob.class);

	@Autowired
	private GetProperties getProperties;

	private RestTemplate restTemplate;

	@Scheduled(cron = "0 0/15 * * * ?")
	public void removeUsers() {
		try {

			logger.debug("********* removeUsers() Method Starting *********");

			restTemplate = new RestTemplate();

			GenericResponse result = restTemplate.getForObject(getProperties.getBaseUrl() + "/removeOnlineUsers",
					GenericResponse.class);

			logger.debug("********* removeUsers() Method Ending *********");

		} catch (Exception ex) {
			logger.error("Exception While Executing OnlineUsersJob", ex);
		}
	}
}
