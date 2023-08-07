package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByShelter_ShelterId (Long shelterId);

//    @Query(value = "", nativeQuery = true)
//    List<Client> findClientWithoutReps();
}
