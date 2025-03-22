package ru.kolosov.CRUD.service.weather;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kolosov.CRUD.dto.DadataDTO;
import ru.kolosov.CRUD.dto.WeatherDTO;
import ru.kolosov.CRUD.service.dadata.DadataService;

import java.net.URI;


@Service
public class WeatherServiceImp implements WeatherService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DadataService dadataService;
    @Value("${weather.key}")
    private String apiKey;
    @Value("${weather.api_url}")
    private String weatherApiUrl;

    @Override
    public WeatherDTO getWeather(String address) {
        DadataDTO dadataDTO = dadataService.getCoordinates(address);

        URI uri = UriComponentsBuilder.fromUriString(weatherApiUrl)
                .queryParam("lat", dadataDTO.getGeo_lat())
                .queryParam("lon", dadataDTO.getGeo_lon())
                .queryParam("lang", "ru_RU")
                .queryParam("limit", 1)
                .queryParam("hours", true)
                .queryParam("extra", false)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-Weather-Key", apiKey);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<WeatherDTO> response = restTemplate.exchange(uri, HttpMethod.GET, request, WeatherDTO.class);
        System.out.println("Ответ от API: " + response.getBody());
        return response.getBody();
    }
}
