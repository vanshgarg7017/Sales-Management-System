package com.sales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ForecastService {

    private final RestTemplate restTemplate;

    @Autowired
    public ForecastService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getForecast() {
        String forecastApiUrl = "http://localhost:5000/forecast"; // Flask API URL
        return restTemplate.getForObject(forecastApiUrl, String.class);
    }
}
