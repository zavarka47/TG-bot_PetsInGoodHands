package ru.skypro.tgbot_petsingoodhands.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Messages {
    private final TelegramBot telegramBot;
    private Logger logger = LoggerFactory.getLogger(Messages.class);

    public Messages(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    public void sendSimpleMessage (Long chatId, String text){
        SendResponse sendResponse = telegramBot.execute(new SendMessage(chatId, text));
        if (!sendResponse.isOk()){
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
    public void sendMessageWithKeyboard (Long chatId, String text, Keyboard keyboard){
        SendResponse sendResponse = telegramBot.execute(
                new SendMessage(chatId, text).replyMarkup(keyboard));
        if (!sendResponse.isOk()){
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public void sendMessageWithMarkdown (Long chatId, String text){
        SendResponse sendResponse = telegramBot.execute(
                new SendMessage(chatId, text).parseMode(ParseMode.MarkdownV2));
        if (!sendResponse.isOk()){
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}
