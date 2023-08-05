package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;

public class ChooseShelterHeaders implements TelegramHeader {
    private final SendMessage sendMessage;
    public ChooseShelterHeaders (SendMessage sendMessages) {
        this.sendMessage = sendMessages;
    }
    @Override
    public boolean appliesTo(Update update) {
        return false;
    }

    @Override
    public void handleUpdate(Update update) {

    }
}
