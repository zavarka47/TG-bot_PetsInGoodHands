package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
@Component
public class ChooseAnimalHandlers implements TelegramHeader {

    private final Messages messages;

    public ChooseAnimalHandlers(Messages messages) {
        this.messages = messages;
    }
    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) && update.message().text().equals("/start");
    }

    @Override
    public void handleUpdate(Update update) {

        InlineKeyboardButton button1 = new InlineKeyboardButton("Собаки").callbackData("1");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Кошки").callbackData("2");

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup().addRow(button1, button2);

        Long chatId = update.message().chat().id();

        messages.sendMessageWithKeyboard(chatId, """
                            Привет \\!👋
                            Какое животное хотел хотите забрать\\?
                            """ , keyboard);

    }
}
