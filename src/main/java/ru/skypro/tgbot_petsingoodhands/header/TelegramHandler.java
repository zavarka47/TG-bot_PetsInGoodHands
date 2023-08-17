package ru.skypro.tgbot_petsingoodhands.header;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHandler {
    boolean appliesTo (Update update);
    void handleUpdate (Update update);
}
