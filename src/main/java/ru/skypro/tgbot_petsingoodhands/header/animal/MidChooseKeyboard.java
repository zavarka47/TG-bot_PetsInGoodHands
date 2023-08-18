package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MidChooseKeyboard implements TelegramHandler {

    private final Messages messages;
    private final ShelterService shelterService;
    private final AnimalService animalService;
    private Pattern pattern = Pattern.compile("(1)(!!)(//d+)");

    public MidChooseKeyboard(Messages messages, ShelterService shelterService, AnimalService animalService) {
        this.messages = messages;
        this.shelterService = shelterService;
        this.animalService = animalService;

    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        long shelterId = Long.parseLong(matcher.group(3));
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать информацию о приюте").callbackData("(2)(!!)(" + shelterId + ")(!!)(1)");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Как взять животное из приюта").callbackData("(2)(!!)(" + shelterId + ")(!!)(1)");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Прислать отчет о питомце").callbackData("(2)(!!)(" + shelterId + ")(!!)(1)");
        InlineKeyboardButton button4 = new InlineKeyboardButton("Позвать волонтер").callbackData("(2)(!!)(" + shelterId + ")(!!)(1)");
        keyBoard.addRow(button1, button2, button3, button4);
        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберете пункт который Вас интересует", keyBoard);

    }
}
