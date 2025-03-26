package ru.kolosov.CRUD.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WindyResponse {
    private List<Long> ts;
    private Map<String, String> units;
    private Map<String, List<Double>> data;
}
