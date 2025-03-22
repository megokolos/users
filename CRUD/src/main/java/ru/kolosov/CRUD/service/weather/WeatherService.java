package ru.kolosov.CRUD.service.weather;

import ru.kolosov.CRUD.dto.WeatherDTO;

public interface WeatherService {
    WeatherDTO getWeather(String address);

}
