package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ChooseShelterHandler implements TelegramHandler {
    private final Messages messages;
    private final ShelterService shelterService;
    private final Pattern pattern = Pattern.compile("0.0.0.0.0.\\d+");

    public ChooseShelterHandler(Messages messages, ShelterService shelterService) {
        this.messages = messages;
        this.shelterService = shelterService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Long animalId = Long.parseLong(update.callbackQuery().data().substring(10));



        List<Shelter> shelters = shelterService.getSheltersByAnimalTypeId(animalId);
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        for (Shelter s : shelters) {
            InlineKeyboardButton button = new InlineKeyboardButton(s.getShelterName()).callbackData("0.0.0.0.1." + s.getShelterId());
            keyBoard.addRow(button);
        }
messages.sendMessageWithKeyboard(chatId,"Выберете приют",keyBoard);
    }
}