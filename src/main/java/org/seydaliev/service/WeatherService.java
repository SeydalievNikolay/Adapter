package org.seydaliev.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String GISMETEO_API_URL = "https://api.gismeteo.net/v2/search/cities/?lang=ru&query=";
    private static final String GISMETEO_TOKEN = "56b30cb255.3443075";

    public String getWeatherData(String latitude, String longitude) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Gismeteo-Token", GISMETEO_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GISMETEO_API_URL + latitude + "," + longitude,
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }
}
