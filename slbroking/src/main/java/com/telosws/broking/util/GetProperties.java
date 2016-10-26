package com.telosws.broking.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by karthikmarupeddi on 2/27/16.
 */
@Configuration
public class GetProperties {

    @Value("${app.BaseUrl}")
    private String baseURL;

    public String getBaseUrl() throws Exception {
        return baseURL;
    }

    @Value("${app.claimsUrl}")
    private String claimsUrl;

    public String getClaimsUrl() throws Exception {
        return claimsUrl;
    }

    @Value("${app.ecardUrl}")
    private String ecardUrl;

    public String getEcardUrl() throws Exception {
        return ecardUrl;
    }

    @Value("${app.ecardForDependentsUrl}")
    private String ecardForDependentsUrl;

    public String getEcardForDependentsUrl() throws Exception {
        return ecardForDependentsUrl;
    }
}
