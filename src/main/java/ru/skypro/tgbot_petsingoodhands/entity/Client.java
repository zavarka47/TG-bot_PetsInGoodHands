package ru.skypro.tgbot_petsingoodhands.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "client_id")
    private Long clientId;
    @ManyToOne
    @JoinColumn (name = "shelterId")
    private Shelter shelter;
    private String name;
    private String phone;
    private Long chatId;
    private LocalDate beginTrailPeriod;
    private boolean trailPeriodIsOver;
    private boolean notificationTrailPeriodIsOver;
    private Integer additionalTrailPeriod;
    private LocalDate beginAdditionalTrailPeriod;
    private boolean notificationAdditionalTrailPeriod;
    @OneToMany (mappedBy = "client")
    private List<Report> reports;

    public Long getClientId() {
        return clientId;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Long getChatId() {
        return chatId;
    }


}
