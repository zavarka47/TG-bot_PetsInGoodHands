package ru.skypro.tgbot_petsingoodhands.handler.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Pattern;
@Component
public class TransportationPetHandler implements TelegramHandler {

    private final Messages messages;
    private final AnimalService animalService;
    private final ShelterService shelterService;

    private Pattern pattern = Pattern.compile("0.0.3.2.1.\\d+");

    public TransportationPetHandler(Messages messages, AnimalService animalService, ShelterService shelterService) {
        this.messages = messages;
        this.animalService = animalService;
        this.shelterService = shelterService;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        Long animalId = shelterService.getShelterById(shelterId).getAnimal().getAnimalId();
        messages.sendSimpleMessage(chatId, animalService.getById(animalId).getTransportation());
    }
}
