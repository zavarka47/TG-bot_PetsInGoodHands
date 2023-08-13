package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.message.service.ShelterService;
import ru.skypro.tgbot_petsingoodhands.repository.ShelterRepository;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AboutShelterHeaders implements TelegramHeader {
    private final Messages messages;
    private final ShelterService shelterService;
    private final Pattern pattern = Pattern.compile("(1)(!!)(shelter_id)(!!)(1)");
    /* Группа 1 - вход в меню "Узнать информацию о приюте"
    Группа 3 - соответствует ID Приюта
    Группа 5 - соответствует команде которую мы хотим получить
    */
    public AboutShelterHeaders(Messages messages, ShelterService shelterService) {
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
        messages.sendSimpleMessage(chatId, shelterService.getShelterById(shelterId).getAbout());/// Будет меняться метод get в зависимости от цифры указанной в 5 группе

        }

    }

