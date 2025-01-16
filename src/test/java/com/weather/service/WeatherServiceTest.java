package com.weather.service;

import com.weather.model.WeatherData;
import com.weather.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Import(TestConfig.class)
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetCurrentWeather() {
        WeatherData mockData = new WeatherData();
        mockData.setMain(Map.of("temp", 20.0, "humidity", 65.0));
        mockData.setWind(Map.of("speed", 5.0));
        mockData.setWeather(List.of(new WeatherData.Weather() {{
            setDescription("Clear sky");
        }}));
        mockData.setCity("London");
        
        when(restTemplate.getForObject(anyString(), eq(WeatherData.class)))
            .thenReturn(mockData);
            
        WeatherData result = weatherService.getCurrentWeather("London");
        
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertEquals(20.0, result.getTemperature());
        assertEquals(65.0, result.getHumidity());
        assertEquals(5.0, result.getWindSpeed());
        assertEquals("Clear sky", result.getDescription());
    }
} 