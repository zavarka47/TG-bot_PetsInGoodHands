package ru.skypro.tgbot_petsingoodhands.handler.animal;

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
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirstMeetRulesHandlersTest {
    @Mock
    private Messages messages;
    @Mock
    private AnimalService animalService;
    @Mock
    private ShelterService shelterService;
    @InjectMocks
    private FirstMeetRulesHandlers firstMeetRulesHandlers;
    private static Update update;
    Pattern pattern = Pattern.compile("0.0.1.2.1.\\d+");
    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.1.2.1.1"), Update.class);
    }
    @Test
    public void appliesToTest(){
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }

    public void handleUpdateTest(){
        firstMeetRulesHandlers.handleUpdate(update);

        when(animalService.getById(any()).getFirstImpressionInShelter()).thenReturn("Правила знакомства");

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);

        verify(messages).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text =  textCaptor.getValue();

        Assertions.assertEquals(chatId, update.callbackQuery().from().id());
        Assertions.assertEquals(text, "Правила знакомства");

    }
}
