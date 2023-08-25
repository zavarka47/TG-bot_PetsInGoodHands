package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.entity.Report;
import ru.skypro.tgbot_petsingoodhands.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    public ReportService (ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    /**
     * Метод возвращает список Report, которые были созданы за последние сутки
     */
    @Transactional
    public List<Report> yesterdayReportIsExist (){
        return reportRepository.getReportByDataTimeReportAfter(LocalDateTime.now().minusDays(1));
    }

    @Transactional
    public List<Report> getReportByNotificationAboutQualityReport() {

       return reportRepository.getReportByNotificationAboutQualityReport(false);

    }

    @Transactional
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Transactional
    public Report getTodayReportByClientId(Long clientId){
        return reportRepository.getTodayReportByClientId(/*clientId*/);
    }
}
