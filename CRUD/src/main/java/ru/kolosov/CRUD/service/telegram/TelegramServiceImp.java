package ru.kolosov.CRUD.service.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.TelegramUser;
import ru.kolosov.CRUD.repository.TelegramRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TelegramServiceImp implements TelegramService{

    @Autowired
    TelegramRepository telegramRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(TelegramUser telegramUser) {
    telegramRepository.save(telegramUser);
    }

    @Override
    public TelegramUser getUser(Long chatId) {
        return telegramRepository.findById(chatId).orElse(null);
    }

    @Override
    public List<TelegramUser> findAll() {
        return telegramRepository.findAll();
    }

}
