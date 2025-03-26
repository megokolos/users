package ru.kolosov.CRUD.service.telegram;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramService {

    @Transactional
    void save(TelegramUser telegramUser);

    TelegramUser getUser(Long chatId);

    Page<TelegramUser> findAll(Pageable pageable);
}
