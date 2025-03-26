package ru.kolosov.CRUD.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolosov.CRUD.model.TelegramUser;

import java.util.List;

@Repository
public interface TelegramRepository extends JpaRepository<TelegramUser, Long> {
    Page<TelegramUser> findAll(Pageable pageable);
}
