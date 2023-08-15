package ru.skypro.tgbot_petsingoodhands.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.tgbot_petsingoodhands.entity.Client;
import ru.skypro.tgbot_petsingoodhands.repository.ClientRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void should_findClientWithTrialPeriodWithoutNotification() {


        Mockito.when(clientRepository.getClientByBeginAdditionalTrailPeriodNotNullAndNotificationAdditionalTrailPeriodIsFalse())
                .thenReturn(List.of(new Client(), new Client()));


        Assertions.assertThat(service.getClientByBeginAdditionalTrailPeriodNotNullAndNotificationAdditionalTrailPeriodIsFalse()).hasSize(2);

        Mockito.verify(clientRepository, Mockito.times(1))
                .getClientByBeginAdditionalTrailPeriodNotNullAndNotificationAdditionalTrailPeriodIsFalse();

        Mockito.verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void should_saveClient() {

        final Client clientToSave = new Client();
        clientToSave.setName("Petrrrr");

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(clientToSave);

        // Act
        final Client actual = service.saveClient(new Client());

        // Assert
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(clientToSave);
        Mockito.verify(clientRepository,  Mockito.times(1)).save(Mockito.any(Client.class));
        Mockito.verifyNoMoreInteractions(clientRepository);

    }

}
