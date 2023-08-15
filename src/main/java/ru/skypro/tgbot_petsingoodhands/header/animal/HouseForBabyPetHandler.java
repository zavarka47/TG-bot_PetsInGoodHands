package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HouseForBabyPetHandler implements TelegramHeader {

    private Messages messages;
    private AnimalService animalService;
    private Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d)(!!)(2)");

    public HouseForBabyPetHandler(Messages messages, AnimalService animalService) {
        this.messages = messages;
        this.animalService = animalService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        Long animalId = Long.parseLong(matcher.group(5));
        messages.sendSimpleMessage(chatId, animalService.getById(animalId).getHouseForBabyPet());
    }
}
