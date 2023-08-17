package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class AboutShelterHandler implements TelegramHeader {
    private final Messages messages;
    private final ShelterService shelterService;
    private final Pattern pattern = Pattern.compile("(1)(!!)(shelter_id)(!!)(1)");
    /* Группа 1 - вход в меню "Узнать информацию о приюте"
    Группа 3 - соответствует ID Приюта
    Группа 5 - соответствует команде которую мы хотим получить
    */
    public AboutShelterHandler(Messages messages, ShelterService shelterService) {
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
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        Long shelterId = Long.parseLong(matcher.group(5));
        messages.sendSimpleMessage(chatId, shelterService.getShelterById(shelterId).getAbout());/// Будет меняться метод get в зависимости от цифры указанной в 5 группе

        }

    }

