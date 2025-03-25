package ru.kolosov.CRUD.Freign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kolosov.CRUD.dto.WindyDTO;

@FeignClient(name = "freignWeatherClient", url = "https://api.windy.com/api/point-forecast/v2")
public interface FreignWeatherClient {

    @PostMapping()
    WindyResponse getWindyWeather(@RequestBody WindyDTO windyDTO);
}
