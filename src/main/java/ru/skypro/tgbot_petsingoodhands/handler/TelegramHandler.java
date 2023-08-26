package ru.skypro.tgbot_petsingoodhands.handler;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHandler {
    boolean appliesTo (Update update);
    void handleUpdate (Update update);
}
