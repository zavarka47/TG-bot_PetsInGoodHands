package ru.skypro.tgbot_petsingoodhands.handler.shelter;


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
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveContactHandlerTest {

    @InjectMocks
    private SaveContactHandler saveContactHandler;

    @Mock
    private Messages messages;

    @Mock
    private ShelterService shelterService;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private static Update update;
    private final Pattern pattern = Pattern.compile("(\\d+)_([А-я\\s)]+)_(\\d+)");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                SaveContactHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        String message = Files.readString(Path.of(
                SaveContactHandlerTest.class.getClassLoader().getResource("message.json").toURI()));
        update = BotUtils.fromJson(message.replace("%text%", "1_Иванов Иван_88005553535"), Update.class);
    }

    @Test
    public void appliesToTest(){
        Assertions.assertTrue(pattern.matcher(update.message().text()).find());
    }

    @Test
    public void handleUpdateTest(){

        var shelter = mock(Shelter.class);

        saveContactHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);

        verify(messages).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text =  textCaptor.getValue();

        Assertions.assertEquals(chatId, update.message().chat().id());
        Assertions.assertTrue(text.contains("Ваш контакт сохранен"));
    }

}
