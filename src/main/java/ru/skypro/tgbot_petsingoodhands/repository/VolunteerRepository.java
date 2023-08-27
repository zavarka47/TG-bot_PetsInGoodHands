package ru.skypro.tgbot_petsingoodhands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.tgbot_petsingoodhands.entity.Volunteer;
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    public Volunteer getVolunteerByShelter_ShelterId(Long sheltersId);
}
