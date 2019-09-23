package com.data.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

//Custom collection name where it will be saved
@Document("user-vehicles")
public class Vehicle {

    @Id
    private UUID _id;

    private String make;
    private String model;
    private String registrationNumber;

    public Vehicle() {
    }

    public Vehicle(String make, String model, String registrationNumber) {
        this._id = UUID.randomUUID();
        this.make = make;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public UUID get_id() {
        return _id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
