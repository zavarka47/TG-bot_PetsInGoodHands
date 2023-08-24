package ru.skypro.tgbot_petsingoodhands.header.report;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Component
public class SaveFullReportHandler implements TelegramHandler {

    private final TelegramBot telegramBot;

    public SaveFullReportHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message().photo()) || Objects.nonNull(update.message().document());
    }

    @Override
    public void handleUpdate(Update update) {
        if (Objects.nonNull(update.message().photo())){
            savePhoto(update);
        }


    }

    private void savePhoto (Update update){
        PhotoSize photoSize = update.message().photo()[update.message().photo().length-1];
        GetFileResponse fileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
        if (fileResponse.isOk()){
            try {
            String extension = StringUtils.getFilenameExtension(
                    fileResponse.file().filePath());
            byte [] image =  telegramBot.getFileContent(fileResponse.file());

                Files.write(Paths.get(LocalDate.now()+"."+extension),image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
