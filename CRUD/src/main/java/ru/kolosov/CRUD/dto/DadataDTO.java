package ru.kolosov.CRUD.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DadataDTO {

    private String result;
    private double geo_lat;
    private double geo_lon;
}
