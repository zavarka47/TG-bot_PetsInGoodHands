package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
