package ru.kolosov.CRUD.service.dadata;

import ru.kolosov.CRUD.dto.DadataDTO;

public interface DadataService {
    DadataDTO getCoordinates(String address);
}
