package ru.skypro.tgbot_petsingoodhands.service;

import org.springframework.stereotype.Service;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;
import ru.skypro.tgbot_petsingoodhands.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ReportService reportService;

    public ClientService(ClientRepository clientRepository, ReportService reportService) {
        this.clientRepository = clientRepository;
        this.reportService = reportService;
    }

    public Client getById (Long id){
        return clientRepository.findById(id).orElse(null);
    }
    public List<Client> getClientsByShelterId (Long shelterId){
        return clientRepository.findAllByShelter_ShelterId(shelterId);
    }

    /**
     * Метод возвращает список клиентов, не приславших форму ежедневного отчета за последние сутки.
     * Используются методы:
     * {@link ReportService#yesterdayReportIsExist()}<p>
     * {@link ClientRepository#findAll()}
     */
    public List<Client> getClientHaveNotReport (){
        List<Client> clientsHaveNotReport = new ArrayList<>();
        List<Client> clientsHaveReport = reportService.yesterdayReportIsExist().stream().map(r -> r.getClient()).collect(Collectors.toList());
        List<Client> clients = clientRepository.findAll();
        for (Client c : clients) {
            if (!clientsHaveReport.contains(c)){
                clientsHaveNotReport.add(c);
            }
        }
        return clientsHaveNotReport;
    }
}
