package ru.kolosov.CRUD.service.Windy;


import ru.kolosov.CRUD.dto.WindyResponse;

public interface WindyService {

    WindyResponse getWindyWeather(double lat, double lon);
}
