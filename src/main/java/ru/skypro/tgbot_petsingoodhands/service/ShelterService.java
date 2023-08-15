package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.repository.ShelterRepository;

import java.util.List;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    public ShelterService (ShelterRepository shelterRepository){
        this.shelterRepository = shelterRepository;
    }

    public Shelter getShelterById (Long id){
        return shelterRepository.findById(id).orElse(null);
    }

    public List<Shelter> getSheltersByAnimalTypeId (Long animalId){
        return shelterRepository.findAllByAnimal_AnimalId(animalId);
    }
}
