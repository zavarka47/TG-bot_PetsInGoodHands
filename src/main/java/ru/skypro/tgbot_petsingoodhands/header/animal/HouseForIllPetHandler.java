package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
@Component
public class HouseForIllPetHandler implements TelegramHeader {
    @Override
    public boolean appliesTo(Update update) {
        return false;
    }

    @Override
    public void handleUpdate(Update update) {

    }
}
