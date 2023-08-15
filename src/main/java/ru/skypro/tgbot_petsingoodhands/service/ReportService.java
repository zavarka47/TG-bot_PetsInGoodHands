package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.repository.ReportRepository;

import java.time.LocalDateTime;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    public ReportService (ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }
    public boolean yesterdayReportIsExist (){
        return reportRepository.getReportByDataTimeReportAfter(LocalDateTime.now().minusDays(1));
    }
}
