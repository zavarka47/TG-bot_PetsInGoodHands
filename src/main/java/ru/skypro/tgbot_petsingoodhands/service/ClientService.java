package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getById (Long id){
        return clientRepository.findById(id).orElse(null);
    }
    public List<Client> getClientsByShelterId (Long shelterId){
        return clientRepository.findAllByShelter_ShelterId(shelterId);
    }
}
