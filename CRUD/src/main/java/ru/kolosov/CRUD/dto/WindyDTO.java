package ru.kolosov.CRUD.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WindyDTO {
    @NonNull
    private Double lat;
    @NonNull
    private Double lon;
    private String model = "gfs";
    private List<String> parameters = List.of("temp", "wind");
    private List<String> levels = List.of("surface");
    private String key = "8dzRrI8DXqrnyXnqW1GMBtGndT2gVpss";

}
