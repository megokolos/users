package ru.kolosov.CRUD.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    private Fact fact;
    private List<Forecast> forecasts;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fact {
        private double temp;
        private double feels_like;
        private String condition;
        private double wind_speed;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        private String date;
        private List<Hour> hours;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hour {
        private String hour;
        private double temp;
        private double wind_speed;
    }
}
