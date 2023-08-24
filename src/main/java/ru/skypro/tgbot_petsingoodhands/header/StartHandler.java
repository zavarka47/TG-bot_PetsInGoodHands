package ru.skypro.tgbot_petsingoodhands.header;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Animal;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;

import java.util.List;
import java.util.Objects;

@Component
public final class StartHandler implements TelegramHandler {

    private final Messages messages;
    private final AnimalService animalService;

    public StartHandler(Messages messages, AnimalService animalService) {
        this.messages = messages;
        this.animalService = animalService;
    }


    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) && update.message().text().equals("/start");
    }

    @Override
    public void handleUpdate(Update update) {
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        List<Animal> animals = animalService.getAll();
        for (Animal a : animals) {
            InlineKeyboardButton button = new InlineKeyboardButton(a.getType()).callbackData("0.0.0.0.0."+a.getAnimalId().toString());
            keyBoard.addRow(button);
        }
        messages.sendMessageWithKeyboard(update.message().chat().id(), "Выберите животное, которое хотели бы приютить", keyBoard);

    }
}

