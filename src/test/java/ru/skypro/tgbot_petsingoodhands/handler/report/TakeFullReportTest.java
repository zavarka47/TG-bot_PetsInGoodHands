package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.response.SendResponse;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TakeFullReportTest {
    @Mock
    private Messages messages;
    @InjectMocks
    private TakeFullReportHandler takeFullReportHandler;
    private static Update update;

    private final Pattern pattern = Pattern.compile("0.0.2.3.1.\\d+");
    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.2.3.1.1"), Update.class);
    }
    @Test
    public void appliesToTest() {
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }
    @Test
    public void handleUpdateTest() {
        ArgumentCaptor<SendResponse> captor = ArgumentCaptor.forClass(SendResponse.class);

        takeFullReportHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Keyboard> keyboardCaptor = ArgumentCaptor.forClass(Keyboard.class);

        verify(messages).sendMessageWithKeyboard(chatIdCaptor.capture(), textCaptor.capture(), keyboardCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text = textCaptor.getValue();

        Assertions.assertEquals(chatId, update.callbackQuery().from().id());
        Assertions.assertTrue(text.contains("Выберете пункт"));
    }
}
