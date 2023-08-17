package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class FirstMeetRulesHandlers implements TelegramHandler {

    private final Messages messages;
    private final AnimalService animalService;
    private Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d)(!!)(2)");

    public FirstMeetRulesHandlers(Messages messages, AnimalService animalService) {
        this.messages = messages;
        this.animalService = animalService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    /* Добавить поле первичные правила знакомста с животными или это FirstImpressionInShelter?
     */
    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        Long animalId = Long.parseLong(matcher.group(5));
        messages.sendSimpleMessage(chatId, animalService.getById(animalId).getFirstImpressionInShelter());
    }
}
