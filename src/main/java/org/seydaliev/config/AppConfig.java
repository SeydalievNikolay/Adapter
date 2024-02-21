package org.seydaliev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AppConfig {
    @Value("${rest.template.connection-timeout}")
    private int connectionTimeout;

    @Value("${rest.template.read-timeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(
                new SimpleClientHttpRequestFactory() {
                    @Override
                    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                        super.prepareConnection(connection, httpMethod);
                        connection.setConnectTimeout(connectionTimeout);
                        connection.setReadTimeout(readTimeout);
                    }
                }
        );
        return restTemplate;
    }
}
