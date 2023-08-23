package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SaveContactHandler implements TelegramHandler {
    private final Pattern pattern = Pattern.compile("(\\d+)_([А-я\\s)]+)_(\\d+)");
    private final ShelterService shelterService;
    private final ClientService clientService;
    private final Messages messages;

    public SaveContactHandler(ShelterService shelterService, ClientService clientService, Messages messages) {
        this.shelterService = shelterService;
        this.clientService = clientService;
        this.messages = messages;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ? pattern.matcher(update.message().text()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Matcher matcher = pattern.matcher(update.message().text());
        Long sheltersId = Long.parseLong(matcher.group(1));
        String clientName = matcher.group(2);
        String clientPhone = matcher.group(3);
        Long clientChatId = update.message().chat().id();

        Client client = new Client();
        client.setShelter(shelterService.getShelterById(sheltersId));
        client.setName(clientName);
        client.setPhone(clientPhone);
        client.setChat_id(clientChatId);
        clientService.saveClient(client);
        messages.sendSimpleMessage(clientChatId, "Ваш контакт сохранен");
    }
}
