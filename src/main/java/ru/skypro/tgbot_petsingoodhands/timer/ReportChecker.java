package ru.skypro.tgbot_petsingoodhands.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Report;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ReportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ReportChecker {

    private  final ReportService reportService;

    private final Messages messages;

    public ReportChecker(ReportService reportService, Messages messages) {
        this.reportService = reportService;
        this.messages = messages;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void task() {

        List<Report> reports = reportService.getReportByNotificationAboutQualityReport();
        for (Report report : reports) {

            report.setNotificationAboutQualityReport(true);

            reportService.saveReport(report);

            HashMap<String, Boolean> reportRespond = checkReport(report);
            if (reportRespond.isEmpty()) {
                messages.sendSimpleMessage(report.getClient().getChatId(),
                        "Добрый день, отчет по клиенту " + report.getClient().getName() + " хороший!");
            } else {
                String errMessage = "Добрый день, отчет по клиенту " + report.getClient().getName()
                                    + " имеет следующие ошибки : \n";
                for (String key : reportRespond.keySet()) {

                    errMessage = errMessage + " не заплнено поле " +  key + "\n";

                }
                messages.sendSimpleMessage(report.getClient().getChatId(), errMessage);
            }
        }
    }

    private HashMap<String, Boolean> checkReport(Report report) {

        HashMap<String, Boolean> reportStatus = new HashMap<String, Boolean>();
        
        if (report.getPhoto() == null) {
            reportStatus.put("photo", false);
        }
        if (report.getReport() == null) {
            reportStatus.put("report", false);
        }
        return reportStatus;
    }

}
