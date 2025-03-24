package ru.kolosov.CRUD.service.telegram;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramService {

    @Transactional
    void save(TelegramUser telegramUser);

    TelegramUser getUser(Long chatId);

    List<TelegramUser> findAll();
}
