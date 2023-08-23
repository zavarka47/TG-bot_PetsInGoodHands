package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
import java.util.regex.Pattern;
@Component
public class TakeContactHandler implements TelegramHandler {
    private final Pattern pattern = Pattern.compile("0.0.6.1.1.\\d+");
    private final Messages messages;

    public TakeContactHandler(Messages messages) {
        this.messages = messages;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        messages.sendSimpleMessage(update.callbackQuery().from().id(),
                "Введите свои данные в формате:\n" +
                        shelterId + "_Иванов Иван Иванович_88005553535\n"+
                "ВНИМАНИЕ! Цифра указанная перед именем должна быть указанна как в примере");


    }
}
