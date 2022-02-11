package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.documentation.Documentation;
import com.api.parkingcontrol.documentation.Request;
import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.enums.Author;
import com.api.parkingcontrol.enums.CustomStatus;
import com.api.parkingcontrol.enums.RequestMethod;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @Documentation(doc = "save parking spot", 
		           author = Author.michelliBrito,
		           api = @Request(method = RequestMethod.POST, url = "/parking-spot/save-parking-spot"),
		           date = "07-02-2022")
    @PostMapping(path  = "/save")
    @ApiOperation(value = "save data")
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setStatus(CustomStatus.BUSY);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @Documentation(doc = "get all data", 
	               author = Author.michelliBrito,
	               api = @Request(method = RequestMethod.GET, url = "/parking-spot/get-all"),
	               date = "07-02-2022") 
    @GetMapping(path = "/get-all")
    @ApiOperation(value = "list all data")
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @Documentation(doc = "get one parking spot by ID", 
	               author = Author.michelliBrito,
	               api = @Request(method = RequestMethod.GET, url = "/parking-spot/id"),
	               date = "07-02-2022")
    @GetMapping(path =  "/parking-spot/{id}")
    @ApiOperation(value = "get one data by ID")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @Documentation(doc = "delete parking spot by ID", 
	               author = Author.michelliBrito,
	               api = @Request(method = RequestMethod.DELETE, url = "/parking-spot/delete/id"),
	               date = "07-02-2022")
    @DeleteMapping(path = "/parking-spot/delete/{id}")
    @ApiOperation(value = "delete data by ID")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @Documentation(doc = "update parking spot", 
	           author = Author.michelliBrito,
	           api = @Request(method = RequestMethod.POST, url = "/parking-spot/update/id"),
	           date = "07-02-2022")
    @PutMapping(path = "/parking-spot/update/{id}")
    @ApiOperation(value = "update data")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDTO parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
   
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
