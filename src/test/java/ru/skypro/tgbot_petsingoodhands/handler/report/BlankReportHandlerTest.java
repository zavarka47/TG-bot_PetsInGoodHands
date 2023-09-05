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


import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;



import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlankReportHandlerTest {

    @InjectMocks
    private BlankReportHandler blankReportHandler;
    @Mock
    private TelegramBot telegramBot;

    private static Update update;

    private static Update updateCallBack;
    private final Pattern pattern = Pattern.compile("0.0.1.3.1.\\d+");

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        String message = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("message.json").toURI()));
        update = BotUtils.fromJson(message.replace("%text%", "0.0.1.3.1.1"), Update.class);
        updateCallBack = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.1.3.1.1"), Update.class);
    }
    @Test
    public void appliesToTest() {
        Assertions.assertTrue(pattern.matcher(updateCallBack.callbackQuery().data()).find());
    }
    @Test
    public void handleUpdateTest() throws URISyntaxException, IOException{

        SendResponse sendResponse = BotUtils.fromJson("""
                {
                "ok": true
                }
                """, SendResponse.class);

        ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);

        when(telegramBot.execute(any())).thenReturn(sendResponse);
        blankReportHandler.handleUpdate(updateCallBack);

        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendDocument document = argumentCaptor.getValue();

        Assertions.assertEquals(document.getFileName(), "Форма отчета");
        Assertions.assertNotEquals(document.getParameters().size(), 0);
        Assertions.assertTrue(((byte[])document.getParameters().get("document")).length > 0);

    }

}

