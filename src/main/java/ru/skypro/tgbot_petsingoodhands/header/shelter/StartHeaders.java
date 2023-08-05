package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;


public final class StartHeaders implements TelegramHeader {
    private final SendMessage sendMessage;

    public StartHeaders(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }


    @Override
    public boolean appliesTo(Update update) {
        return false;
    }

    @Override
    public void handleUpdate(Update update) {

    }
}
