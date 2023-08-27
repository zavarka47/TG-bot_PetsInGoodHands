package ru.skypro.tgbot_petsingoodhands.handler.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Pattern;
@Component
public class GetImpotentDocumentHandler implements TelegramHandler {
    private final Messages messages;
    private final ShelterService shelterService;
    private Pattern pattern = Pattern.compile("0.0.2.2.1.\\d+");

    public GetImpotentDocumentHandler(Messages messages, ShelterService shelterService) {
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
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        messages.sendSimpleMessage(chatId, shelterService.getShelterById(shelterId).getDocumentsForAdoption());


    }
}
