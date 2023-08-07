package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository <Shelter, Long> {
    List<Shelter> findAllByAnimal_AnimalId (Long animalId);
}
