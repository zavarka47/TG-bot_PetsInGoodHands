package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Client;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByShelter_ShelterId (Long shelterId);

    @Query(value = "SELECT \n" +
            "*\n" +
            "FROM public.client AS client \n" +
            "\tLEFT JOIN public.report AS reports ON\n" +
            "\t\tclient.client_id = reports.client_id\n" +
            "\t\tAND reports.data_time_report BETWEEN ?1 AND ?2\n" +
            "WHERE not client.trail_period_is_over = true and coalesce(reports.client_id, 0) = 0", nativeQuery = true)
    List<Client> getClientListWithoutReports(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select \n" +
            "\t*, \n" +
            "\t (select \n" +
            "\t\tdate_part('day', now() - max(rep.data_time_report)) as days\n" +
            "\tfrom report as rep\n" +
            "\twhere rep.client_id = client.client_id \n" +
            "\t) AS rep_days\n" +
            "from\n" +
            "\tpublic.client AS client\n" +
            "where  (select \n" +
            "\t\tdate_part('day', now() - max(rep.data_time_report)) as days\n" +
            "\tfrom report as rep\n" +
            "\twhere rep.client_id = client.client_id ) >= 2", nativeQuery = true)
    List<Client> getClientListWithoutReportsMoreThan2Days();

    List<Client> getClientByAdditionalTrailPeriod(Boolean isTtrail);

    List<Client> getClientByBeginAdditionalTrailPeriodNotNullAndNotificationAdditionalTrailPeriodIsFalse();

    List<Client> getClientsByTrailPeriodIsOverTrueAndNotificationTrailPeriodIsOverFalse();

    Client findByChatId (Long chatId);





}
