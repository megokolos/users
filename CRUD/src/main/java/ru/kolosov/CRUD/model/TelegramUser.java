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
    @Column(name = "chat_id")
    @NonNull
    private Long chatId;

    @NonNull
    private String city;

    @NonNull
    @Column(name = "good_temperature")
    private Double goodTemperature;

    @NonNull
    @Column(name = "good_wind_speed")
    private Double goodWindSpeed;

    @Version
    private Long version;
}
