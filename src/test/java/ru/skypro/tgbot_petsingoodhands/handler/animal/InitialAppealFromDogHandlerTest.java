package ru.skypro.tgbot_petsingoodhands.handler.animal;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
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
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.AnimalService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitialAppealFromDogHandlerTest {
    @Mock
    private Messages messages;
    @Mock
    private AnimalService animalService;
    @Mock
    private ShelterService shelterService;
    @InjectMocks
    private InitialAppealFromDogHandler initialAppealFromDogHandler;
    private static Update update;
    Pattern pattern = Pattern.compile("0.0.7.2.1.\\d+");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.7.2.1.1"), Update.class);
    }

    @Test
    public void appliesToTest() {
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }

    @Test
    public void handleUpdateTest() {
        var shelter = mock(Shelter.class);
        var animal = mock(Animal.class);
        when(shelterService.getShelterById(any())).thenReturn(shelter);
        when(shelter.getAnimal()).thenReturn(animal);
        when(animalService.getById(any())).thenReturn(animal);
        when(animal.getInitialAppealFromDogHandler()).thenReturn("Советы кинолога по первичному общению с псом");
        initialAppealFromDogHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);

        verify(messages).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text = textCaptor.getValue();

        Assertions.assertEquals(chatId, update.callbackQuery().from().id());
        Assertions.assertEquals(text, "Советы кинолога по первичному общению с псом");

    }
}
