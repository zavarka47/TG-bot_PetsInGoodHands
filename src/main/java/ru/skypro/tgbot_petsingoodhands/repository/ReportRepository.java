package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean getReportByDataTimeReportAfter (LocalDateTime localDateTime);
}
