package ru.skypro.tgbot_petsingoodhands.handler.report;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.skypro.tgbot_petsingoodhands.entity.Report;
import ru.skypro.tgbot_petsingoodhands.handler.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ClientService;
import ru.skypro.tgbot_petsingoodhands.service.ReportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class SaveFullReportHandler implements TelegramHandler {

    private final TelegramBot telegramBot;
    private final ClientService clientService;
    private final ReportService reportService;
    private final Messages messages;

    public SaveFullReportHandler(TelegramBot telegramBot, ClientService clientService, ReportService reportService, Messages messages) {
        this.telegramBot = telegramBot;
        this.clientService = clientService;
        this.reportService = reportService;
        this.messages = messages;
    }

    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ?
                (Objects.nonNull(update.message().photo()) || Objects.nonNull(update.message().document())) : false;
    }

    @Override
    public void handleUpdate(Update update) {
        Long chatId = update.message().chat().id();

        if (Objects.nonNull(update.message().photo()) && Objects.isNull(update.message().document())){
            savePhoto(update);
            messages.sendSimpleMessage(chatId, "Сохранил фото Вашего питомца");
        }
        if (Objects.isNull(update.message().photo()) && Objects.nonNull(update.message().document())){
            saveReport(update);
            messages.sendSimpleMessage(chatId, "Сохранил отчет о состоянии Вашего питомца");
        }
    }

    private void savePhoto (Update update){
        Long chatId = update.message().chat().id();
        Long clientId = Optional.of(clientService.getByChatId(chatId).getClientId()).orElse(0L);

        if (clientId!=0) {
            PhotoSize fullPhoto = update.message().photo()[update.message().photo().length-1];
            PhotoSize smallPhoto = update.message().photo()[0];
            GetFileResponse fileResponse = telegramBot.execute(new GetFile(fullPhoto.fileId()));
            if (fileResponse.isOk()){
                try {
                    String extension = StringUtils.getFilenameExtension(
                            fileResponse.file().filePath());
                    byte [] image =  telegramBot.getFileContent(fileResponse.file());
                    byte [] preview = telegramBot.getFileContent(telegramBot.execute(new GetFile(smallPhoto.fileId())).file());

                    Path path = Path.of("src/reports/"+clientId+"_"+LocalDate.now()+"/");

                    if(!Files.exists(path)){
                        Files.createDirectories(path);
                    }

                    Files.write(Paths.get( path +"/"+ (clientId+"_"+LocalDate.now())+"."+extension),image);

                    {
                        Report report = reportService.getTodayReportByClientId(clientId);

                        if (Objects.nonNull(report)){
                            report.setPhoto(preview);
                            reportService.saveReport(report);
                        } else {
                            report = new Report();
                            report.setClient(clientService.getById(clientId));
                            report.setDataTimeReport(LocalDateTime.now());
                            report.setPhoto(preview);
                            reportService.saveReport(report);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void saveReport (Update update){
        Long chatId = update.message().chat().id();
        Long clientId = Optional.of(clientService.getByChatId(chatId).getClientId()).orElse(0L);
        if (clientId!=0){

        Document document = update.message().document();
        GetFileResponse fileResponse = telegramBot.execute(new GetFile(document.fileId()));
        if (fileResponse.isOk()){
            try {
                String extension = StringUtils.getFilenameExtension(
                        fileResponse.file().filePath());
                byte [] file =  telegramBot.getFileContent(fileResponse.file());

                Path path = Path.of("src/reports/"+clientId+"_"+LocalDate.now()+"/");
                if(!Files.exists(path)){
                    Files.createDirectories(path);
                }

                Files.write(Paths.get( path +"/"+ (clientId+"_"+LocalDate.now())+"."+extension),file);


                    Report report = reportService.getTodayReportByClientId(clientId);

                    if (Objects.nonNull(report)){
                        report.setReport(file);
                        reportService.saveReport(report);
                    } else {
                        report = new Report();
                        report.setClient(clientService.getById(clientId));
                        report.setDataTimeReport(LocalDateTime.now());
                        report.setReport(file);
                        reportService.saveReport(report);
                    }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        }
    }
}
