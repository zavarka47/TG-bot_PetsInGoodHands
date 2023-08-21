package ru.skypro.tgbot_petsingoodhands.header.shelter;

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
public class ChooseFunctionBySheltersHandler implements TelegramHandler {
    private final Messages messages;
    private final Pattern pattern = Pattern.compile("0.0.0.1.1.\\d+");

    public ChooseFunctionBySheltersHandler(Messages messages) {
        this.messages = messages;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать о приюте").callbackData("0.0.1.1.1." + shelterId);/// aboutShelters
        InlineKeyboardButton button2 = new InlineKeyboardButton("Узнать расписание работы приюта и адрес, схему проезда").callbackData("0.0.2.1.1."+ shelterId);/// WorkHourShelterHandler
        InlineKeyboardButton button3 = new InlineKeyboardButton("Узнать контактные данные охраны для оформления пропуска на машину.").callbackData("0.0.3.1.1."+ shelterId);///GetImpotentDocumentHandler
        InlineKeyboardButton button4 = new InlineKeyboardButton("Узнать общие рекомендации о технике безопасности на территории приюта.").callbackData("0.0.4.1.1."+ shelterId);/// SafetyRecommendationsShelterHandler
        InlineKeyboardButton button5 = new InlineKeyboardButton("Узнать контактные данные приюта.").callbackData("0.0.5.1.1."+ shelterId);///GetContactShelterHandler
        InlineKeyboardButton button6 = new InlineKeyboardButton("Принять и записать контактные данные для связи.").callbackData("0.0.6.1.1."+ shelterId); ///TakeContactHandler
        InlineKeyboardButton button7 = new InlineKeyboardButton("Позвать волонтера").callbackData("0.0.10.1.1."+ shelterId);/// СallVolunteerHandler
        keyBoard.addRow(button1).addRow(button2).addRow(button3).addRow(button4).addRow(button5).addRow(button6).addRow(button7);

        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберете пункт который Вас интересует", keyBoard);


    }
}
