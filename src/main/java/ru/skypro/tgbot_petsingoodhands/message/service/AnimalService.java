package ru.skypro.tgbot_petsingoodhands.message.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.entity.Animal;
import ru.skypro.tgbot_petsingoodhands.repository.AnimalRepository;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService (AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAll (){
        return animalRepository.findAll();
    }

    public Animal getById (Long id){
        return animalRepository.findById(id).orElse(null);
    }
}
