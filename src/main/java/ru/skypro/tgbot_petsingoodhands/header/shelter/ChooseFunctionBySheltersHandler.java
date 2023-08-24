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
        InlineKeyboardButton button1 = new InlineKeyboardButton("О приюте").callbackData("0.0.1.1.1." + shelterId);/// aboutShelters
        InlineKeyboardButton button2 = new InlineKeyboardButton("Расписание работы, адрес и схема проезда").callbackData("0.0.2.1.1."+ shelterId);/// WorkHourShelterHandler
        InlineKeyboardButton button3 = new InlineKeyboardButton("Контактные данные охраны для оформления пропуска").callbackData("0.0.3.1.1."+ shelterId);///TakeContactHandler
        InlineKeyboardButton button4 = new InlineKeyboardButton("Рекомендации о технике безопасности на территории приюта").callbackData("0.0.4.1.1."+ shelterId);/// SafetyRecommendationsShelterHandler
        InlineKeyboardButton button5 = new InlineKeyboardButton("Записать контактные данные для связи").callbackData("0.0.5.1.1."+ shelterId); ///TakeContactHandler
        InlineKeyboardButton button6 = new InlineKeyboardButton("Позвать волонтера").callbackData("1.0.0.0.0."+ shelterId);/// СallVolunteerHandler
        keyBoard.addRow(button1).addRow(button2).addRow(button3).addRow(button4).addRow(button5).addRow(button6);



        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберете пункт который Вас интересует", keyBoard);


    }
}
