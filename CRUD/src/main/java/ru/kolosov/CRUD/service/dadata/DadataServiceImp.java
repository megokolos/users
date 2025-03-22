package ru.kolosov.CRUD.service.dadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolosov.CRUD.dto.DadataDTO;

@Service
public class DadataServiceImp implements DadataService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${dadata.api_key}")
    private String apiKey;
    @Value("${dadata.secret}")
    private String secret;
    @Value("${dadata.url}")
    private String url;

    @Override
    public DadataDTO getCoordinates(String address) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + apiKey);
        headers.set("X-Secret", secret);

        String requestBody = "[\"" + address + "\"]";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<DadataDTO[]> response = restTemplate.exchange(url, HttpMethod.POST, request, DadataDTO[].class);

        return response.getBody() != null && response.getBody().length > 0 ? response.getBody()[0] : null;
    }
}
