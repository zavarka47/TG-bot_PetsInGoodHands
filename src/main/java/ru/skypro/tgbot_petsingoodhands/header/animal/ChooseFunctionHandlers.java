package ru.skypro.tgbot_petsingoodhands.header.animal;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChooseFunctionHandlers implements TelegramHandler {

    private final Messages messages;
    private final Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d+)(!!)(2)");

    public ChooseFunctionHandlers(Messages messages) {
        this.messages = messages;

    }
    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) && pattern.matcher(update.callbackQuery().data()).find();
    }

    @Override
    public void handleUpdate(Update update) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        long animalId = Long.parseLong(matcher.group(5));
        InlineKeyboardButton button = new InlineKeyboardButton("Узнать первичные правила знакомства с животным").callbackData(("(1)(!!)(" + animalId + ")(!!)(1)"));
        InlineKeyboardButton button1 = new InlineKeyboardButton("Список документов необходимых для того, чтобы приютить животное").callbackData(("(1)(!!)(" + animalId + ")(!!)(2)"));
        InlineKeyboardButton button2 = new InlineKeyboardButton("Рекомендации по транспортировке животного").callbackData(("(1)(!!)(" + animalId + ")(!!)(3)"));
        InlineKeyboardButton button3 = new InlineKeyboardButton("Список рекомендаций по обустройству дома для щенка,котенка").callbackData(("(1)(!!)(" + animalId + ")(!!)(4)"));
        InlineKeyboardButton button4 = new InlineKeyboardButton("Список рекомендаций по обустройству дома для взрослого животного").callbackData(("(1)(!!)(" + animalId + ")(!!)(5)"));
        InlineKeyboardButton button5 = new InlineKeyboardButton("Список рекомендаций по обустройству дома для животного с ограниченными возможностями").callbackData(("(1)(!!)(" + animalId + ")(!!)(6)"));
        InlineKeyboardButton button6 = new InlineKeyboardButton("Советы кинолога для первичного общения с собакой").callbackData(("(1)(!!)(" + animalId + ")(!!)(7)"));
        InlineKeyboardButton button7 = new InlineKeyboardButton("Рекомендации по проверенным кинологам для дальнейшего обращения к ним").callbackData(("(1)(!!)(" + animalId + ")(!!)(8)"));
        InlineKeyboardButton button8 = new InlineKeyboardButton("Принять и записать контактные данные для связи").callbackData(("(1)(!!)(" + animalId + ")(!!)(9)"));
        InlineKeyboardButton button9 = new InlineKeyboardButton("Позвать волонтера.").callbackData("0");
        keyboard.addRow(button, button1, button2, button3, button4, button5, button6, button7, button8, button9);
        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберете пункт, который вас интересует", keyboard);

    }
}
