package ru.skypro.tgbot_petsingoodhands.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.entity.Report;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.service.ReportService;

import java.util.List;
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
            if (checkReport(report)) {
                messages.sendSimpleMessage(report.getClient().getChat_id(),
                        "Добрый день, отчет по клиенту   " + report.getClient().getName() + " хороший!");
            } else {
                messages.sendSimpleMessage(report.getClient().getChat_id(),
                        "Добрый день, отчет по клиенту   " + report.getClient().getName() + " плохой!");
            }
        }
    }

    private boolean checkReport(Report report) {
        return true;
    }

}
