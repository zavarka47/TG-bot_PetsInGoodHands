package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetContactShelterHeaders implements TelegramHeader {
    private final SendMessage sendMessage;
    private final Messages messages;
    private final ShelterService shelterService;
    private final Pattern pattern = Pattern.compile("(1)(!!)(shelter_id)(!!)(2)");

    public GetContactShelterHeaders(SendMessage sendMessage, Messages messages, ShelterService shelterService) {
        this.sendMessage = sendMessage;
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
        messages.sendSimpleMessage(chatId, shelterService.getShelterById(shelterId).getContacts());

        }

    }

