package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.message.service.ShelterService;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StartHeaders implements TelegramHeader {

    private final Messages messages;
    private final ShelterService shelterService;
    private final Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d+)(!!)(1)");

    public StartHeaders(Messages messages, ShelterService shelterService) {
        this.messages = messages;
        this.shelterService = shelterService;
    }


    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        Long shelterId = Long.parseLong(matcher.group(5));
        messages.sendSimpleMessage(chatId, shelterService.getShelterById(shelterId).getAbout());

        List<Shelter> shelters = shelterService.getSheltersByAnimalTypeId(Long.parseLong(matcher.group(3)));
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        for (Shelter s : shelters) {
            InlineKeyboardButton button = new InlineKeyboardButton(s.getShelterId().toString()).callbackData("(1)(!!)(" + s.getAnimal().getAnimalId() + ")(!!)(" + s.getShelterId() + "(!!)(1)");
            keyBoard.addRow(button);
        }

    }
    }

