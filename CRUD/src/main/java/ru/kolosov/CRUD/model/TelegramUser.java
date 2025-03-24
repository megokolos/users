package ru.kolosov.CRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telegram")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String city;

    @NonNull
    @Column(name = "good_temperature")
    private Double goodTemperature;

    @NonNull
    @Column(name = "good_wind_speed")
    private Double goodWindSpeed;
}
