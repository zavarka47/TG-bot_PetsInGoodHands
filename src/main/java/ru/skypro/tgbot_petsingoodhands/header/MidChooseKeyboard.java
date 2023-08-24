package ru.skypro.tgbot_petsingoodhands.header;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class MidChooseKeyboard implements TelegramHandler {
    private final Messages messages;
    private final ShelterService shelterService;
    private final AnimalService animalService;
    private Pattern pattern = Pattern.compile("0.0.0.0.1.\\d+");

    public MidChooseKeyboard(Messages messages, ShelterService shelterService, AnimalService animalService) {
        this.messages = messages;
        this.shelterService = shelterService;
        this.animalService = animalService;

    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать информацию о приюте").callbackData("0.0.0.1.1." + shelterId);
        InlineKeyboardButton button2 = new InlineKeyboardButton("Как взять животное из приюта").callbackData("0.0.0.2.1." + shelterId);
        InlineKeyboardButton button3 = new InlineKeyboardButton("Прислать отчет о питомце").callbackData("0.0.0.3.1." + shelterId);
        InlineKeyboardButton button4 = new InlineKeyboardButton("Позвать волонтер").callbackData("1.0.0.0.0."+ shelterId);
        keyBoard.addRow(button1).addRow(button2).addRow(button3).addRow(button4);
        messages.sendMessageWithKeyboard(chatId, "Выберете пункт который Вас интересует", keyBoard);

    }
}
