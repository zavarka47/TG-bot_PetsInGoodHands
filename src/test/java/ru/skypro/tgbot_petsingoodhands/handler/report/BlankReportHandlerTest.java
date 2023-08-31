package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.handler.animal.ChooseFunctionByAnimalHandlerTest;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/// как подсунуть текстовый файл на проверку e
@ExtendWith(MockitoExtension.class)
    public class BlankReportHandlerTest {
    @Mock
    private  Messages messages;
    @Mock
    private  TelegramBot telegramBot;
    @InjectMocks
    private BlankReportHandler blackReportHandler;
    private static Update update;
    private static SendResponse sendResponse;

    Pattern pattern = Pattern.compile("0.0.1.3.1.\\d+");


    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        update = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.1.3.1.1"), Update.class);
        String response = Files.readString(Path.of(
                ChooseFunctionByAnimalHandlerTest.class.getClassLoader().getResource("response.json").toURI()));
       sendResponse = BotUtils.fromJson(response, SendResponse.class);
    }
    @Test
    public void appliesToTest(){
        Assertions.assertTrue(pattern.matcher(update.callbackQuery().data()).find());
    }

  /*  @Test
    public void handleUpdateTest(){
        when(telegramBot.execute(any())).thenReturn(sendResponse);
        blackReportHandler.handleUpdate(update);
        ArgumentCaptor<SendResponse> captor = ArgumentCaptor.forClass(SendResponse.class);
        verify(telegramBot,times(1)).execute(captor.capture());
        var sendDocument = captor.getValue();

//        Assertions.assertEquals(sendDocument.getParameters().get("isMultipart"),true);

*/
    }






