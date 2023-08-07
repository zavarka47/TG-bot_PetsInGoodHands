package ru.skypro.tgbot_petsingoodhands.timer;


import com.pengrad.telegrambot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ClientChecker {

    private final TelegramBot telegramBot;

    private final Messages messages;
    private final ClientService clientService;

    public ClientChecker(TelegramBot telegramBot, Messages messages, ClientService clientService) {
        this.telegramBot = telegramBot;
        this.messages = messages;
        this.clientService = clientService;
    }

    /**
     * cron = "s m h d m dow"
     */
    //@Scheduled(cron = "0 31 19 * * *")
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void task() {

        List<Client> clients = clientService.getClientListWithoutReports();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChat_id(),
                    "Добрый день  " + client.getName() +
                            "! Мы не получили от Вас отчёт за вчерашний день!" +
                            "Будте добры прислать его в ближайшее време, в противном случае" +
                            "мы будем вынуждены направить к Вам волонтёра!");
        }
        //messages.sendSimpleMessage(5398232539l, "тЕСТ");

    }

    //@Scheduled(cron = "0 31 19 * * *")
//    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
//    public void taskTrial() {
//        List<Client> clients = clientService.getClientListWithoutReports();
//        for (Client client: clients) {
//            messages.sendSimpleMessage(5398232539l, client.getName());
//
//        }
//    }


}
