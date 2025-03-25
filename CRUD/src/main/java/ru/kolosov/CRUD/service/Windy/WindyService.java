package ru.kolosov.CRUD.service.Windy;


import ru.kolosov.CRUD.Freign.WindyResponse;

public interface WindyService {

    WindyResponse getWindyWeather(double lat, double lon);
}
