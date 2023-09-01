package ru.skypro.tgbot_petsingoodhands.handler.animal;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class ChooseFunctionByAnimalHandler implements TelegramHandler {
    private final Messages messages;
    private final AnimalService animalService;
    private final ShelterService shelterService;

    public ChooseFunctionByAnimalHandler(Messages messages, AnimalService animalService, ShelterService shelterService) {
        this.messages = messages;
        this.animalService = animalService;
        this.shelterService = shelterService;
    }

    private Pattern pattern = Pattern.compile("0.0.0.2.1.\\d+");
    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.callbackQuery()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.callbackQuery().from().id();
        Long shelterId = Long.parseLong(update.callbackQuery().data().substring(10));
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Правила знакомства").callbackData("0.0.1.2.1."+shelterId);///FirstMeetRulesHandlers
        InlineKeyboardButton button2 = new InlineKeyboardButton("Список документов для усыновления").callbackData("0.0.2.2.1."+shelterId);///GetImpotentDocumentHandler
        InlineKeyboardButton button3 = new InlineKeyboardButton("Рекомендации по транспортировке").callbackData("0.0.3.2.1."+shelterId);///TransportationPetHandler
        InlineKeyboardButton button4 = new InlineKeyboardButton("Обустройство дома для мальца").callbackData("0.0.4.2.1."+shelterId);///HouseForBabyPetHandler
        InlineKeyboardButton button5 = new InlineKeyboardButton("Обустройство дома для питомца").callbackData("0.0.5.2.1."+shelterId);///HouseForAdultPetHandler
        InlineKeyboardButton button6 = new InlineKeyboardButton("Обустройство дома для немощного").callbackData("0.0.6.2.1."+shelterId);///HouseForIllPetHandler
        keyboard.addRow(button1).addRow(button2).addRow(button3).addRow(button4).addRow(button5).addRow(button6);
        if (animalService.getById(shelterService.getShelterById(shelterId).getAnimal().getId()).getType().equals("пёс")){
            InlineKeyboardButton button7 = new InlineKeyboardButton("Советы кинологи по общению с псом").callbackData("0.0.7.2.1."+shelterId);///InitialAppealFromDogHandler
            InlineKeyboardButton button8 = new InlineKeyboardButton("Советы кинологи по дрессировке").callbackData("0.0.8.2.1."+shelterId);///MiddleAppealFromDogHandler
            keyboard.addRow(button7).addRow(button8);
        }
        InlineKeyboardButton button9 = new InlineKeyboardButton("Записать контактные данные для связи").callbackData("0.0.5.1.1."+ shelterId);
        InlineKeyboardButton button10 = new InlineKeyboardButton("Позвать волонтера").callbackData("1.0.0.0.0."+ shelterId);/// СallVolunteerHandler
        keyboard.addRow(button9).addRow(button10);

        messages.sendMessageWithKeyboard(chatId, "Выберете пункт который Вас интересует", keyboard);
    }
}
