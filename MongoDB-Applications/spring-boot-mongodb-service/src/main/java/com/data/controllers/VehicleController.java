package com.data.controllers;

import com.data.documents.Vehicle;
import com.data.dto.VehicleCreateDTO;
import com.data.dto.VehicleUpdateDTO;
import com.data.services.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping(value = "/vehicles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Vehicle> getVehicle(@PathVariable(value = "id") UUID id){
        return new ResponseEntity<>(vehicleService.getVehicleById(id), HttpStatus.OK);
    }
    
    //post data {"make":"Toyota","model":"DSK", "registrationNumber":"Gtfr"} {"make":"Maruti","model":"Omni", "registrationNumber":"MH-12 6754"}

    @PostMapping(value = "/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleCreateDTO vehicleCreateDTO){
        return new ResponseEntity<>(vehicleService.createVehicle(vehicleCreateDTO), HttpStatus.CREATED);
    }

    //put data {"make":"Mahindra","model":"Data"}
    @PutMapping(value = "/vehicles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleUpdateDTO vehicleUpdateDTO, @PathVariable(value = "id") UUID id){
        return new ResponseEntity<>(vehicleService.updateVehicle(vehicleUpdateDTO, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/vehicles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable(value="id") UUID id){
        return new ResponseEntity<>(vehicleService.deleteVehicleById(id), HttpStatus.OK);
    }
}
