package com.weather.service;

import com.weather.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${openweathermap.api.key}")
    private String apiKey;
    
    @Value("${openweathermap.api.url}")
    private String apiUrl;
    
    @Cacheable(value = "weatherData", key = "#city", unless = "#result == null")
    public WeatherData getCurrentWeather(String city) {
        try {
            String url = String.format("%s/weather?q=%s&appid=%s&units=metric", 
                apiUrl, city, apiKey);
            logger.info("Fetching weather data for city: {}", city);
            WeatherData response = restTemplate.getForObject(url, WeatherData.class);
            if (response == null) {
                throw new RuntimeException("No weather data received for city: " + city);
            }
            return response;
        } catch (RestClientException e) {
            logger.error("Error fetching weather data for city: {}", city, e);
            throw new RuntimeException("Failed to fetch weather data for city: " + city, e);
        }
    }
} 