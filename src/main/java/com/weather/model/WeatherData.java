package com.weather.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WeatherData implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("name")
    private String city;

    @JsonProperty("main")
    private Map<String, Double> main;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("wind")
    private Map<String, Double> wind;

    public double getTemperature() {
        return main != null ? main.get("temp") : 0.0;
    }

    public double getHumidity() {
        return main != null ? main.get("humidity") : 0.0;
    }

    public String getDescription() {
        return weather != null && !weather.isEmpty() ? 
            weather.get(0).getDescription() : "";
    }

    public double getWindSpeed() {
        return wind != null ? wind.get("speed") : 0.0;
    }

    @Data
    public static class Weather implements Serializable {
        private static final long serialVersionUID = 1L;
        private String description;
    }
} 