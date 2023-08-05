package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;

import java.util.Objects;

public class GetImpotentDocumentHeaders implements TelegramHeader {
    private final SendMessage sendMessage;

    public GetImpotentDocumentHeaders(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ? update.message().text().equals("/start") : false;
    }

    @Override
    public void handleUpdate(Update update) {

    }
}
