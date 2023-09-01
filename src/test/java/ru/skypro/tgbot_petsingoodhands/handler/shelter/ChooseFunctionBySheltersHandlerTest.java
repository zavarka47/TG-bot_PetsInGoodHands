package ru.skypro.tgbot_petsingoodhands.handler.shelter;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.tgbot_petsingoodhands.entity.Animal;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.handler.animal.ChooseFunctionByAnimalHandler;
import ru.skypro.tgbot_petsingoodhands.handler.animal.ChooseFunctionByAnimalHandlerTest;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChooseFunctionBySheltersHandlerTest {

    @InjectMocks
    private ChooseFunctionBySheltersHandler chooseFunctionBySheltersHandler;

    @Mock
    private Messages messages;

    @Mock
    private ShelterService shelterService;

    private static Update update;

    private final Pattern pattern = Pattern.compile("0.0.1.1.1.\\d+");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionBySheltersHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.1.1.1.1"), Update.class);
    }

    @Test
    public void appliesToTest(){
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }

    @Test
    public void handleUpdateTest(){

        var shelter = mock(Shelter.class);

        chooseFunctionBySheltersHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Keyboard> keyboardCaptor = ArgumentCaptor.forClass(Keyboard.class);

        verify(messages).sendMessageWithKeyboard(chatIdCaptor.capture(), textCaptor.capture(), keyboardCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text =  textCaptor.getValue();

        Assertions.assertEquals(chatId, update.callbackQuery().from().id());
        Assertions.assertTrue(text.contains("Выберете пункт который Вас интересует"));

    }

}
