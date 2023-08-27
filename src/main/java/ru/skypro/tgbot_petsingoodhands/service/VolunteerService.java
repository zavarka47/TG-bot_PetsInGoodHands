package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Long getChatIdBySheltersId (Long sheltersId) {
        return volunteerRepository.getVolunteerByShelter_ShelterId(sheltersId).getChatId();
    }
}

