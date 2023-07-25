package ru.skypro.tgbot_petsingoodhands.header;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHeader {
    boolean appliesTo (Update update);
    void handleUpdate (Update update);
}
