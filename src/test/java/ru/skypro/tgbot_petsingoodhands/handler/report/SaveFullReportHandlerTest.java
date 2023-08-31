package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
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
/// Нужно найти как подсунуть документ или фото что бы он провалился в message или как замокать
@ExtendWith(MockitoExtension.class)
public class SaveFullReportHandlerTest {
    @Mock
    private Messages messages;


    @InjectMocks
    private SaveFullReportHandler saveFullReportHandler;
    private static Update update;
    Pattern pattern = Pattern.compile("0.0.0.3.1.\\d+");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException{
        String callbackQuery = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        String message = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("mediaMessage.json").toURI()));
        update = BotUtils.fromJson(message.replace("%text%", "Text"), Update.class);
    }

    @Test
    public void appliesToTest() {
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }

    @Test
    public void handleUpdateTest() {
        ArgumentCaptor<SendResponse> captor = ArgumentCaptor.forClass(SendResponse.class);

        saveFullReportHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);


        verify(messages).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text = textCaptor.getValue();

        Assertions.assertEquals(chatId, update.callbackQuery().from().id());
        Assertions.assertTrue(text.contains("Пришлите заполненный отчет и фото питомца"));
    }
}

