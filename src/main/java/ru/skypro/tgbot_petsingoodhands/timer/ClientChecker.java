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
    @Scheduled(cron = "0 0 20 * * *")
    //@Scheduled(fixedDelay = 15, timeUnit = TimeUnit.SECONDS)
    public void task() {

        List<Client> clients = clientService.getClientListWithoutReports();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChatId(),
                    "Добрый день  " + client.getName() + "!" +
                            "Мы не получили от Вас отчёт за вчерашний день!" +
                            "Будте добры прислать его в ближайшее време, в противном случае" +
                            "мы будем вынуждены направить к Вам волонтёра!");
        }

    }

    //@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.DAYS)
    @Scheduled(cron = "0 0 21 * * *")
    public void task2daysDelay() {

        List<Client> clients = clientService.getClientListWithoutReportsMoreThan2Days();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChatId(),
                    "Добрый день  " + client.getName() +
                            "! Мы не получили от Вас отчёты два и более дней. " +
                            "В связи с этим к Вам будет направлен волонтёр");
            //можно послать сообщение репортёру
        }

    }
    @Scheduled(cron = "0 1 21 * * *")
    //@Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void taskTrial() {
        List<Client> clients = clientService.getClientListWithoutReports();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChatId(), client.getName());

        }
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void taskCheckAditional() {

        List<Client> clients = clientService.getClientByBeginAdditionalTrailPeriodNotNullAndNotificationAdditionalTrailPeriodIsFalse();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChatId(),
                    "Добрый день  " + client.getName() +
                            "! Вам назначен дополнительный испытательный срок c " + client.getBeginAdditionalTrailPeriod() +
                            " на срок " + client.getAdditionalTrailPeriod() + " дней!");
            client.setNotificationAdditionalTrailPeriod(true);
            clientService.saveClient(client);

        }
    }

    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void taskCheckTrialIsOver() {

        List<Client> clients = clientService.getClientsByTrailPeriodIsOverTrueAndNotificationTrailPeriodIsOverFalse();
        for (Client client: clients) {
            messages.sendSimpleMessage(client.getChatId(),
                    "Добрый день  " + client.getName() +
                            "! Ваш испытательный срок завершен !");
            client.setNotificationTrailPeriodIsOver(true);
            clientService.saveClient(client);
        }
    }

}
