package ru.kolosov.CRUD.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kolosov.CRUD.dto.WeatherDTO;
import ru.kolosov.CRUD.model.TelegramUser;
import ru.kolosov.CRUD.service.telegram.TelegramService;
import ru.kolosov.CRUD.service.weather.WeatherService;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramSteps {
    private final Map<Long, InputStep> userSteps = new HashMap<>();
    private final Map<Long, TelegramUser> userData = new HashMap<>();

    @Autowired
    TelegramService telegramService;

    @Autowired
    WeatherService weatherService;

    protected String userInputStep(Long chatId, String text) {
        InputStep step = userSteps.getOrDefault(chatId, InputStep.CITY);
        TelegramUser user = userData.getOrDefault(chatId, new TelegramUser(chatId));

        switch (step) {
            case CITY:
                user.setCity(text);
                userSteps.put(chatId, InputStep.TEMPERATURE);
                userData.put(chatId, user);
                return "Введите комфортную температуру (например, 15.5):";

            case TEMPERATURE:
                try {
                    user.setGoodTemperature(Double.parseDouble(text));
                    userSteps.put(chatId, InputStep.WIND_SPEED);
                    userData.put(chatId, user);
                    return "Введите комфортную скорость ветра (например, 5.0):";
                } catch (NumberFormatException e) {
                    return "Ошибка! Введите число для температуры.";
                }

            case WIND_SPEED:
                try {
                    user.setGoodWindSpeed(Double.parseDouble(text));
                    user.setChatId(chatId);
                    userSteps.put(chatId, InputStep.DONE);
                    userData.put(chatId, user);
                    return saveUserAndGetWeather(user);
                } catch (NumberFormatException e) {
                    return "Ошибка! Введите число для скорости ветра.";
                }

            case DONE:
                return "✅ Данные уже сохранены! Напишите /reset для нового ввода.";

            default:
                return "Ошибка! Попробуйте ещё раз.";
        }
    }
    private String saveUserAndGetWeather(TelegramUser user) {
        telegramService.save(user);
        String city = user.getCity();
        double goodTemperature = user.getGoodTemperature();
        double goodWindSpeed = user.getGoodWindSpeed();

        WeatherDTO weatherDTO = weatherService.getWeather(city);
        if (weatherDTO == null || weatherDTO.getFact() == null) {
            return "Не удалось получить данные о погоде для " + city;
        }

        StringBuilder textToSend = new StringBuilder("Подходящее время для прогулок в ")
                .append(city)
                .append(":\n");


        long counter = weatherDTO.getForecasts().get(0).getHours().stream()
        .filter(x -> x.getTemp() >= goodTemperature && x.getWind_speed() <= goodWindSpeed)
        .peek(x -> textToSend.append(x.getHour()).append(":00  ")
                .append(x.getTemp()).append("°C, ")
                .append("ветер ").append(x.getWind_speed()).append(" м/с\n"))
        .count();

        if (counter == 0) {
            return "Сегодня неподходящий день для прогулок.";
        } else {
            return "✅ Данные сохранены!\n" + textToSend.toString();
        }
    }


    protected void resetUserInput(Long chatId) {
        userSteps.put(chatId, InputStep.CITY);
        userData.put(chatId, new TelegramUser());
    }

    enum InputStep {
        CITY, TEMPERATURE, WIND_SPEED, DONE
    }
}

