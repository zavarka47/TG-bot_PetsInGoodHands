package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;

@Component
public class FirstMeetRulesHandlers implements TelegramHeader {

    private final Messages messages;

    public FirstMeetRulesHandlers(Messages messages) {
        this.messages = messages;
    }


    @Override
    public boolean appliesTo(Update update) {
        return false;
    }

    @Override
    public void handleUpdate(Update update) {

    }
}
