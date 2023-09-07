
package ru.skypro.tgbot_petsingoodhands.handler.report;

import com.pengrad.telegrambot.BotUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;
import ru.skypro.tgbot_petsingoodhands.service.ShelterService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import com.pengrad.telegrambot.model.Update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaveFullReportHandlerTest {

    @InjectMocks
    private SaveFullReportHandler saveFullReportHandler;

    @Mock
    private Messages messages;

    @Mock
    private ClientService clientService;

    private static Update update;

    private static Update updateCallBack;
    private final Pattern pattern = Pattern.compile("0.0.1.1.1.\\d+");


    @Test
    public void appliesToTest() {
        Assertions.assertTrue(pattern.matcher(updateCallBack.callbackQuery().data()).find());
    }

    @BeforeAll
    public static void initializationResource() throws URISyntaxException, IOException {
        String callbackQuery = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("callbackQuery.json").toURI()));
        String message = Files.readString(Path.of(
                SaveFullReportHandlerTest.class.getClassLoader().getResource("message.json").toURI()));
        update = BotUtils.fromJson(message.replace("%text%", "0.0.1.1.1.1"), Update.class);
        updateCallBack = BotUtils.fromJson(callbackQuery.replace("%text%", "0.0.1.1.1.1"), Update.class);
    }
    @Test
    public void handleUpdateTest() throws URISyntaxException, IOException{

        var client = mock(Client.class);

        when(clientService.getByChatId(any())).thenReturn(client);
        when(client.getId()).thenReturn(0L);

        saveFullReportHandler.handleUpdate(update);

        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);

        verify(messages).sendSimpleMessage(chatIdCaptor.capture(), textCaptor.capture());

        Long chatId = chatIdCaptor.getValue();
        String text =  textCaptor.getValue();

        Assertions.assertEquals(chatId, update.message().chat().id());
        Assertions.assertTrue(text.contains("Сохранил фото Вашего питомца"));
    }


}