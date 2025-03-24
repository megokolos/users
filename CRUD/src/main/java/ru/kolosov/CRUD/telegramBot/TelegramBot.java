package ru.kolosov.CRUD.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kolosov.CRUD.dto.WeatherDTO;
import ru.kolosov.CRUD.model.TelegramUser;
import ru.kolosov.CRUD.service.telegram.TelegramService;
import ru.kolosov.CRUD.service.weather.WeatherService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    TelegramService telegramService;

    public TelegramBot(@Value("${telegram_bot.name}") String botName,
                       @Value("${telegram_bot.token}") String botToken) {
        super(botToken);
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if ("/start".equals(text)) {
                sendTextMessage(chatId, "Введите свой город, комфортную температуру и скорость ветра.\nПример: Нижний Новгород 15 5");
                return;
            }

            Pattern pattern = Pattern.compile("^(.+?)\\s(-?\\d+(?:\\.\\d+)?)\\s(-?\\d+(?:\\.\\d+)?)$");
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                sendTextMessage(chatId, "Ошибка! Введите данные в формате: Город Температура Скорость_ветра\nПример: Нижний Новгород 15 5");
                return;
            }

            String city = matcher.group(1);
            double goodTemperature = Double.parseDouble(matcher.group(2));
            double goodWindSpeed = Double.parseDouble(matcher.group(3));

            TelegramUser telegramUser = new TelegramUser(chatId, city, goodTemperature, goodWindSpeed);
            telegramService.save(telegramUser);

            sendTextMessage(chatId, "Предпочтения сохранены, вам будут присылать проноз погоды каждый день в 12 часов");


            WeatherDTO weatherDTO = weatherService.getWeather(city);
            if (weatherDTO == null || weatherDTO.getFact() == null) {
                sendTextMessage(chatId, "Не удалось получить данные о погоде для " + city);
                return;
            }

            if (weatherDTO.getFact().getTemp() < goodTemperature || weatherDTO.getFact().getWind_speed() > goodWindSpeed) {
                sendTextMessage(chatId, "Сегодня не подходящий день для прогулок в " + city);
                return;
            }

            StringBuilder textToSend = new StringBuilder("Подходящее время для прогулок в ")
                    .append(city)
                    .append(":\n");

            for (WeatherDTO.Hour hour : weatherDTO.getForecasts().get(0).getHours()) {
                if (hour.getTemp() >= goodTemperature && hour.getWind_speed() <= goodWindSpeed) {
                    textToSend.append(hour.getHour()).append(":00 - ")
                            .append(hour.getTemp()).append("°C, ")
                            .append("ветер ").append(hour.getWind_speed()).append(" м/с\n");
                }
            }
            sendTextMessage(chatId, textToSend.toString());
        }
    }

    private void sendTextMessage(Long chatId, String text) {
        try {
            execute(new SendMessage(chatId.toString(), text));
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить сообщение: " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    private void autoSend() {
        List<TelegramUser> allusers = telegramService.findAll();

        for (TelegramUser telegramUser : allusers) {
            Long chatId = telegramUser.getChatId();
            WeatherDTO weatherDTO = weatherService.getWeather(telegramUser.getCity());

            StringBuilder textToSend = new StringBuilder("Подходящее время для прогулок в ")
                    .append(telegramUser.getCity())
                    .append(":\n");
            for (WeatherDTO.Hour hour : weatherDTO.getForecasts().get(0).getHours()) {
                if (hour.getTemp() >= telegramUser.getGoodTemperature() && hour.getWind_speed() <= telegramUser.getGoodWindSpeed()) {
                    textToSend.append(hour.getHour()).append(":00 - ")
                            .append(hour.getTemp()).append("°C, ")
                            .append("ветер ").append(hour.getWind_speed()).append(" м/с\n");
                }
            }
            sendTextMessage(chatId, textToSend.toString());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
