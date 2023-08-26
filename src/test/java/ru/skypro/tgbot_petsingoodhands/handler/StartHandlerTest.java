package ru.skypro.tgbot_petsingoodhands.handler;

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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StartHandlerTest {
    @Mock
    private Messages messages;
    @Mock
    private AnimalService animalService;
    @InjectMocks
    private StartHandler startHandler;
    private static Update update;
    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String message = Files.readString(
                Path.of(StartHandlerTest.class.getClassLoader().getResource("message.json").toURI()));
        update = BotUtils.fromJson(message.replace("%text%","/start"), Update.class);
    }
    @Test
    public void appliesToTest(){
        Assertions.assertEquals(update.message().text(), "/start");
    }

    @Test
    public void handleUpdateTest(){
        startHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Keyboard> keyboardCaptor = ArgumentCaptor.forClass(Keyboard.class);

        verify(messages).sendMessageWithKeyboard(chatIdCaptor.capture(), textCaptor.capture(), keyboardCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text = textCaptor.getValue();

        Assertions.assertEquals(chatId, update.message().chat().id());
        Assertions.assertTrue(text.contains("животное"));
    }


}