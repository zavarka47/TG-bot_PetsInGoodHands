package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;

import java.util.Objects;

public class ChooseShelterHeaders implements TelegramHeader {
    private final SendMessage sendMessage;
    public ChooseShelterHeaders (SendMessage sendMessages) {
        this.sendMessage = sendMessages;
    }
    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ? update.message().text().equals("/start") : false;
    }

    @Override
    public void handleUpdate(Update update) {

        InlineKeyboardButton button1 = new InlineKeyboardButton("Приют для кошек").callbackData("1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Приют для собак").callbackData("2");
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup().addRow(button1, button2);
        Long chatId = update.message().chat().id();

    }
}
