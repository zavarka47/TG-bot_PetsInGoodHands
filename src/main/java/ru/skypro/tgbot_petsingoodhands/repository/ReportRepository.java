package ru.skypro.tgbot_petsingoodhands.repository;

import liquibase.pro.packaged.B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.entity.Report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> getReportByDataTimeReportAfter (LocalDateTime localDateTime);

    List<Report> getReportByNotificationAboutQualityReport(Boolean notificationAboutQuality);

}
