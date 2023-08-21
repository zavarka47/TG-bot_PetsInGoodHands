package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.VolunteerService;

import java.util.Objects;
import java.util.regex.Pattern;
@Component
public class CallVolunteerHandler implements TelegramHandler {
    private final Messages messages;
    private final VolunteerService volunteerService;
    private final Pattern pattern = Pattern.compile("0.0.10.1.1.\\d+");

    public CallVolunteerHandler(Messages messages, VolunteerService volunteerService) {
        this.messages = messages;
        this.volunteerService = volunteerService;
    }


    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        Long chatIdClient = update.callbackQuery().from().id();
        Long chatIdVolunteer = volunteerService.getChatIdBySheltersId(shelterId);
        messages.sendSimpleMessage(chatIdVolunteer,"Необходима помощь " + chatIdClient);
        messages.sendSimpleMessage(chatIdClient,"Помощь в пути, ожидайте ");




    }
    }

