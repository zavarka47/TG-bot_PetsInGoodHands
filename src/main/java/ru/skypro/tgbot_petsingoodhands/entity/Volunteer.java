package ru.skypro.tgbot_petsingoodhands.entity;

import javax.persistence.*;

@Entity
public class Volunteer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long volunteerId;

    private Long chatId;
    @ManyToOne
    @JoinColumn (name = "shelterId")
    private Shelter shelter;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}
