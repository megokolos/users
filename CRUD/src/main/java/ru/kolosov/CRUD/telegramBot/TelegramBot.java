package ru.kolosov.CRUD.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;
    private final String botToken;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    TelegramService telegramService;

    @Autowired
    TelegramSteps telegramSteps;

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
                telegramSteps.resetUserInput(chatId);
                sendTextMessage(chatId, "Введите ваш город:");
                return;
            }

            if ("/reset".equals(text)) {
                telegramSteps.resetUserInput(chatId);
                sendTextMessage(chatId, "Ввод сброшен! Введите ваш город:");
                return;
            }
            String response = telegramSteps.userInputStep(chatId, text);
            sendTextMessage(chatId, response);

        }
    }

    private void sendTextMessage(Long chatId, String text) {
        try {
            execute(new SendMessage(chatId.toString(), text));
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить сообщение: " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 9 * * ?")
    private void autoSend() {
        int page = 0;
        int size = 100;
        Page<TelegramUser> telegramUserPage;
        do {
            telegramUserPage = telegramService.findAll(PageRequest.of(page, size));
            List<TelegramUser> allusers = telegramUserPage.getContent();

            for (TelegramUser telegramUser : allusers) {
                Long chatId = telegramUser.getChatId();
                WeatherDTO weatherDTO = weatherService.getWeather(telegramUser.getCity());

                StringBuilder textToSend = new StringBuilder("Подходящее время для прогулок в ")
                        .append(telegramUser.getCity())
                        .append(":\n");

                weatherDTO.getForecasts().get(0).getHours().stream()
                        .filter(x -> x.getTemp() >= telegramUser.getGoodTemperature() && x.getWind_speed() <= telegramUser.getGoodWindSpeed())
                        .forEach(x -> textToSend.append(x.getHour()).append(":00  ")
                                .append(x.getTemp()).append("°C, ")
                                .append("ветер ").append(x.getWind_speed()).append(" м/с\n"));

                sendTextMessage(chatId, textToSend.toString());
                page++;
            }
        } while (!telegramUserPage.isLast());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
