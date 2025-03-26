package ru.kolosov.CRUD.controller.RestControllers.Windy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolosov.CRUD.dto.WindyResponse;
import ru.kolosov.CRUD.service.Windy.WindyService;

@RestController
@RequestMapping("/weather")
public class WindyController {

    @Autowired
    private WindyService windyService;

    @GetMapping
    public WindyResponse getWeather(
            @RequestParam double lat,
            @RequestParam double lon) {
        return windyService.getWindyWeather(lat, lon);
    }
}
