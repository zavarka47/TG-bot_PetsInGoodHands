package ru.skypro.tgbot_petsingoodhands.handler;

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
import ru.skypro.tgbot_petsingoodhands.handler.animal.ChooseFunctionByAnimalHandlerTest;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.VolunteerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CallVolunteerHandlerTest {
    @Mock
    private Messages messages;
    @Mock
    private VolunteerService volunteerService;
    @InjectMocks
    private CallVolunteerHandler callVolunteerHandler;
    private static Update update;
    private Pattern pattern = Pattern.compile("0.0.0.0.0.\\d+");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.0.0.0.1"), Update.class);
    }
    @Test
    public void appliesToTest(){
        Assertions.assertTrue(Objects.nonNull(update.callbackQuery()));
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }
    @Test
    public void handlersUpdateTest(){
        when(volunteerService.getChatIdBySheltersId(any())).thenReturn(321L);

        callVolunteerHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);

        verify(messages, times(2)).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatIdClient = chatIdCaptor.getAllValues().get(0);
        String textFoClient = textCaptor.getAllValues().get(0);

        Assertions.assertEquals(chatIdClient, 321L);
        Assertions.assertTrue(textFoClient.contains("Необходима помощь"));

        Long chatIdVolunteer = chatIdCaptor.getAllValues().get(1);
        String textFoVolunteer = textCaptor.getAllValues().get(1);

        Assertions.assertEquals(chatIdVolunteer, update.callbackQuery().from().id());
        Assertions.assertTrue(textFoVolunteer.contains("Помощь в пути, ожидайте"));

    }
}
