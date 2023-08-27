package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class ChooseFunctionByReportHandler implements TelegramHandler {
    private final Messages messages;

    public ChooseFunctionByReportHandler(Messages messages) {
        this.messages = messages;
    }

    Pattern pattern = Pattern.compile("0.0.0.3.1.\\d+");

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Получить форму отчета").callbackData("0.0.1.3.1." + shelterId);
        InlineKeyboardButton button2 = new InlineKeyboardButton("Прислать заполненный отчет").callbackData("0.0.2.3.1." + shelterId);
        InlineKeyboardButton button3 = new InlineKeyboardButton("Позвать волонтера").callbackData("1.0.0.0.0."+ shelterId);
        keyBoard.addRow(button1).addRow(button2).addRow(button3);
        messages.sendMessageWithKeyboard(chatId, "Выберете пункт который Вас интересует", keyBoard);
    }
}
