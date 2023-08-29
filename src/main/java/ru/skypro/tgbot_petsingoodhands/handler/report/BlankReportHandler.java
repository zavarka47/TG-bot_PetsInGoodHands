package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class BlankReportHandler implements TelegramHandler {
    private final Messages messages;
    private final TelegramBot telegramBot;
    Pattern pattern = Pattern.compile("0.0.1.3.1.\\d+");
    Logger logger = LoggerFactory.getLogger(BlankReportHandler.class);
    public BlankReportHandler(Messages messages, TelegramBot telegramBot) {
        this.messages = messages;
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        try {
            byte[] blankReport = Files.readAllBytes(
                    Paths.get(BlankReportHandler.class.getClassLoader().getResource("blankReport.txt").toURI()));
            SendDocument document = new SendDocument(chatId, blankReport);
            document.fileName("Форма отчета");
            SendResponse execute = telegramBot.execute(document);
            if (!execute.isOk()){
                logger.error("Error during sending message: {}", execute.description());
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}

