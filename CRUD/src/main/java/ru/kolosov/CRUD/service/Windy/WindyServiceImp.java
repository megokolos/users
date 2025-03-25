package ru.kolosov.CRUD.service.Windy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kolosov.CRUD.Freign.FreignWeatherClient;
import ru.kolosov.CRUD.Freign.WindyResponse;
import ru.kolosov.CRUD.dto.WindyDTO;


@Service
public class WindyServiceImp implements WindyService {

    @Autowired
    private FreignWeatherClient windyClient;
    @Override
    public WindyResponse getWindyWeather(double lat, double lon) {
        WindyDTO windyDTO = new WindyDTO(lat, lon);
        return windyClient.getWindyWeather(windyDTO);
    }
}
