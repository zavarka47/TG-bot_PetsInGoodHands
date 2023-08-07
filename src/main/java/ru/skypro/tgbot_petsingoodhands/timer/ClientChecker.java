package ru.skypro.tgbot_petsingoodhands.timer;


import com.pengrad.telegrambot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

@Component
public class ClientChecker {

    private final TelegramBot telegramBot;

    private final Messages messages;
    private final ClientS

    public ClientChecker(TelegramBot telegramBot, Messages messages) {
        this.telegramBot = telegramBot;

        this.messages = messages;
    }

    /**
     * cron = "s m h d m dow"
     */
    @Scheduled(cron = "0 4 9 * * *")
    public void task() {


        messages.sendSimpleMessage(5398232539l, "тЕСТ");

    }

}
