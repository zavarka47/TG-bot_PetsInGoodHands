package ru.skypro.tgbot_petsingoodhands.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Animal {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "animal_id")
    private Long animalId;
    private String type;
    private String firstImpressionInShelter;
    private String transportation;
    private String houseForBabyPet;
    private String houseForAdultPet;
    private String houseForIllPet;
    private String initialAppealFromDogHandler;
    private String middleAppealFromDogHandler;
    @OneToMany (mappedBy = "animal")
    private List<Shelter>  shelters;

    public Long getAnimalId() {
        return animalId;
    }

    public String getType() {
        return type;
    }

    public String getFirstImpressionInShelter() {
        return firstImpressionInShelter;
    }

    public String getTransportation() {
        return transportation;
    }

    public String getHouseForBabyPet() {
        return houseForBabyPet;
    }

    public String getHouseForAdultPet() {
        return houseForAdultPet;
    }

    public String getHouseForIllPet() {
        return houseForIllPet;
    }

    public String getInitialAppealFromDogHandler() {
        return initialAppealFromDogHandler;
    }

    public String getMiddleAppealFromDogHandler() {
        return middleAppealFromDogHandler;
    }

}
