package org.lecoder.easyflow.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
@ConfigurationProperties(prefix = "wecom-config")
@Data
public class WeComConfig {

    private String baseUrl;

    private Map<String, String> endpoints;

    private Map<String,String> credentials;

    private Map<String,String> comCredentials;




}
