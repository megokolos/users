package ru.kolosov.CRUD.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kolosov.CRUD.dto.WeatherDTO;
import ru.kolosov.CRUD.service.weather.WeatherService;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;

    @Autowired
    private WeatherService weatherService;

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
                SendMessage sendMessage = new SendMessage(chatId.toString(), "Введите свой город, комфортную температуру и скорость ветра");
                try {
                    execute(sendMessage);
                    return;
                } catch (TelegramApiException e) {
                    System.out.println("Не удалось отправить сообщение");
                }
            }
            String[] cityAndTemperature = text.split(" ");
            String city = cityAndTemperature[0];
            Double goodTemperature = Double.parseDouble(cityAndTemperature[1]);
            Double goodWindSpeed = Double.parseDouble(cityAndTemperature[2]);
            WeatherDTO weatherDTO = weatherService.getWeather(city);
            if (weatherDTO.getFact().getTemp() < goodTemperature || weatherDTO.getFact().getWind_speed() > goodWindSpeed) {
                SendMessage sendMessage = new SendMessage(chatId.toString(), "Сегодня не подходящий день для прогулок");
                try {
                    execute(sendMessage);
                    return;
                } catch (TelegramApiException e) {
                    System.out.println("Не удалось отправить сообщение");
                }
            }
            StringBuilder textToSend = new StringBuilder("Подходящее время, температура и скорость ветра:").append("\n");
            for (WeatherDTO.Hour hour : weatherDTO.getForecasts().get(0).getHours()) {
                if (hour.getTemp() >= goodTemperature && hour.getWind_speed() <= goodWindSpeed) {
                    textToSend.append(hour.getHour()).append(" hours")
                            .append(" - ")
                            .append(hour.getTemp()).append(" degree, ")
                            .append("wind speed ").append(hour.getWind_speed()).append("m/s")
                            .append("\n");
                }
            }
            SendMessage sendMessage = new SendMessage(chatId.toString(), textToSend.toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                System.out.println("Не удалось отправить сообщение");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
