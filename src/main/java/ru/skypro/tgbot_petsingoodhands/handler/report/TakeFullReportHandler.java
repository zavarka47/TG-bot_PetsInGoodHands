package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class TakeFullReportHandler implements TelegramHandler {
    private final Pattern pattern = Pattern.compile("0.0.2.3.1.\\d+");
    private final Messages messages;

    public TakeFullReportHandler(Messages messages) {
        this.messages = messages;
    }


    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        messages.sendSimpleMessage(update.callbackQuery().from().id(),
                "Пришлите заполненный отчет и фото питомца");

    }
}
