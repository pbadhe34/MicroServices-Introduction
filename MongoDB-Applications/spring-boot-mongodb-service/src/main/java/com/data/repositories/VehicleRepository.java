package com.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.documents.Vehicle;

import java.util.UUID;

public interface VehicleRepository extends MongoRepository<Vehicle, UUID> {
}
