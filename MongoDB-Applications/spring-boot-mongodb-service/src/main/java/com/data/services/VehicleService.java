package com.data.services;

import com.data.documents.Vehicle;
import com.data.dto.VehicleCreateDTO;
import com.data.dto.VehicleUpdateDTO;

import java.util.UUID;

public interface VehicleService {

    public Vehicle getVehicleById(UUID id);
    public Vehicle createVehicle(VehicleCreateDTO vehicleCreateDTO);
    public Vehicle updateVehicle(VehicleUpdateDTO vehicleUpdateDTO, UUID id);
    public Vehicle deleteVehicleById(UUID id);
}
